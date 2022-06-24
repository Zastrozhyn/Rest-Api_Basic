package com.epam.esm.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.exception.model.GiftCertificateModel;
import com.epam.esm.exception.model.TagModel;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelAssembler
        implements RepresentationModelAssembler<GiftCertificate, GiftCertificateModel> {
    private static final String TAGS = "tags";
    private static final String ADD_TAG_TO_CERTIFICATE = "add tag";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String ALL = "all";
    private static final String ID = "Id";

    private final TagModelAssembler assembler;

    @Autowired
    public GiftCertificateModelAssembler(TagModelAssembler tagModelAssembler) {
        this.assembler = tagModelAssembler;
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificate entity) {
        List<TagModel> tagList = entity.getTags().stream().map(assembler::toModel).toList();
        GiftCertificateModel model = GiftCertificateModel.builder()
                .duration(entity.getDuration())
                .createDate(entity.getCreateDate())
                .description(entity.getDescription())
                .lastUpdateDate(entity.getLastUpdateDate())
                .tags(new HashSet<>(tagList))
                .price(entity.getPrice())
                .id(entity.getId())
                .name(entity.getName())
                .build();
        addSelfLink(model);
        return model;
    }

    @Override
    public CollectionModel<GiftCertificateModel> toCollectionModel(Iterable<? extends GiftCertificate> entities) {
        CollectionModel<GiftCertificateModel> models = RepresentationModelAssembler.super.toCollectionModel(entities);
        addLinks(models);
        return models;
    }

    public GiftCertificateModel toModelWithAllLinks(GiftCertificate entity){
        GiftCertificateModel model = toModel(entity);
        addLinks(model);
        return model;
    }

    public void addLinks(GiftCertificateModel model) {
        model.add(linkTo(GiftCertificateController.class).withRel(ALL));
        model.add(linkTo(GiftCertificateController.class).slash(model.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        model.add(linkTo(GiftCertificateController.class).slash(model.getId())
                .withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        model.add(linkTo(GiftCertificateController.class).slash(model.getId())
                .slash(TAGS).withRel(ADD_TAG_TO_CERTIFICATE).withType(RequestMethod.PUT.name()));
    }

    public void addSelfLink(GiftCertificateModel model) {
        model.add(linkTo(methodOn(GiftCertificateController.class).findById(model.getId())).withSelfRel());
    }

    public void addLinks(CollectionModel<GiftCertificateModel> models) {
        models.add(linkTo(GiftCertificateController.class).withRel(ALL));
        models.add(linkTo(GiftCertificateController.class)
                .slash(ID).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        models.add(linkTo(GiftCertificateController.class).slash(ID)
                .slash(TAGS).withRel(ADD_TAG_TO_CERTIFICATE).withType(RequestMethod.PUT.name()));
    }
}
