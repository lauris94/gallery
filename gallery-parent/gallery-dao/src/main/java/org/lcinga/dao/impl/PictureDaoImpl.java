package org.lcinga.dao.impl;

import org.lcinga.dao.PictureDao;
import org.lcinga.model.dto.PictureSearch;
import org.lcinga.model.entities.Picture;
import org.lcinga.model.entities.Picture_;
import org.lcinga.model.entities.Tag;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcinga on 2016-07-26.
 */
public class PictureDaoImpl extends GenericDaoImpl<Picture, Long> implements PictureDao {



    public List<Picture> getAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Picture> criteriaQuery = criteriaBuilder.createQuery(Picture.class);
        Root<Picture> rootEntry = criteriaQuery.from(Picture.class);
        CriteriaQuery<Picture> all = criteriaQuery.select(rootEntry);
        TypedQuery<Picture> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public List<Picture> search(PictureSearch pictureSearchObject) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Picture> criteriaQuery = criteriaBuilder.createQuery(Picture.class);
        Root<Picture> root = criteriaQuery.from(Picture.class);

        List<Predicate> predicates = buildPredicates(pictureSearchObject, criteriaBuilder, root, criteriaQuery);
        criteriaQuery.where(toArray(predicates));
        criteriaQuery.distinct(true);
        List<Picture> list = em.createQuery(criteriaQuery).getResultList();
        return list;
    }

    private List<Predicate> buildPredicates(PictureSearch pictureSearchObject, CriteriaBuilder criteriaBuilder, Root<Picture> root, CriteriaQuery<Picture> criteriaQuery) {
        List<Predicate> predicates = new ArrayList<>();

        if (pictureSearchObject.getTextInput() != null && !pictureSearchObject.getTextInput().isEmpty() && pictureSearchObject.getSearchByNameStatus().equals(PictureSearch.SearchByNameStatus.WITHOUT_LIKE)){
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(root.get(Picture_.name)), pictureSearchObject.getTextInput().toUpperCase()));
        }
        if (pictureSearchObject.getTextInput() != null && !pictureSearchObject.getTextInput().isEmpty() && pictureSearchObject.getSearchByNameStatus().equals(PictureSearch.SearchByNameStatus.WITH_LIKE)){
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(Picture_.name)), pictureSearchObject.getTextInput().toUpperCase() + "%"));
        }

        if (pictureSearchObject.getSelectedTags().size() > 0){
            //pictureSearchObject.getSelectedTags().forEach(tag -> makeTagPredicates(tag, criteriaBuilder, root, criteriaQuery));       todo search by selected tags

        }
        return predicates;
    }

    private void makeTagPredicates(Tag tag, CriteriaBuilder criteriaBuilder, Root<Picture> root, CriteriaQuery<Picture> criteriaQuery) {

    }


    public static Predicate[] toArray(List<Predicate> predicates) {
        if (predicates == null) {
            throw new IllegalArgumentException("Parameter predicates is required");
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
