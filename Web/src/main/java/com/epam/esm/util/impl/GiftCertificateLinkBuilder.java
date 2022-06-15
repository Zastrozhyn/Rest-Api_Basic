package com.epam.esm.util.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateModel;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateLinkBuilder implements HateoasLinkBuilder<GiftCertificateModel> {
    private final static String TAGS = "tags";
    private final static String ADD_TAG_TO_CERTIFICATE = "add tag";
    private final TagLinkBuilder linkBuilder;

    @Autowired
    public GiftCertificateLinkBuilder(TagLinkBuilder linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    @Override
    public void buildLinks(GiftCertificateModel certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel());
        certificate.add(linkTo(GiftCertificateController.class).withRel(ALL));
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId())
                .withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId())
                .slash(TAGS).withRel(ADD_TAG_TO_CERTIFICATE).withType(RequestMethod.PUT.name()));
        certificate.getTags().forEach(linkBuilder::buildSelfLink);
    }

    @Override
    public void buildSelfLink(GiftCertificateModel certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel());
        certificate.getTags().forEach(linkBuilder::buildSelfLink);
    }

    @Override
    public void buildAllLinks(CollectionModel<GiftCertificateModel> models) {
        models.add(linkTo(GiftCertificateController.class).withRel(ALL));
        models.add(linkTo(GiftCertificateController.class)
                .slash(ID).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        models.add(linkTo(GiftCertificateController.class).slash(ID)
                .slash(TAGS).withRel(ADD_TAG_TO_CERTIFICATE).withType(RequestMethod.PUT.name()));
    }
}
