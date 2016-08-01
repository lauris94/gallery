package org.lcinga.dao.impl;

import org.lcinga.dao.PictureDao;
import org.lcinga.model.entities.Picture;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by lcinga on 2016-07-26.
 */
public class PictureDaoImpl extends GenericDaoImpl<Picture, Long> implements PictureDao {

    public List<Picture> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Picture> cq = cb.createQuery(Picture.class);
        Root<Picture> rootEntry = cq.from(Picture.class);
        CriteriaQuery<Picture> all = cq.select(rootEntry);
        TypedQuery<Picture> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
