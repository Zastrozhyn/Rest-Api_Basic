package com.epam.esm.controller;

import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserWithTotalCost;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.impl.OrderLinkBuilder;
import com.epam.esm.util.impl.TagLinkBuilder;
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
    private final OrderLinkBuilder orderLinkBuilder;
    private final TagLinkBuilder tagLinkBuilder;

    @Autowired
    public UserController(UserService service, OrderService orderService, UserLinkBuilder linkBuilder,
                          OrderLinkBuilder orderLinkBuilder, TagLinkBuilder tagLinkBuilder) {
        this.service = service;
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
        this.orderLinkBuilder = orderLinkBuilder;
        this.tagLinkBuilder = tagLinkBuilder;
    }

    @GetMapping
    public CustomPage<UserDto> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                              @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        List<UserDto> users = service.findAll(pageSize, page);
        users.forEach(linkBuilder::buildSelfLink);
        CustomPage<UserDto> customPage = new CustomPage<>(users, page, pageSize);
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
        UserDto userDto = service.update(user, id);
        linkBuilder.buildLinks(userDto);
        return userDto;
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user){
        UserDto userDto = service.create(user);
        linkBuilder.buildLinks(userDto);
        return userDto;
    }

    @PostMapping("{id}/orders")
    public OrderDto createOrder(@PathVariable Long id, @RequestBody List<Long> certificates ){
        return orderService.create(id, certificates);
    }

    @GetMapping("{id}/orders")
    public CustomPage<OrderDto> findAllUsersOrder(@PathVariable Long id,
                                         @RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        List<OrderDto> orders = orderService.findAllUsersOrder(id, page, pageSize);
        orders.forEach(orderLinkBuilder::buildSelfLink);
        return new CustomPage<>(orders, page, pageSize);
    }

    @GetMapping("/tags")
    public List<TagDto> getMostPopularTag() {
        List<TagDto> tagDtoList = service.getMostPopularTag();
        tagDtoList.forEach(tagLinkBuilder::buildLinks);
        return tagDtoList;
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
