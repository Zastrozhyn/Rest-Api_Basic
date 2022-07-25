package com.epam.esm.service;

import com.epam.esm.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User findById(Long id);
    User findByName(String name);
    List<User> findAll(Integer pageSize, Integer page);
    void delete(Long id);
    User update(User user, Long id);
    boolean isUserExist(Long userId);
}
