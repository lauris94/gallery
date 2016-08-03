package org.lcinga.ui;

import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.lcinga.model.enums.ImageQuality;

import java.util.Arrays;

/**
 * Created by lcinga on 2016-08-03.
 */
public class ModalUploadImage extends Panel {
    public ModalUploadImage(String id) {
        super(id);

        Form<?> form = new Form<Void>("pictureDetailsInputForm");

        final TextField<String> pictureName = new TextField<>("pictureNameInput");
        form.add(pictureName);

        final TextArea<String> pictureDescription = new TextArea<>("descriptionInput");
        form.add(pictureDescription);

        DropDownChoice<ImageQuality> ddc = new DropDownChoice<>("imageQualityDD", Arrays.asList(ImageQuality.values()), new EnumChoiceRenderer<ImageQuality>(this));
        form.add(ddc);

        add(form);
    }

    public void onSubmitButtonClick() {

    }
}
