package org.lcinga.ui;

import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.lcinga.model.entities.Picture;

/**
 * Created by lcinga on 2016-08-02.
 */
public class ModalLargeImage extends Panel {
    private static final long serialVersionUID = -7421757455793189797L;

    public ModalLargeImage(String id, Picture picture) {
        super(id);
        add(makeImage("image", picture.getPictureSource().getLargeImage()));
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
