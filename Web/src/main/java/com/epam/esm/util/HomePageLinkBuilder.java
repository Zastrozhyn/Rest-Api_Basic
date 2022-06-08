package com.epam.esm.util;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.CustomPage;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class HomePageLinkBuilder {
    private static final String USERS = "users";
    private static final String ORDERS = "orders";
    private static final String TAGS = "tags";
    private static final String CERTIFICATES = "gift certificates";

    public void buildLinks(CustomPage customPage){
        customPage.add(linkTo(UserController.class).withRel(USERS));
        customPage.add(linkTo(OrderController.class).withRel(ORDERS));
        customPage.add(linkTo(TagController.class).withRel(TAGS));
        customPage.add(linkTo(GiftCertificateController.class).withRel(CERTIFICATES));
    }
}
