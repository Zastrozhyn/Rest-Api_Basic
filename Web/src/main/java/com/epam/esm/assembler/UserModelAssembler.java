package com.epam.esm.assembler;

import com.epam.esm.controller.UserController;
import com.epam.esm.exception.model.UserModel;
import com.epam.esm.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {
    private static final String ORDERS = "orders";
    private static final String CREATE_ORDER = "create order";
    private static final String ALL_USERS_ORDERS = "all orders";
    private static final String DELETE = "delete";
    private static final String ALL = "all";
    private static final String UPDATE = "update";
    private static final String ID = "Id";

    @Override
    public UserModel toModel(User entity) {
        UserModel model = UserModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        addSelfLink(model);
        return model;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> models = RepresentationModelAssembler.super.toCollectionModel(entities);
        addLinks(models);
        return models;
    }

    public UserModel toModelWithLinks(User entity){
        UserModel model = toModel(entity);
        addLinks(model);
        return model;
    }

    public void addLinks(UserModel resource) {
        resource.add(linkTo(UserController.class).withRel(ALL));
        resource.add(linkTo(UserController.class).slash(resource.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        resource.add(linkTo(UserController.class).slash(resource.getId())
                .withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        resource.add(linkTo(UserController.class).slash(resource.getId()).slash(ORDERS).withRel(CREATE_ORDER).withType(RequestMethod.POST.name()));
        resource.add(linkTo(UserController.class).slash(resource.getId()).slash(ORDERS).withRel(ALL_USERS_ORDERS));

    }
    public void addLinks(CollectionModel<UserModel> resources) {
        resources.add(linkTo(UserController.class).withRel(ALL));
        resources.add(linkTo(UserController.class).slash(ID).slash(ORDERS)
                .withRel(CREATE_ORDER).withType(RequestMethod.POST.name()));
        resources.add(linkTo(UserController.class).slash(ID).slash(ORDERS).withRel(ALL_USERS_ORDERS));
    }

    public void addSelfLink(UserModel resource) {
        resource.add(linkTo(methodOn(UserController.class).findUser(resource.getId())).withSelfRel());
    }
}
