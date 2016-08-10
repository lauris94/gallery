package org.lcinga.service;

import org.lcinga.dao.PictureDao;
import org.lcinga.model.dto.PictureSearch;
import org.lcinga.model.entities.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lcinga on 2016-08-10.
 */
@Transactional
@Service
public class PictureSearchServiceImpl implements PictureSearchService {

    private final PictureDao pictureDao;

    @Autowired
    public PictureSearchServiceImpl(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }

    @Override
    public List<Picture> search(PictureSearch pictureSearchObject) {
        return pictureDao.search(pictureSearchObject);
    }
}
