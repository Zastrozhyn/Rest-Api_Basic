package com.epam.esm.util.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.TagDto;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder implements HateoasLinkBuilder<TagDto> {

    @Override
    public void buildLinks(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).findById(tag.getId())).withSelfRel());
        tag.add(linkTo(TagController.class).withRel(ALL));
        tag.add(linkTo(TagController.class).slash(tag.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
    }

    @Override
    public void buildSelfLink(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class).findById(tag.getId())).withSelfRel());
    }

    @Override
    public void buildAllLinks(CustomPage<TagDto> customPage) {
        customPage.add(linkTo(TagController.class).withRel(ALL));
    }
}
