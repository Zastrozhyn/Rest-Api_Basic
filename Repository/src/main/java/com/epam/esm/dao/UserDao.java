package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserWithTotalCost;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    User create(User user);
    User findById(Long id);
    List<User> findAll(Integer offset, Integer limit);
    void delete(Long id);
    User update(User user);
    List<Tag> getMostPopularTag();
    BigDecimal findTotalCost(Long id);
    List<UserWithTotalCost> getUsersWithTotalCost();

    boolean exists(Long id);
}
