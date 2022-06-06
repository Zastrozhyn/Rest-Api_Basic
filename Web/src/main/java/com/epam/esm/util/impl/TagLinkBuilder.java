package com.epam.esm.util.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CustomPage;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder implements HateoasLinkBuilder<Tag> {

    @Override
    public void buildLinks(Tag tag) {
        tag.add(linkTo(methodOn(TagController.class).findById(tag.getId())).withSelfRel());
        tag.add(linkTo(TagController.class).withRel(ALL));
        tag.add(linkTo(TagController.class).slash(tag.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
    }

    @Override
    public void buildSelfLink(Tag tag) {

    }

    @Override
    public void buildAllLinks(CustomPage<Tag> customPage) {

    }
}
