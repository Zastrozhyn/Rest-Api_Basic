package com.epam.esm.util.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.entity.User;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkBuilder implements HateoasLinkBuilder<User> {
    private static final String ORDERS = "orders";
    private static final String CREATE_ORDER = "create order";
    private static final String ALL_USERS_ORDERS = "all orders";

    @Override
    public void buildLinks(User user) {
        user.add(linkTo(methodOn(UserController.class).findUser(user.getId())).withSelfRel());
        user.add(linkTo(UserController.class).withRel(ALL));
        user.add(linkTo(UserController.class).slash(user.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        user.add(linkTo(methodOn(UserController.class)
                .update(user.getId(), user)).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        user.add(linkTo(UserController.class).slash(user.getId()).slash(ORDERS).withRel(CREATE_ORDER).withType(RequestMethod.POST.name()));
        user.add(linkTo(UserController.class).slash(user.getId()).slash(ORDERS).withRel(ALL_USERS_ORDERS));
    }
}
