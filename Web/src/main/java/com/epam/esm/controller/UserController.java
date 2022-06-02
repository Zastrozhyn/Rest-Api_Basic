package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService service, OrderService orderService) {
        this.service = service;
        this.orderService = orderService;
    }

    @GetMapping
    public List<User> findAll(@RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                              @RequestParam(required = false, defaultValue = "1", name = "page") Integer page){
        return service.findAll(pageSize, page);
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable Long id){
        return service.findUser(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id,@RequestBody User user){
        return service.update(user, id);
    }

    @PostMapping
    public User create(@RequestBody User user){
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
}
