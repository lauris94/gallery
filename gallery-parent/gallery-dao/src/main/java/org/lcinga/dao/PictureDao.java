package org.lcinga.dao;

import org.lcinga.model.dto.PictureSearch;
import org.lcinga.model.entities.Picture;

import java.util.List;

/**
 * Created by lcinga on 2016-07-26.
 */
public interface PictureDao extends GenericDao<Picture, Long> {
    List<Picture> getAll();

    List<Picture> search(PictureSearch pictureSearchObject);
}