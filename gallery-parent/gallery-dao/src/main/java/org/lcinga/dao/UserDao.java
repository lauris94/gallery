package org.lcinga.dao;

import org.lcinga.model.entities.User;

import java.util.List;

/**
 * Created by Laurynas Cinga on 2016-08-11.
 */
public interface UserDao extends GenericDao<User, Long> {
    User findLoggedUser(User user);
    User findByUsername (String username);
}
