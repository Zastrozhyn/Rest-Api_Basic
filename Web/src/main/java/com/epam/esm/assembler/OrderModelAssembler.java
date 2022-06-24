package com.epam.esm.assembler;

import com.epam.esm.controller.OrderController;
import com.epam.esm.exception.model.GiftCertificateModel;
import com.epam.esm.exception.model.OrderModel;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, OrderModel> {
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ALL = "all";
    private static final String ID = "Id";
    private final GiftCertificateModelAssembler certificateModelAssembler;
    private final UserModelAssembler userModelAssembler;

    @Autowired
    public OrderModelAssembler(GiftCertificateModelAssembler certificateDtoConverter, UserModelAssembler userDtoConverter) {
        this.certificateModelAssembler = certificateDtoConverter;
        this.userModelAssembler = userDtoConverter;
    }

    @Override
    public OrderModel toModel(Order entity) {
        List<GiftCertificateModel> certificates = entity.getCertificateList().stream()
                .map(certificateModelAssembler::toModel).toList();
        OrderModel model = OrderModel.builder()
                .orderDate(entity.getOrderDate())
                .cost(entity.getCost())
                .id(entity.getId())
                .certificateList(certificates)
                .user(userModelAssembler.toModel(entity.getUser()))
                .build();
        addSelfLink(model);
        return model;
    }

    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderModel> models = RepresentationModelAssembler.super.toCollectionModel(entities);
        addLinks(models);
        return models;
    }

    public OrderModel toModelWithAllLinks(Order entity){
        OrderModel model = toModel(entity);
        addLinks(model);
        return model;
    }

    public void addLinks(OrderModel order) {
        order.add(linkTo(OrderController.class).withRel(ALL));
        order.add(linkTo(OrderController.class).slash(order.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        order.add(linkTo(OrderController.class).slash(order.getId()).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
    }

    public void addSelfLink(OrderModel order) {
        order.add(linkTo(methodOn(OrderController.class).findOrder(order.getId())).withSelfRel());
    }

    public void addLinks(CollectionModel<OrderModel> models) {
        models.add(linkTo(OrderController.class).slash(ID).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        models.add(linkTo(OrderController.class).slash(ID).withRel(UPDATE).withType(RequestMethod.PATCH.name()));

    }
}
