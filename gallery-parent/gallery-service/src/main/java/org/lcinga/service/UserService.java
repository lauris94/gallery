package org.lcinga.service;

import org.lcinga.model.entities.User;

/**
 * Created by Laurynas Cinga on 2016-08-11.
 */
public interface UserService {
    User findLoggedUser(User user);
}