package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    User create(User user);
    User findById(Long id);

    User findByName(String name);

    List<User> findAll(Integer offset, Integer limit);
    void delete(Long id);
    User update(User user);
    List<Tag> getMostPopularTag();
    BigDecimal findTotalCost(Long id);
    boolean exists(Long id);
}
