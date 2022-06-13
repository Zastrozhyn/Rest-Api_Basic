package com.epam.esm.controller;

import com.epam.esm.converter.impl.GiftCertificateModelAssembler;
import com.epam.esm.dto.GiftCertificateModel;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.impl.GiftCertificateLinkBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final LocaleResolver localeResolver;
    private final GiftCertificateLinkBuilder linkBuilder;
    private final GiftCertificateModelAssembler assembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, LocaleResolver localeResolver,
                                     GiftCertificateLinkBuilder linkBuilder, GiftCertificateModelAssembler assembler) {
        this.giftCertificateService = giftCertificateService;
        this.localeResolver = localeResolver;
        this.linkBuilder = linkBuilder;
        this.assembler = assembler;
    }

    @GetMapping()
    public CollectionModel<GiftCertificateModel> findByAttributes(@RequestParam(required = false, name = "tagList") List<String> tagList,
                                                                  @RequestParam(required = false, name = "searchPart") String searchPart,
                                                                  @RequestParam(required = false, name = "sortingField") String sortingField,
                                                                  @RequestParam(required = false, name = "orderSort") String orderSort,
                                                                  @RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                                                  @RequestParam(required = false, defaultValue = "1", name = "page") Integer page,
                                                                  @RequestParam(required = false, name = "search") String search){
        CollectionModel<GiftCertificateModel> certificates = assembler.toCollectionModel(giftCertificateService.findByAttributes(tagList, searchPart,
                sortingField, orderSort, search, pageSize, page));
        certificates.forEach(linkBuilder::buildSelfLink);
        linkBuilder.buildAllLinks(certificates);
        return certificates;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateModel create(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificateModel certificate = assembler.toModel(giftCertificateService.create(giftCertificate));
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @GetMapping("/{id}")
    public GiftCertificateModel findById(@PathVariable Long id) {
        GiftCertificateModel certificate = assembler.toModel(giftCertificateService.findById(id));
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateModel update(@PathVariable Long id, @RequestBody GiftCertificate giftCertificate) {
        GiftCertificateModel certificate = assembler.toModel(giftCertificateService.update(id, giftCertificate));
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @PutMapping("/{id}/tags")
    public GiftCertificateModel addTagToCertificate(@PathVariable Long id, @RequestBody Tag tag){
        GiftCertificateModel certificate = assembler.toModel(giftCertificateService.addTagToCertificate(tag, id));
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @DeleteMapping ("/{id}/tags")
    public GiftCertificateModel deleteTagFromCertificate(@PathVariable Long id, @RequestBody Tag tag){
        GiftCertificateModel certificate = assembler.toModel(giftCertificateService.deleteTagFromCertificate(tag, id));
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @GetMapping("locales")
    public void changeLocale (@RequestParam(name = "locale") String locale ,
                              HttpServletRequest request, HttpServletResponse response){
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale));
    }

}
