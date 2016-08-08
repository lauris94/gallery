package org.lcinga.ui;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
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

    private static final int ITEMS_PER_PAGE = 4;
    private static final Double MODAL_WIDTH_MULTIPLIER_IMAGE = 0.6;
    private static final Double MODAL_HEIGHT_MULTIPLIER_IMAGE = 0.8;
    private static final Double MODAL_WIDTH_MULTIPLIER = 0.3;
    private static final Double MODAL_HEIGHT_MULTIPLIER = 0.5;
    private ModalWindow modalWindow;
    private ModalLargeImage modalLargeImage;
    private ModalUploadImage modalUploadImage;
    private ModalUploadImage modalEditImage;
    private WebMarkupContainer webMarkupContainer;
    private LoadableDetachableModel imageListModel;

    @SpringBean
    private PictureService pictureService;

    public ContentPanel(String id) {
        super(id);
        webMarkupContainer = new WebMarkupContainer("container");
        webMarkupContainer.setOutputMarkupId(true);

        imageListModel = new LoadableDetachableModel() {
            private static final long serialVersionUID = -873256375646275185L;

            @Override
            protected Object load() {
                return pictureService.getAllPictures();
            }
        };

        makePicturesListView();
        modalWindow = new ModalWindow("modalWindow");
        add(modalWindow);
        uploadImageClick();
        add(webMarkupContainer);
    }

    private void uploadImageClick() {
        AjaxLink link = new AjaxLink("uploadImage") {
            private static final long serialVersionUID = 5978113764969653661L;

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                setModalWindowSize(MODAL_WIDTH_MULTIPLIER, MODAL_HEIGHT_MULTIPLIER);
                modalUploadImage = new ModalUploadImage(ModalWindow.CONTENT_ID, new CompoundPropertyModel<>(new Picture()), modalWindow) {
                    private static final long serialVersionUID = 1388666203503740698L;

                    @Override
                    public void onUpload(AjaxRequestTarget target) {
                        imageListModel.detach();
                        target.add(webMarkupContainer);
                    }
                };
                modalWindow.setContent(modalUploadImage);
                modalWindow.show(ajaxRequestTarget);
            }
        };
        webMarkupContainer.add(link);
    }

    private void setModalWindowSize(double width, double height) {
        getApplication().getRequestCycleSettings().setGatherExtendedBrowserInfo(true);
        WebClientInfo w = WebSession.get().getClientInfo();
        ClientProperties clientProperties = w.getProperties();
        modalWindow.setInitialWidth((int) (clientProperties.getBrowserWidth() * width));
        modalWindow.setInitialHeight((int) (clientProperties.getBrowserHeight() * height));
    }

    private void makePicturesListView() {

        final PageableListView<Picture> listView = new PageableListView<Picture>("listview", imageListModel, ITEMS_PER_PAGE) {
            private static final long serialVersionUID = 3263873336191237351L;

            protected void populateItem(final ListItem<Picture> item) {

                Picture picture = item.getModelObject();
                item.add(new Label("uploadDate", DateUtils.convertDateToString(picture.getUploadDate())));
                item.add(new Label("editDate", ObjectUtils.defaultIfNull(DateUtils.convertDateToString(picture.getEditDate()), "-")));
                item.add(new Label("description", ObjectUtils.defaultIfNull(picture.getDescription(), "-")));
                item.add(new Label("name", ObjectUtils.defaultIfNull(picture.getName(), "-")));
                item.add(new Label("quality", ObjectUtils.defaultIfNull(makeQualityString(picture.getQuality()), "-")));
                AjaxLink link = new AjaxLink("link") {
                    private static final long serialVersionUID = 5978113764969653661L;

                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        setModalWindowSize(MODAL_WIDTH_MULTIPLIER_IMAGE, MODAL_HEIGHT_MULTIPLIER_IMAGE);
                        modalLargeImage = new ModalLargeImage(modalWindow.getContentId(), item.getModelObject());
                        modalWindow.setContent(modalLargeImage);
                        modalWindow.show(ajaxRequestTarget);
                    }
                };
                AjaxLink editButton = new AjaxLink("editButton") {
                    private static final long serialVersionUID = -207801702236802329L;

                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        setModalWindowSize(MODAL_WIDTH_MULTIPLIER, MODAL_HEIGHT_MULTIPLIER);
                        modalEditImage = new ModalUploadImage(ModalWindow.CONTENT_ID, new CompoundPropertyModel<>(item.getModelObject()), modalWindow) {
                            private static final long serialVersionUID = -2950275701529876148L;

                            @Override
                            public void onUpload(AjaxRequestTarget target) {
                                imageListModel.detach();
                                target.add(webMarkupContainer);
                            }
                        };
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

        CustomPagingNavigator pager = new CustomPagingNavigator("pager", listView) {
            private static final long serialVersionUID = -8365301612181923459L;

            @Override
            protected void onAjaxEvent(AjaxRequestTarget target) {
                target.add(webMarkupContainer);
            }
        };

        listView.setOutputMarkupId(true);
        webMarkupContainer.add(pager);
        webMarkupContainer.add(listView);
    }

    private String makeQualityString(ImageQuality imageQuality) {
        if (imageQuality == null) {
            return null;
        }
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
