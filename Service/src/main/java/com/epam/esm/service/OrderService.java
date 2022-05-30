package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    Order findOrder(Long id);
    List<Order> findAll();
    void delete(Long id);
    Order update(Order order, Long id);
}
