package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserWithTotalCost;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    UserDto create(UserDto user);
    UserDto findUser(Long id);
    List<UserDto> findAll(Integer pageSize, Integer page);
    void delete(Long id);
    UserDto update(UserDto user, Long id);
    boolean isUserExist(User user);
    List<Tag> getMostPopularTag();
    BigDecimal findTotalCost(Long id);

    List<UserWithTotalCost> getUsersWithTotalCost();
}
