package org.lcinga.dao.impl;

import org.lcinga.dao.UserDao;
import org.lcinga.model.entities.User;
import org.lcinga.model.entities.User_;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 * Created by Laurynas Cinga on 2016-08-11.
 */
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @Override
    public User findLoggedUser(User user) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootEntry = criteriaQuery.from(User.class);
        CriteriaQuery<User> all = criteriaQuery.select(rootEntry);
        all.where(criteriaBuilder.and(criteriaBuilder.equal(rootEntry.get(User_.username), user.getUsername()), criteriaBuilder.equal(rootEntry.get(User_.password), user.getPassword())));
        TypedQuery<User> allQuery = em.createQuery(all);
        try {
            return allQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootEntry = criteriaQuery.from(User.class);
        CriteriaQuery<User> all = criteriaQuery.select(rootEntry);
        all.where(criteriaBuilder.equal(rootEntry.get(User_.username), username));
        TypedQuery<User> allQuery = em.createQuery(all);
        try {
            return allQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
