package org.lcinga.ui;

import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.entities.PictureSource;
import org.lcinga.model.enums.ImageQuality;
import org.lcinga.service.PictureService;
import org.lcinga.ui.utils.ImageUtils;

import java.util.Arrays;

/**
 * Created by lcinga on 2016-08-03.
 */
public class ModalUploadImage extends Panel {
    private static final long serialVersionUID = -3016593005565028329L;

    private static final int STRING_MIN_LENGTH = 2;
    private static final int STRING_MAX_LENGTH = 20;
    private static final int STRING_MAX_LENGTH_DESCRIPTION = 500;
    private static final int IMAGE_BYTES_MIN_LENGTH = 10000;
    private byte[] imageBytes;
    private FileUploadField fileUploadField;
    private Picture picture;

    @SpringBean
    private PictureService pictureService;

    public ModalUploadImage(String id, CompoundPropertyModel compoundPropertyModel) {
        super(id, compoundPropertyModel);
        Form<Picture> form = new Form<Picture>("pictureDetailsInputForm", compoundPropertyModel) {
            private static final long serialVersionUID = 5481335960355058032L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                picture = getModelObject();

                if (fileUploadField.getFileUpload() != null) {
                    imageBytes = ImageUtils.getImageBytesFromInput(fileUploadField);
                    if (imageBytes.length > IMAGE_BYTES_MIN_LENGTH) {
                        PictureSource pictureSource = new PictureSource();
                        pictureSource.setLargeImage(imageBytes);
                        picture.setPictureSource(pictureSource);
                        pictureService.createPicture(picture, 200, 200);
                    }
                } else  if (picture.getPictureSource().getLargeImage() != null){
                    pictureService.createPicture(picture, 200, 200);
                }else{
                    System.out.println("Image was not found");
                }
            }
        };

        form.add(new TextField<>("name").add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH)));
        form.add(new TextArea<>("description").add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH_DESCRIPTION)));
        form.add(new DropDownChoice<>("quality", Arrays.asList(ImageQuality.values()), new EnumChoiceRenderer<>()));

        form.setMultiPart(true);
        fileUploadField = new FileUploadField("largeImage", new Model<>());
        form.add(fileUploadField);

        form.add(new Button("submitPicture"));
        add(form);
    }
}
