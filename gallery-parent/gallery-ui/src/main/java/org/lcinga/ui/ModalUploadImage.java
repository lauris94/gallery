package org.lcinga.ui;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.entities.PictureSource;
import org.lcinga.model.enums.ImageQuality;
import org.lcinga.service.PictureService;
import org.lcinga.ui.utils.ImageUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by lcinga on 2016-08-03.
 */
public class ModalUploadImage extends Panel {
    private static final long serialVersionUID = -3016593005565028329L;

    private static final int STRING_MIN_LENGTH = 2;
    private static final int STRING_MAX_LENGTH = 20;
    private static final int STRING_MAX_LENGTH_DESCRIPTION = 500;
    private static final int IMAGE_BYTES_MIN_LENGTH = 10000;

    private FileUploadField fileUploadField;
    private TextArea<String> pictureDescription;
    private TextField<String> pictureName;
    private DropDownChoice<ImageQuality> qualityDropDownChoice;
    private byte[] imageBytes;

    @SpringBean
    private PictureService pictureService;

    public ModalUploadImage(String id) {
        super(id);
        Form<Picture> form = new Form<Picture>("pictureDetailsInputForm") {
            private static final long serialVersionUID = 5481335960355058032L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                imageBytes = ImageUtils.getImageBytesFromInput(fileUploadField);
                if (imageBytes.length > IMAGE_BYTES_MIN_LENGTH){
                    createPicture();
                }
                else{
                    System.out.println("Image was not found");
                }
            }
        };

        pictureName = new TextField<>("pictureNameInput", Model.<String>of());
        pictureName.add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH));
        form.add(pictureName);

        pictureDescription = new TextArea<>("descriptionInput", Model.<String>of());
        pictureDescription.add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH_DESCRIPTION));
        form.add(pictureDescription);

        qualityDropDownChoice = new DropDownChoice<>("imageQualityDD", Model.of(),Arrays.asList(ImageQuality.values()), new EnumChoiceRenderer<>());
        form.add(qualityDropDownChoice);

        form.setMultiPart(true);
        form.add(fileUploadField = new FileUploadField("fileInput"));
        form.add(new Button("submitPicture"));
        add(form);
    }

    private void createPicture(){
        Picture picture = new Picture();
        picture.setDescription(pictureDescription.getModelObject());
        picture.setEditDate(new Date());
        picture.setUploadDate(new Date());
        picture.setName(pictureName.getModelObject());
        picture.setQuality(qualityDropDownChoice.getModelObject());
        PictureSource pictureSource = new PictureSource();
        pictureSource.setLargeImage(imageBytes);
        picture.setPictureSource(pictureSource);
        pictureService.createPicture(picture, 200, 200);
    }
}
