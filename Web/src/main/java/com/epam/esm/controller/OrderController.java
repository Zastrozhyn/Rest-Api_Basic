package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Order findOrder(@PathVariable Long id){
        return service.findOrder(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public Order update(@PathVariable Long id,@RequestBody Order order){
        return service.update(order, id);
    }

    @PostMapping
    public Order create(@RequestBody Order order){
        return service.create(order);
    }
}
