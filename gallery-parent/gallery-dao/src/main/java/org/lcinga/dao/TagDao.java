package org.lcinga.dao;

import org.lcinga.model.entities.Tag;

import java.util.List;

/**
 * Created by lcinga on 2016-08-08.
 */
public interface TagDao extends GenericDao<Tag, Long> {
    List<Tag> getAll();
}
