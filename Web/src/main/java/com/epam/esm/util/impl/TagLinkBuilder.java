package com.epam.esm.util.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagModel;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder implements HateoasLinkBuilder<TagModel> {
    @Override
    public void buildLinks(TagModel tag) {
        tag.add(linkTo(methodOn(TagController.class).findById(tag.getId())).withSelfRel());
        tag.add(linkTo(TagController.class).withRel(ALL));
        tag.add(linkTo(TagController.class).slash(tag.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
    }

    @Override
    public void buildSelfLink(TagModel tag) {
        tag.add(linkTo(methodOn(TagController.class).findById(tag.getId())).withSelfRel());
    }

    @Override
    public void buildAllLinks(CollectionModel<TagModel> models) {
        models.add(linkTo(TagController.class).withRel(ALL));
    }
}
