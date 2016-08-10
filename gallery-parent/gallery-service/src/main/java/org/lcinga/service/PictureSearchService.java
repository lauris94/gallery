package org.lcinga.service;

import org.lcinga.model.dto.PictureSearch;
import org.lcinga.model.entities.Picture;

import java.util.List;

/**
 * Created by lcinga on 2016-08-10.
 */
public interface PictureSearchService {
    List<Picture> search(PictureSearch pictureSearchObject);
}
