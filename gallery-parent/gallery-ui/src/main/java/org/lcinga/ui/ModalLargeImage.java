package org.lcinga.ui;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.lcinga.model.entities.Picture;
import org.lcinga.service.IPictureService;

/**
 * Created by lcinga on 2016-08-02.
 */
public class ModalLargeImage extends WebPage {
    private static final long serialVersionUID = -7421757455793189797L;

    @SpringBean
    private IPictureService iPictureService;

    private Picture picture;

    public ModalLargeImage(final PageParameters pageParameters) {
        StringValue a = pageParameters.get("imageId");
        //Long imageID = a.toLong();
        //picture = iPictureService.getPicture(imageID);
        //picture.getPictureSource().getLargeImage();
    }
}
