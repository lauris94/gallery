package org.lcinga.ui.Utils;

import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.resource.DynamicImageResource;

/**
 * Created by lcinga on 2016-08-03.
 */
public class ImageUtils {

    public static NonCachingImage makeImage(String tagID, final byte[] item) {
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
