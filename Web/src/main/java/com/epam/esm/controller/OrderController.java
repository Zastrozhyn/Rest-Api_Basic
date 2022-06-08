package com.epam.esm.controller;

import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.OrderDto;
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
    public CustomPage<OrderDto> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                     @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        List<OrderDto> orders = service.findAll(page, pageSize);
        orders.forEach(linkBuilder::buildSelfLink);
        CustomPage<OrderDto> customPage = new CustomPage<>(orders, page, pageSize);
        linkBuilder.buildAllLinks(customPage);
        return customPage;
    }

    @GetMapping("/{id}")
    public OrderDto findOrder(@PathVariable Long id){
        OrderDto order = service.findOrder(id);
        linkBuilder.buildLinks(order);
        return order;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public OrderDto update(@PathVariable Long id,@RequestBody OrderDto order){
        OrderDto orderDto = service.update(order, id);
        linkBuilder.buildLinks(orderDto);
        return orderDto;
    }

}
