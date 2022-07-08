package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService {
    Order create(Long userId, List<Long> certificates);
    Order findById(Long id);
    void delete(Long id);
    List<Order> findAllUsersOrder(Long id, Integer page, Integer pageSize);
    Order update(Order order, Long id);
    List<Order> findAll(Integer page, Integer pageSize);
}
