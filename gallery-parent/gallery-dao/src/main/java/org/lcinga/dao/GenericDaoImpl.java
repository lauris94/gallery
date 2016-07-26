package org.lcinga.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lcinga on 2016-07-26.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    @PersistenceContext
    private EntityManager em;

    private Class<T> type;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    public T create(T t) {
        this.em.persist(t);
        return t;
    }

    public void delete(Object id) {
        this.em.remove(this.em.getReference(type, id));
    }

    public T find(Object id) {
        return (T) this.em.find(type, id);
    }

    public T update(T t) {
        return this.em.merge(t);
    }
}
