package com.epam.esm.controller;

import com.epam.esm.converter.impl.OrderModelAssembler;
import com.epam.esm.converter.impl.TagModelAssembler;
import com.epam.esm.converter.impl.UserModelAssembler;
import com.epam.esm.dto.OrderModel;
import com.epam.esm.dto.TagModel;
import com.epam.esm.dto.UserModel;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserWithTotalCost;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.impl.OrderLinkBuilder;
import com.epam.esm.util.impl.TagLinkBuilder;
import com.epam.esm.util.impl.UserLinkBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final OrderService orderService;
    private  final UserLinkBuilder linkBuilder;
    private final OrderLinkBuilder orderLinkBuilder;
    private final TagLinkBuilder tagLinkBuilder;
    private final UserModelAssembler assembler;
    private final OrderModelAssembler orderModelAssembler;
    private final TagModelAssembler tagModelAssembler;

    @Autowired
    public UserController(UserService service, OrderService orderService, UserLinkBuilder linkBuilder,
                          OrderLinkBuilder orderLinkBuilder, TagLinkBuilder tagLinkBuilder,
                          UserModelAssembler assembler, OrderModelAssembler orderModelAssembler,
                          TagModelAssembler tagModelAssembler) {
        this.service = service;
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
        this.orderLinkBuilder = orderLinkBuilder;
        this.tagLinkBuilder = tagLinkBuilder;
        this.assembler = assembler;
        this.orderModelAssembler = orderModelAssembler;
        this.tagModelAssembler = tagModelAssembler;
    }

    @GetMapping
    public CollectionModel<UserModel> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                              @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        CollectionModel<UserModel> users = assembler.toCollectionModel(service.findAll(pageSize, page));
        users.forEach(linkBuilder::buildSelfLink);
        linkBuilder.buildAllLinks(users);
        return users;
    }

    @GetMapping("/{id}")
    public UserModel findUser(@PathVariable Long id){
        UserModel user = assembler.toModel(service.findUser(id));
        linkBuilder.buildLinks(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public UserModel update(@PathVariable Long id, @RequestBody User user){
        UserModel userModel = assembler.toModel(service.update(user, id));
        linkBuilder.buildLinks(userModel);
        return userModel;
    }

    @PostMapping
    public UserModel create(@RequestBody User user){
        UserModel userModel = assembler.toModel(service.create(user));
        linkBuilder.buildLinks(userModel);
        return userModel;
    }

    @PostMapping("{id}/orders")
    public OrderModel createOrder(@PathVariable Long id, @RequestBody List<Long> certificates ){
        OrderModel orderModel = orderModelAssembler.toModel(orderService.create(id, certificates));
        orderLinkBuilder.buildLinks(orderModel);
        return orderModel;
    }

    @GetMapping("{id}/orders")
    public CollectionModel<OrderModel> findAllUsersOrder(@PathVariable Long id,
                                                    @RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                                    @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        CollectionModel<OrderModel> orders = orderModelAssembler.toCollectionModel(orderService.findAllUsersOrder(id, page, pageSize));
        orders.forEach(orderLinkBuilder::buildSelfLink);
        return orders;
    }

    @GetMapping("/tags")
    public List<TagModel> getMostPopularTag() {
        List<TagModel> tagList = service.getMostPopularTag()
                .stream()
                .map(tagModelAssembler::toModel)
                .toList();
        tagList.forEach(tagLinkBuilder::buildSelfLink);
        return tagList;
    }

    @GetMapping("{id}/total")
    public BigDecimal totalCostOfUserOrders(@PathVariable Long id) {
        return service.findTotalCost(id);
    }

    @GetMapping("/total")
    public List<UserWithTotalCost> getRichestUser() {
        return service.getUsersWithTotalCost();
    }

    @PostMapping("/create")
    public void create1000(){
        service.create1000();
    }
}
