package org.lcinga.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
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
    private static final int MAX_TAGS_FOR_IMAGE = 3;
    private byte[] imageBytes;
    private FileUploadField fileUploadField;
    private WebMarkupContainer webMarkupContainer;
    private Picture selectedPicture;

    private Tag selectedTag;

    @SpringBean
    private PictureService pictureService;

    @SpringBean
    private TagService tagService;

    public ModalUploadImage(String id, IModel compoundPropertyModel, ModalWindow modalWindow) {
        super(id, compoundPropertyModel);

        webMarkupContainer = new WebMarkupContainer("container");

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
                    error(getString("imageNotFoundError"));
                }
            }
        };

        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);

        TextField<String> name = new TextField<>("name");
        name.add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH));
        name.setRequired(true);

        form.add(new TextArea<>("description").add(new StringValidator(STRING_MIN_LENGTH, STRING_MAX_LENGTH_DESCRIPTION)));

        DropDownChoice qualityDD = new DropDownChoice<>("quality", Arrays.asList(ImageQuality.values()), new EnumChoiceRenderer<>());
        qualityDD.setNullValid(true);

        PropertyListView<Picture> addedTagsListview = new PropertyListView<Picture>("tags") {
            private static final long serialVersionUID = 4597174609935228612L;

            @Override
            protected void populateItem(ListItem<Picture> listItem) {
                listItem.add(new Label("text"));
            }
        };

        List<Tag> availableTags = getAvailableTags();

        DropDownChoice<Tag> availableTagsDropDown = new DropDownChoice<>("availableTags", new PropertyModel<>(this, "selectedTag"), availableTags, new ChoiceRenderer<>("text", "id"));
        availableTagsDropDown.setNullValid(true);
        availableTagsDropDown.add(new OnChangeAjaxBehavior() {
            private static final long serialVersionUID = -4423699054711218043L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
            }
        });

        AjaxLink<Picture> addTagButton = new AjaxLink<Picture>("addTagButton", new Model<>()) {
            private static final long serialVersionUID = -5887808754579473780L;

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                if (selectedTag != null) {
                    if (selectedPicture.getTags() == null) {
                        List<Tag> tagstemp = new ArrayList<>();
                        tagstemp.add(selectedTag);
                        selectedPicture.setTags(tagstemp);
                        availableTags.remove(selectedTag);
                    } else if (selectedPicture.getTags().size() >= MAX_TAGS_FOR_IMAGE) {
                        error(getString("reachedMaxTags"));
                    } else {
                        selectedPicture.getTags().add(selectedTag);
                        availableTags.remove(selectedTag);
                    }
                    selectedTag = null;
                    ajaxRequestTarget.add(webMarkupContainer);
                } else {
                    error(getString("tagNotSelectedError"));
                }
                ajaxRequestTarget.add(feedbackPanel);
            }
        };

        AjaxLink<Picture> removeTagButton = new AjaxLink<Picture>("removeTagButton", new Model<>()) {
            private static final long serialVersionUID = -5887808754579473780L;

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                if (selectedPicture.getTags() != null && !selectedPicture.getTags().isEmpty()) {
                    selectedPicture.getTags().clear();
                    availableTags.clear();
                    availableTags.addAll(tagService.getAllTags());
                    ajaxRequestTarget.add(webMarkupContainer);
                } else {
                    ajaxRequestTarget.add(feedbackPanel);
                    error(getString("removeTagError"));
                }
            }
        };

        fileUploadField = new FileUploadField("largeImage", new Model<>());

        AjaxSubmitLink submitButton = new AjaxSubmitLink("submitPicture", form) {
            private static final long serialVersionUID = 3342574116572820400L;

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

        form.setMultiPart(true);
        add(form);
        form.add(name);
        form.add(qualityDD);
        form.add(feedbackPanel);
        form.add(cancelButton);
        form.add(submitButton);
        form.add(fileUploadField);
        form.add(webMarkupContainer);
        webMarkupContainer.add(addedTagsListview);
        webMarkupContainer.add(availableTagsDropDown);
        webMarkupContainer.add(addTagButton);
        webMarkupContainer.add(removeTagButton);
        webMarkupContainer.setOutputMarkupId(true);
    }

    private List<Tag> getAvailableTags() {
        List<Tag> allTags = tagService.getAllTags();
        selectedPicture = (Picture) getDefaultModelObject();
        if (selectedPicture.getId() != null) {
            List<Tag> tags = selectedPicture.getTags();
            allTags.removeAll(tags);
            return allTags;
        }
        return allTags;
    }

    public abstract void onUpload(AjaxRequestTarget target);

    public Tag getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(Tag selectedTag) {
        this.selectedTag = selectedTag;
    }
}
