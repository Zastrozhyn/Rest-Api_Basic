package com.epam.esm.util.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.HateoasLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateLinkBuilder implements HateoasLinkBuilder<GiftCertificate> {
    private final static String TAGS = "tags";
    private final static String ADD_TAG_TO_CERTIFICATE = "add tag";
    @Override
    public void buildLinks(GiftCertificate certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel());
        certificate.add(linkTo(GiftCertificateController.class).withRel(ALL));
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId()).withRel(DELETE).withType(RequestMethod.DELETE.name()));
        certificate.add(linkTo(methodOn(GiftCertificateController.class)
                .update(certificate.getId(), certificate)).withRel(UPDATE).withType(RequestMethod.PATCH.name()));
        certificate.add(linkTo(GiftCertificateController.class).slash(certificate.getId())
                .slash(TAGS).withRel(ADD_TAG_TO_CERTIFICATE).withType(RequestMethod.PUT.name()));


    }
}
