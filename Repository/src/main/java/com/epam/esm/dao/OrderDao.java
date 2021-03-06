package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;

public interface OrderDao {
    Order create(Order order);
    Order findById(Long id);
    void delete(Long id);
    Order update(Order order);
    List<Order> findAllUsersOrder(User user, Integer offset, Integer limit);
    List<Order> findAll(Integer offset, Integer limit);
    boolean exists(Long id);
}
