package com.epam.esm.controller;

import com.epam.esm.assembler.OrderModelAssembler;
import com.epam.esm.assembler.UserModelAssembler;
import com.epam.esm.entity.User;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.UserModel;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final OrderService orderService;
    private final UserModelAssembler assembler;
    private final OrderModelAssembler orderModelAssembler;

    @Autowired
    public UserController(UserService service, OrderService orderService, UserModelAssembler assembler,
                          OrderModelAssembler orderModelAssembler) {
        this.service = service;
        this.orderService = orderService;
        this.assembler = assembler;
        this.orderModelAssembler = orderModelAssembler;
    }

    @GetMapping
    public CollectionModel<UserModel> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        return assembler.toCollectionModel(service.findAll(pageSize, page));
    }

    @GetMapping("/{id}")
    public UserModel findUser(@PathVariable Long id){
        return assembler.toModelWithLinks(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public UserModel update(@PathVariable Long id, @RequestBody User user){
        return assembler.toModel(service.update(user, id));
    }

    @PostMapping
    public UserModel create(@RequestBody User user){
        return assembler.toModelWithLinks(service.create(user));
    }

    @PostMapping("{id}/orders")
    public OrderModel createOrder(@PathVariable Long id, @RequestBody List<Long> certificates ){
        return orderModelAssembler.toModelWithAllLinks(orderService.create(id, certificates));
    }

    @GetMapping("{id}/orders")
    public CollectionModel<OrderModel> findAllUsersOrder(@PathVariable Long id,
                                                    @RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                                    @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        return orderModelAssembler.toCollectionModel(orderService.findAllUsersOrder(id, page, pageSize));
    }
}
