package org.lcinga.service;

import org.lcinga.dao.UserDao;
import org.lcinga.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Laurynas Cinga on 2016-08-11.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public User findLoggedUser(User user) {
        return userDao.findLoggedUser(user);
    }
}
