package org.lcinga.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lcinga.model.entities.Picture;
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
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm";

    @SpringBean
    private IPictureService iPictureService;

    public ContentPanel(String id) {
        super(id);
        pictures = iPictureService.getAllPictures();
        makePicturesListView();
    }

    private void makePicturesListView() {
        final PageableListView<Picture> listview = new PageableListView<Picture>("listview", pictures, ITEMS_PER_PAGE) {
            private static final long serialVersionUID = 3263873336191237351L;

            protected void populateItem(final ListItem<Picture> item) {
                Picture picture = item.getModelObject();
                item.add(new Label("uploadDate", convertDateToString(picture.getUploadDate())));
                item.add(new Label("editDate", convertDateToString(picture.getEditDate())));
                item.add(new Label("description", picture.getDescription()));
                item.add(new Label("quality", picture.getQuality()));
                AjaxLink link = new AjaxLink("link") {
                    private static final long serialVersionUID = 5978113764969653661L;

                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        //atidaryti popup langa
                    }
                };
                link.add(makeImage(item));
                item.add(link);
            }
        };
        add(listview);
    }

    private String convertDateToString(Date input){
        Instant instant = Instant.ofEpochMilli(input.getTime());
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
        return res.format(formatter);
    }

    private NonCachingImage makeImage(final ListItem item){
        final DynamicImageResource dynamicImageResource = new DynamicImageResource() {
            private static final long serialVersionUID = 7335073635482061137L;

            @Override
            protected byte[] getImageData(Attributes attributes) {
                return ((Picture) item.getModelObject()).getSmallImage();
            }
        };
        AbstractReadOnlyModel abstractReadOnlyModel = new AbstractReadOnlyModel<DynamicImageResource>() {
            private static final long serialVersionUID = -8441070630040720955L;

            @Override
            public DynamicImageResource getObject() {
                return dynamicImageResource;
            }
        };
        return new NonCachingImage("image", abstractReadOnlyModel);
    }
}
