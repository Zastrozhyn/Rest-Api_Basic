package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto create(Long userId, List<Long> certificates);
    OrderDto findOrder(Long id);
    void delete(Long id);
    List<OrderDto> findAllUsersOrder(Long id, Integer page, Integer pageSize);
    OrderDto update(OrderDto order, Long id);
    List<OrderDto> findAll(Integer page, Integer pageSize);
}
