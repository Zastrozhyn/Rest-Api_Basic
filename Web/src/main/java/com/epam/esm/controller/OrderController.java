package com.epam.esm.controller;

import com.epam.esm.assembler.OrderModelAssembler;
import com.epam.esm.exception.model.OrderModel;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    private final OrderModelAssembler assembler;

    @Autowired
    public OrderController(OrderService service, OrderModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<OrderModel> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                               @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        return assembler.toCollectionModel(service.findAll(page, pageSize));
    }

    @GetMapping("/{id}")
    public OrderModel findOrder(@PathVariable Long id){
        return assembler.toModelWithAllLinks(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public OrderModel update(@PathVariable Long id, @RequestBody Order order){;
        return assembler.toModelWithAllLinks(service.update(order, id));
    }
}
