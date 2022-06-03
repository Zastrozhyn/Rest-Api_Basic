package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.impl.OrderLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final OrderLinkBuilder linkBuilder;

    @Autowired
    public OrderController(OrderService service, OrderLinkBuilder linkBuilder) {
        this.service = service;
        this.linkBuilder = linkBuilder;
    }


    @GetMapping
    public List<Order> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                               @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        List<Order> orders = service.findAll(page, pageSize);
        orders.forEach(linkBuilder::buildLinks);
        return orders;
    }

    @GetMapping("/{id}")
    public Order findOrder(@PathVariable Long id){
        Order order = service.findOrder(id);
        linkBuilder.buildLinks(order);
        return order;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public Order update(@PathVariable Long id,@RequestBody Order order){
        return service.update(order, id);
    }

}
