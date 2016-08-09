package org.lcinga.service;

import org.lcinga.dao.TagDao;
import org.lcinga.model.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lcinga on 2016-08-08.
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Transactional(readOnly = true)
    @Override
    public List<Tag> getAllTags() {
        return tagDao.getAll();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getByName(name);
    }
}
