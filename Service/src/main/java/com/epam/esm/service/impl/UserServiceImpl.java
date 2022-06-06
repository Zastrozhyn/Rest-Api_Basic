package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserWithTotalCost;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.esm.util.ApplicationUtil.*;

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
    @Transactional
    public UserDto create(UserDto user) {
        isUserNameValid(user.convertToUser());
        return new UserDto(userDao.create(user.convertToUser()));
    }

    @Override
    public UserDto findUser(Long id) {
        User user = userDao.findUser(id);
        isUserExist(user);
        return new UserDto(user);
    }

    @Override
    public List<UserDto> findAll(Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return userDao.findAll(calculateOffset(pageSize,page), pageSize)
                .stream()
                .map(UserDto::new)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (isUserExist(userDao.findUser(id))){
            userDao.delete(id);
        }
    }

    @Override
    @Transactional
    public UserDto update(UserDto user, Long id) {

        if (isUserExist(userDao.findUser(id)) && isUserNameValid(user.convertToUser())){
            user.setId(id);
            userDao.update(user.convertToUser());
        }
        return user;
    }

    @Override
    public boolean isUserExist(User user){
        if (user ==  null){
            throw new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    @Override
    public List<Tag> getMostPopularTag(){
        return userDao.getMostPopularTag();
    }

    @Override
    public BigDecimal findTotalCost(Long id) {
        return userDao.findTotalCost(id);
    }

    @Override
    public List<UserWithTotalCost> getUsersWithTotalCost() {
        return userDao.getUsersWithTotalCost();
    }

    private boolean isUserNameValid(User user){
        if(!validator.isValid(user)){
            throw new EntityException(ExceptionCode.NOT_VALID_USER_NAME.getErrorCode());
        }
        return true;
    }
}
