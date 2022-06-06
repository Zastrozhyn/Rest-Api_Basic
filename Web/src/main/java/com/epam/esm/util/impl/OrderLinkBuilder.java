package com.epam.esm.util.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.CustomPage;
import com.epam.esm.entity.Order;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkBuilder implements HateoasLinkBuilder<Order> {

    @Override
    public void buildLinks(Order order) {
        order.add(linkTo(methodOn(OrderController.class).findOrder(order.getId())).withSelfRel());
        order.add(linkTo(OrderController.class).withRel(ALL));
        order.add(linkTo(OrderController.class).slash(order.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        order.add(linkTo(methodOn(OrderController.class)
                .update(order.getId(), order)).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
    }

    @Override
    public void buildSelfLink(Order order) {

    }

    @Override
    public void buildAllLinks(CustomPage<Order> customPage) {

    }
}
