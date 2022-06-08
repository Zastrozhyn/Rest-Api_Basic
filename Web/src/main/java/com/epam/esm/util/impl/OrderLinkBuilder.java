package com.epam.esm.util.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkBuilder implements HateoasLinkBuilder<OrderDto> {
    private final UserLinkBuilder userLinkBuilder;
    private final GiftCertificateLinkBuilder giftCertificateLinkBuilder;

    @Autowired
    public OrderLinkBuilder(UserLinkBuilder userLinkBuilder, GiftCertificateLinkBuilder giftCertificateLinkBuilder) {
        this.userLinkBuilder = userLinkBuilder;
        this.giftCertificateLinkBuilder = giftCertificateLinkBuilder;
    }


    @Override
    public void buildLinks(OrderDto order) {
        order.add(linkTo(methodOn(OrderController.class).findOrder(order.getId())).withSelfRel());
        order.add(linkTo(OrderController.class).withRel(ALL));
        order.add(linkTo(OrderController.class).slash(order.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        order.add(linkTo(methodOn(OrderController.class)
                .update(order.getId(), order)).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
    }

    @Override
    public void buildSelfLink(OrderDto order) {
        order.add(linkTo(methodOn(OrderController.class).findOrder(order.getId())).withSelfRel());
        userLinkBuilder.buildSelfLink(order.getUser());
        order.getCertificateList().forEach(giftCertificateLinkBuilder::buildSelfLink);
    }

    @Override
    public void buildAllLinks(CustomPage<OrderDto> customPage) {
        customPage.add(linkTo(OrderController.class).slash(ID).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        customPage.add(linkTo(OrderController.class).slash(ID).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
    }
}
