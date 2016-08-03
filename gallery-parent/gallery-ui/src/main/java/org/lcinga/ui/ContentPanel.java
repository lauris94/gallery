package org.lcinga.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.enums.ImageQuality;
import org.lcinga.service.PictureService;
import org.lcinga.ui.utils.DateUtils;
import org.lcinga.ui.utils.ImageUtils;

import java.util.List;

/**
 * Created by lcinga on 2016-08-01.
 */
public class ContentPanel extends Panel {
    private static final long serialVersionUID = -3473068584065610593L;

    private List<Picture> pictures;
    private static final int ITEMS_PER_PAGE = 4;
    private static final Double MODAL_WIDTH_MULTIPLIER = 0.5;
    private static final Double MODAL_HEIGHT_MULTIPLIER = 0.7;
    private ModalWindow modalWindow;
    private ModalLargeImage modalLargeImage;
    private ModalUploadImage modalUploadImage;
    private ModalEditImage modalEditImage;
    private Picture picture;

    @SpringBean
    private PictureService pictureService;

    public ContentPanel(String id) {
        super(id);
        pictures = pictureService.getAllPictures();
        makePicturesListView();
        modalWindow = new ModalWindow("modalWindow");
        setModalWindowSizeByBrowserSize();
        add(modalWindow);
        uploadImageClick();
    }

    private void uploadImageClick() {
        AjaxLink link = new AjaxLink("uploadImage") {
            private static final long serialVersionUID = 5978113764969653661L;

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                modalUploadImage = new ModalUploadImage(ModalWindow.CONTENT_ID);
                modalWindow.setContent(modalUploadImage);
                modalWindow.show(ajaxRequestTarget);
            }
        };
        add(link);
    }

    private void setModalWindowSizeByBrowserSize() {
        getApplication().getRequestCycleSettings().setGatherExtendedBrowserInfo(true);
        WebClientInfo w = WebSession.get().getClientInfo();
        ClientProperties clientProperties = w.getProperties();
        modalWindow.setInitialWidth((int) (clientProperties.getBrowserWidth() * MODAL_WIDTH_MULTIPLIER));
        modalWindow.setInitialHeight((int) (clientProperties.getBrowserHeight() * MODAL_HEIGHT_MULTIPLIER));
    }

    private void makePicturesListView() {
        final PageableListView<Picture> listview = new PageableListView<Picture>("listview", pictures, ITEMS_PER_PAGE) {
            private static final long serialVersionUID = 3263873336191237351L;

            protected void populateItem(final ListItem<Picture> item) {
                picture = item.getModelObject();
                item.add(new Label("uploadDate", DateUtils.convertDateToString(picture.getUploadDate())));
                item.add(new Label("editDate", DateUtils.convertDateToString(picture.getEditDate())));
                item.add(new Label("description", picture.getDescription()));
                item.add(new Label("name", picture.getName()));
                item.add(new Label("quality", makeQualityString(picture.getQuality())));
                AjaxLink link = new AjaxLink("link") {
                    private static final long serialVersionUID = 5978113764969653661L;

                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        modalLargeImage = new ModalLargeImage(modalWindow.getContentId(), item.getModelObject());
                        modalWindow.setContent(modalLargeImage);
                        modalWindow.show(ajaxRequestTarget);
                    }
                };
                AjaxLink editButton = new AjaxLink("editButton") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        modalEditImage = new ModalEditImage(ModalWindow.CONTENT_ID, item.getModelObject());
                        modalWindow.setContent(modalEditImage);
                        modalWindow.show(ajaxRequestTarget);
                    }
                };
                add(link);
                add(editButton);
                link.add(ImageUtils.makeImage("image", item.getModelObject().getSmallImage()));
                item.add(link);
                item.add(editButton);
            }
        };
        add(listview);
    }

    private String makeQualityString(ImageQuality imageQuality) {
        switch (imageQuality) {
            case BAD:
                return getString("badQuality");
            case NORMAL:
                return getString("normalQuality");
            case GOOD:
                return getString("goodQuality");
            case PERFECT:
                return getString("perfectQuality");
            default:
                return getString("noQuality");
        }
    }
}
