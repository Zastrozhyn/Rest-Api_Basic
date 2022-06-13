package com.epam.esm.controller;

import com.epam.esm.converter.impl.OrderModelAssembler;
import com.epam.esm.dto.OrderModel;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.impl.OrderLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;
    private final OrderLinkBuilder linkBuilder;
    private final OrderModelAssembler assembler;

    @Autowired
    public OrderController(OrderService service, OrderLinkBuilder linkBuilder, OrderModelAssembler assembler) {
        this.service = service;
        this.linkBuilder = linkBuilder;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<OrderModel> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                               @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        CollectionModel<OrderModel> orders = assembler.toCollectionModel(service.findAll(page, pageSize));
        orders.forEach(linkBuilder::buildSelfLink);
        linkBuilder.buildAllLinks(orders);
        return orders;
    }

    @GetMapping("/{id}")
    public OrderModel findOrder(@PathVariable Long id){
        OrderModel order = assembler.toModel(service.findOrder(id));
        linkBuilder.buildLinks(order);
        return order;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public OrderModel update(@PathVariable Long id, @RequestBody Order order){
        OrderModel orderModel = assembler.toModel(service.update(order, id));
        linkBuilder.buildLinks(orderModel);
        return orderModel;
    }

}
