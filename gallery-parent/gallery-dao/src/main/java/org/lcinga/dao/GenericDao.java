package org.lcinga.dao;

import java.io.Serializable;

public interface GenericDao<T, PK extends Serializable> {
    T create(T t);

    void delete(PK id);

    T find(PK id);

    T update(T t);
}
