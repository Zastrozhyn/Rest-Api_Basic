package com.epam.esm.controller;

import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.UserWithTotalCost;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.impl.UserLinkBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserController(UserService service, OrderService orderService, UserLinkBuilder linkBuilder) {
        this.service = service;
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
    }


    @GetMapping
    public CustomPage<UserDto> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                              @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        List<UserDto> users = service.findAll(pageSize, page);
        users.forEach(linkBuilder::buildSelfLink);
        CustomPage customPage = new CustomPage(users, page, pageSize);
        linkBuilder.buildAllLinks(customPage);
        return customPage;
    }

    @GetMapping("/{id}")
    public UserDto findUser(@PathVariable Long id){
        UserDto user = service.findUser(id);
        linkBuilder.buildLinks(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Long id,@RequestBody UserDto user){
        return service.update(user, id);
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user){
        return service.create(user);
    }

    @PostMapping("{id}/orders")
    public Order createOrder(@PathVariable Long id, @RequestBody List<Long> certificates ){
        return orderService.create(id, certificates);
    }

    @GetMapping("{id}/orders")
    public List<Order> findAllUsersOrder(@PathVariable Long id,
                                         @RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        return orderService.findAllUsersOrder(id, page, pageSize);
    }

    @GetMapping("/tags")
    public List<Tag> getMostPopularTag() {
        return service.getMostPopularTag();
    }

    @GetMapping("{id}/total")
    public BigDecimal totalCostOfUserOrders(@PathVariable Long id) {
        return service.findTotalCost(id);
    }

    @GetMapping("/total")
    public List<UserWithTotalCost> getRichestUser() {
        return service.getUsersWithTotalCost();
    }

}
