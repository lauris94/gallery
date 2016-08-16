package org.lcinga.ui.utils;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.util.file.Files;
import org.lcinga.ui.ModalUploadImage;

import java.io.File;

/**
 * Created by lcinga on 2016-08-03.
 */
public class ImageUtils {

    private static final Logger logger = Logger.getLogger(ModalUploadImage.class);

    private ImageUtils() {
    }

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

    public static byte[] getImageBytesFromInput(FileUploadField fileUploadField) {
        final FileUpload uploadedImage = fileUploadField.getFileUpload();
        try {
            File newFile = new File(uploadedImage.getClientFileName());
            uploadedImage.writeTo(newFile);
            return Files.readBytes(newFile);
        } catch (Exception e) {
            logger.error("Error on creating image");
        }
        return null;
    }
}
