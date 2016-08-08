package org.lcinga.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.entities.PictureSource;
import org.lcinga.model.entities.Tag;
import org.lcinga.model.enums.ImageQuality;
import org.lcinga.service.PictureService;
import org.lcinga.service.TagService;
import org.lcinga.ui.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lcinga on 2016-08-03.
 */
public abstract class ModalUploadImage extends Panel {
    private static final long serialVersionUID = -3016593005565028329L;

    private static final int STRING_MIN_LENGTH = 5;
    private static final int STRING_MAX_LENGTH = 20;
    private static final int STRING_MAX_LENGTH_DESCRIPTION = 150;
    private static final int IMAGE_BYTES_MIN_LENGTH = 10000;
    private static final int THUMBNAIL_WIDTH = 150;
    private static final int THUMBNAIL_HEIGHT = 150;
    private byte[] imageBytes;
    private FileUploadField fileUploadField;

    @SpringBean
    private PictureService pictureService;

    @SpringBean
    private TagService tagService;

    public ModalUploadImage(String id, IModel compoundPropertyModel, ModalWindow modalWindow) {
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

        TextField<String> name = new TextField<>("name");
        name.add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH));
        name.setRequired(true);

        form.add(name);
        form.add(new TextArea<>("description").add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH_DESCRIPTION)));
        form.add(new DropDownChoice<>("quality", Arrays.asList(ImageQuality.values()), new EnumChoiceRenderer<>()));

        form.setMultiPart(true);

        List<Tag> tagsList = tagService.getAllTags();






        fileUploadField = new FileUploadField("largeImage", new Model<>());
        form.add(fileUploadField);

        AjaxSubmitLink submitButton = new AjaxSubmitLink("submitPicture", form) {
            @Override
            protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onAfterSubmit(target, form);
                target.add(feedbackPanel);
                if (!form.hasErrorMessage()) {
                    modalWindow.close(target);
                    onUpload(target);
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

    public abstract void onUpload(AjaxRequestTarget target);
}
