package org.lcinga.dao.impl;

import org.lcinga.dao.TagDao;
import org.lcinga.model.entities.Tag;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by lcinga on 2016-08-08.
 */
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {
    @Override
    public List<Tag> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> rootEntry = cq.from(Tag.class);
        CriteriaQuery<Tag> all = cq.select(rootEntry);
        TypedQuery<Tag> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
