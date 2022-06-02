package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;

public interface UserDao {
    User create(User user);
    User findUser(Long id);
    List<User> findAll(Integer offset, Integer limit);
    void delete(Long id);
    User update(User user);
}
