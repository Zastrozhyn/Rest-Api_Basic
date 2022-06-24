package com.epam.esm.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.exception.model.TagModel;
import com.epam.esm.entity.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, TagModel> {
    private static final String DELETE = "delete";
    private static final String ALL = "all";

    @Override
    public TagModel toModel(Tag entity) {
        TagModel model = TagModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        addSelfLink(model);
        return model;
    }

    @Override
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends Tag> entities) {
        CollectionModel<TagModel> model = RepresentationModelAssembler.super.toCollectionModel(entities);
        addLinks(model);
        return model;
    }

    public TagModel toModelWithLinks(Tag entity){
        TagModel model = toModel(entity);
        addLinks(model);
        return model;
    }

    public void addLinks(TagModel resource) {
        resource.add(linkTo(TagController.class).slash(resource.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
    }
    public void addLinks(CollectionModel<TagModel> resources) {
        resources.add(linkTo(TagController.class).withRel(ALL));
    }

    public void addSelfLink(TagModel resource) {
        resource.add(linkTo(methodOn(TagController.class).findById(resource.getId())).withSelfRel());
    }
}
