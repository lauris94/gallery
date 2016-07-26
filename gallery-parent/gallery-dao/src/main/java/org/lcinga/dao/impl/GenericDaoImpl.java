package org.lcinga.dao.impl;

import org.lcinga.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lcinga on 2016-07-26.
 */
public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
    @PersistenceContext
    protected EntityManager em;

    private Class<T> type;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    public T create(T t) {
        em.persist(t);
        return t;
    }

    public void delete(PK id) {
        em.remove(this.em.getReference(type, id));
    }

    public T find(PK id) {
        return (T) em.find(type, id);
    }

    public T update(T t) {
        return em.merge(t);
    }
}
