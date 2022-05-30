package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderDao {
    Order create(Order order);
    Order findOrder(Long id);
    List<Order> findAll();
    void delete(Long id);
    Order update(Order order);
}
