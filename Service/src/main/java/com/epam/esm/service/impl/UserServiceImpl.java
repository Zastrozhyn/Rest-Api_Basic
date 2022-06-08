package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.TagDtoConverter;
import com.epam.esm.converter.impl.UserDtoConverter;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
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
    private final UserDtoConverter converter;
    private final TagDtoConverter tagDtoConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserValidator validator, UserDtoConverter converter,
                           TagDtoConverter tagDtoConverter) {
        this.userDao = userDao;
        this.validator = validator;
        this.converter = converter;
        this.tagDtoConverter = tagDtoConverter;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = converter.convertFromDto(userDto);
        isUserNameValid(user);
        return converter.convertToDto(userDao.create(user));
    }

    @Override
    public UserDto findUser(Long id) {
        User user = userDao.findUser(id);
        isUserExist(user);
        return converter.convertToDto(user);
    }

    @Override
    public List<UserDto> findAll(Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return userDao.findAll(calculateOffset(pageSize,page), pageSize)
                .stream()
                .map(converter::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (isUserExist(userDao.findUser(id))){
            userDao.delete(id);
        }
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto, Long id) {
        User user = converter.convertFromDto(userDto);
        if (isUserExist(userDao.findUser(id)) && isUserNameValid(user)){
            user.setId(id);

        }
        return converter.convertToDto(userDao.update(user));
    }

    @Override
    public boolean isUserExist(User user){
        if (user ==  null){
            throw new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    @Override
    public List<TagDto> getMostPopularTag(){
        return userDao.getMostPopularTag()
                .stream()
                .map(tagDtoConverter::convertToDto)
                .toList();
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
