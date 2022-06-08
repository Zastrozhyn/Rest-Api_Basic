package com.epam.esm.controller;

import com.epam.esm.dto.CustomPage;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.impl.GiftCertificateLinkBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     LocaleResolver localeResolver, GiftCertificateLinkBuilder linkBuilder) {
        this.giftCertificateService = giftCertificateService;
        this.localeResolver = localeResolver;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping()
    public CustomPage<GiftCertificateDto> findByAttributes(@RequestParam(required = false, name = "tagList") List<String> tagList,
                                                        @RequestParam(required = false, name = "searchPart") String searchPart,
                                                        @RequestParam(required = false, name = "sortingField") String sortingField,
                                                        @RequestParam(required = false, name = "orderSort") String orderSort,
                                                        @RequestParam(required = false, defaultValue = "10", name = "pageSize") Integer pageSize,
                                                        @RequestParam(required = false, defaultValue = "1", name = "page") Integer page,
                                                        @RequestParam(required = false, name = "search") String search){
        List<GiftCertificateDto> certificates = giftCertificateService.findByAttributes(tagList, searchPart,
                sortingField, orderSort, search, pageSize, page);
        certificates.forEach(linkBuilder::buildSelfLink);
        CustomPage<GiftCertificateDto> customPage = new CustomPage<>(certificates, page, pageSize);
        linkBuilder.buildAllLinks(customPage);
        return customPage;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificateDto certificate = giftCertificateService.create(giftCertificate);
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        GiftCertificateDto certificate = giftCertificateService.findById(id);
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable Long id, @RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificateDto certificate = giftCertificateService.update(id, giftCertificate);
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @PutMapping("/{id}/tags")
    public GiftCertificateDto addTagToCertificate(@PathVariable Long id, @RequestBody TagDto tag){
        GiftCertificateDto certificate = giftCertificateService.addTagToCertificate(tag, id);
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @DeleteMapping ("/{id}/tags")
    public GiftCertificateDto deleteTagFromCertificate(@PathVariable Long id, @RequestBody TagDto tag){
        GiftCertificateDto certificate = giftCertificateService.deleteTagFromCertificate(tag, id);
        linkBuilder.buildLinks(certificate);
        return certificate;
    }

    @GetMapping("locales")
    public void changeLocale (@RequestParam(name = "locale") String locale ,
                              HttpServletRequest request, HttpServletResponse response){
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale));
    }

}
