package org.lcinga.service;

import org.lcinga.model.entities.Picture;

import java.util.List;

/**
 * Created by lcinga on 2016-08-01.
 */
public interface PictureService {
    void createPicture(Picture picture);

    void createPicture(Picture picture, int width, int height);

    Picture updatePicture(Picture picture);

    Picture getPicture(long id);

    List<Picture> getAllPictures();

    void remove(Picture picture);
}
