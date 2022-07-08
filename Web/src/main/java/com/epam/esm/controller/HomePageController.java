package com.epam.esm.controller;

import com.epam.esm.model.CustomPage;
import com.epam.esm.util.HomePageLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {
    private final HomePageLinkBuilder linkBuilder;

    @Autowired
    public HomePageController(HomePageLinkBuilder linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    public CustomPage getHomePage(){
        CustomPage customPage = new CustomPage();
        linkBuilder.buildLinks(customPage);
        return customPage;
    }
}
