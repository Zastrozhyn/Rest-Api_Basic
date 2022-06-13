package com.epam.esm.util.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderModel;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkBuilder implements HateoasLinkBuilder<OrderModel> {
    private final UserLinkBuilder userLinkBuilder;
    private final GiftCertificateLinkBuilder giftCertificateLinkBuilder;

    @Autowired
    public OrderLinkBuilder(UserLinkBuilder userLinkBuilder, GiftCertificateLinkBuilder giftCertificateLinkBuilder) {
        this.userLinkBuilder = userLinkBuilder;
        this.giftCertificateLinkBuilder = giftCertificateLinkBuilder;
    }


    @Override
    public void buildLinks(OrderModel order) {
        order.add(linkTo(methodOn(OrderController.class).findOrder(order.getId())).withSelfRel());
        order.add(linkTo(OrderController.class).withRel(ALL));
        order.add(linkTo(OrderController.class).slash(order.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        order.add(linkTo(OrderController.class).slash(order.getId()).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        userLinkBuilder.buildSelfLink(order.getUser());
        order.getCertificateList().forEach(giftCertificateLinkBuilder::buildSelfLink);
    }

    @Override
    public void buildSelfLink(OrderModel order) {
        order.add(linkTo(methodOn(OrderController.class).findOrder(order.getId())).withSelfRel());
        userLinkBuilder.buildSelfLink(order.getUser());
        order.getCertificateList().forEach(giftCertificateLinkBuilder::buildSelfLink);
    }

    @Override
    public void buildAllLinks(CollectionModel<OrderModel> models) {
        models.add(linkTo(OrderController.class).slash(ID).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        models.add(linkTo(OrderController.class).slash(ID).withRel(UPDATE).withType(RequestMethod.PATCH.name()));

    }

}
