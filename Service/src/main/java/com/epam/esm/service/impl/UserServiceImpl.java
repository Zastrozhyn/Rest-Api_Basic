package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserValidator validator;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    @Override
    public User create(User user) {
        if (isUserNameValid(user)){
            userDao.create(user);
        }
        return user;
    }

    @Override
    public User findUser(Long id) {
        User user = userDao.findUser(id);
        if (user ==  null){
            throw new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void delete(Long id) {
        if (isUserExist(id)){
            userDao.delete(id);
        }
    }

    @Override
    public User update(User user, Long id) {
        if (isUserExist(id) && isUserNameValid(user)){
            user.setId(id);
            userDao.update(user);
        }
        return user;
    }

    private boolean isUserExist(Long id){
        if (userDao.findUser(id) ==  null){
            throw new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    private boolean isUserNameValid(User user){
        if(!validator.isValid(user)){
            throw new EntityException(ExceptionCode.NOT_VALID_USER_NAME.getErrorCode());
        }
        return true;
    }
}
