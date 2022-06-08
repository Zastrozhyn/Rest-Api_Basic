package com.epam.esm.util.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateLinkBuilder implements HateoasLinkBuilder<GiftCertificateDto> {
    private final static String TAGS = "tags";
    private final static String ADD_TAG_TO_CERTIFICATE = "add tag";
    private final TagLinkBuilder linkBuilder;

    @Autowired
    public GiftCertificateLinkBuilder(TagLinkBuilder linkBuilder) {
        this.linkBuilder = linkBuilder;
    }


    @Override
    public void buildLinks(GiftCertificateDto certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel());
        certificate.add(linkTo(GiftCertificateController.class).withRel(ALL));
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        certificate.add(linkTo(methodOn(GiftCertificateController.class)
                .update(certificate.getId(), certificate)).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId())
                .slash(TAGS).withRel(ADD_TAG_TO_CERTIFICATE).withType(RequestMethod.PUT.name()));


    }

    @Override
    public void buildSelfLink(GiftCertificateDto certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel());
        certificate.getTags().forEach(linkBuilder::buildSelfLink);
    }

    @Override
    public void buildAllLinks(CustomPage<GiftCertificateDto> customPage) {
        customPage.add(linkTo(GiftCertificateController.class).withRel(ALL));
        customPage.add(linkTo(GiftCertificateController.class)
                .slash(ID).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        customPage.add(linkTo(GiftCertificateController.class).slash(ID)
                .slash(TAGS).withRel(ADD_TAG_TO_CERTIFICATE).withType(RequestMethod.PUT.name()));
    }
}
