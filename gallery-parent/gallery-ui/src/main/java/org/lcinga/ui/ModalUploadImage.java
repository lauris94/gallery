package org.lcinga.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
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

    private static final int STRING_MIN_LENGTH = 5;
    private static final int STRING_MAX_LENGTH = 20;
    private static final int STRING_MAX_LENGTH_DESCRIPTION = 500;
    private static final int IMAGE_BYTES_MIN_LENGTH = 10000;
    private static final int THUMBNAIL_WIDTH = 150;
    private static final int THUMBNAIL_HEIGHT = 150;
    private byte[] imageBytes;
    private FileUploadField fileUploadField;

    @SpringBean
    private PictureService pictureService;

    public ModalUploadImage(String id, IModel compoundPropertyModel, WebMarkupContainer webMarkupContainer, ModalWindow modalWindow) {
        super(id, compoundPropertyModel);
        Form<Picture> form = new Form<Picture>("pictureDetailsInputForm", compoundPropertyModel) {
            private static final long serialVersionUID = 5481335960355058032L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                Picture picture = getModelObject();
                if (fileUploadField.getFileUpload() != null) {
                    imageBytes = ImageUtils.getImageBytesFromInput(fileUploadField);
                    if (imageBytes.length > IMAGE_BYTES_MIN_LENGTH) {
                        PictureSource pictureSource = new PictureSource();
                        pictureSource.setLargeImage(imageBytes);
                        picture.setPictureSource(pictureSource);
                        pictureService.createPicture(picture, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
                    }
                } else if (picture.getPictureSource() != null) {
                    pictureService.createPicture(picture, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
                } else {
                    error("Image was not found!");
                }
            }
        };

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);

        form.add(feedbackPanel);

        form.add(new TextField<>("name").add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH)));
        form.add(new TextArea<>("description").add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH_DESCRIPTION)));
        form.add(new DropDownChoice<>("quality", Arrays.asList(ImageQuality.values()), new EnumChoiceRenderer<>()));

        form.setMultiPart(true);

        fileUploadField = new FileUploadField("largeImage", new Model<>());
        form.add(fileUploadField);

        AjaxSubmitLink submitButton = new AjaxSubmitLink("submitPicture", form) {
            @Override
            protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onAfterSubmit(target, form);
                target.add(feedbackPanel);
                if (!form.hasErrorMessage()) {
                    modalWindow.close(target);
                    setResponsePage(TemplatePage.class);
                }
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(feedbackPanel);
                super.onError(target, form);
            }
        };

        AjaxLink cancelButton = new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                modalWindow.close(ajaxRequestTarget);
            }
        };

        form.add(cancelButton);
        form.add(submitButton);
        add(form);
    }
}
