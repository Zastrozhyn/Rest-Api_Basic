package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserWithTotalCost;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    User create(User user);
    User findUser(Long id);
    List<User> findAll(Integer pageSize, Integer page);
    void delete(Long id);
    User update(User user, Long id);
    boolean isUserExist(User user);
    List<Tag> getMostPopularTag();
    BigDecimal findTotalCost(Long id);
    List<UserWithTotalCost> getUsersWithTotalCost();
    void create1000();
}
