package org.lcinga.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.enums.ImageQuality;
import org.lcinga.service.IPictureService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm";
    private ModalWindow modalWindow;
    private ModalLargeImage modalLargeImage;
    private Picture picture;

    @SpringBean
    private IPictureService iPictureService;

    public ContentPanel(String id) {
        super(id);
        pictures = iPictureService.getAllPictures();
        makePicturesListView();
        modalWindow = new ModalWindow("modalWindow");
        setModalWindowSizeByBrowserSize();
        add(modalWindow);
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
                item.add(new Label("uploadDate", convertDateToString(picture.getUploadDate())));
                item.add(new Label("editDate", convertDateToString(picture.getEditDate())));
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
                link.add(makeImage("image", item.getModelObject().getSmallImage()));
                item.add(link);
            }
        };
        add(listview);
    }

    private String convertDateToString(Date input) {
        Instant instant = Instant.ofEpochMilli(input.getTime());
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
        return res.format(formatter);
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

    private NonCachingImage makeImage(String tagID, final byte[] item) {
        final DynamicImageResource dynamicImageResource = new DynamicImageResource() {
            private static final long serialVersionUID = 7335073635482061137L;

            @Override
            protected byte[] getImageData(Attributes attributes) {
                return item;
            }
        };
        AbstractReadOnlyModel abstractReadOnlyModel = new AbstractReadOnlyModel<DynamicImageResource>() {
            private static final long serialVersionUID = -8441070630040720955L;

            @Override
            public DynamicImageResource getObject() {
                return dynamicImageResource;
            }
        };
        return new NonCachingImage(tagID, abstractReadOnlyModel);
    }
}