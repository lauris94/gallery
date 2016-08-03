package org.lcinga.ui;

import org.apache.wicket.markup.html.form.Form;
import org.lcinga.model.entities.Picture;
import org.lcinga.ui.utils.ImageUtils;

/**
 * Created by lcinga on 2016-08-03.
 */
public class ModalEditImage extends ModalUploadImage {
    private static final long serialVersionUID = 4429505542688799769L;

    public ModalEditImage(String id, Picture picture) {
        super(id);

        Form<?> form = (Form<?>) get("pictureDetailsInputForm");

        //form.remove("submitPicture");

        form.add(ImageUtils.makeImage("imagePreview", picture.getSmallImage()));
    }
}
