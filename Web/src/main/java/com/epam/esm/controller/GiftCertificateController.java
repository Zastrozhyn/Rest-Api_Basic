package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
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

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, LocaleResolver localeResolver) {
        this.giftCertificateService = giftCertificateService;
        this.localeResolver = localeResolver;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificate create(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.create(giftCertificate);
    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate update(@PathVariable Long id, @RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.update(id, giftCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @GetMapping()
    public List<GiftCertificate> findByAttributes(@RequestParam(required = false, name = "tagName") String tagName,
                                                     @RequestParam(required = false, name = "searchPart") String searchPart,
                                                     @RequestParam(required = false, name = "sortingField") String sortingField,
                                                     @RequestParam(required = false, name = "orderSort") String orderSort,
                                                     @RequestParam(required = false, name = "search") String search){
        return giftCertificateService.findByAttributes(tagName, searchPart, sortingField, orderSort, search);
    }

    @PutMapping("/{id}/tags")
    public GiftCertificate addTagToCertificate(@PathVariable Long id, @RequestBody Tag tag){
        return giftCertificateService.addTagToCertificate(tag, id);
    }

    @DeleteMapping ("/{id}/tags")
    public GiftCertificate deleteTagFromCertificate(@PathVariable Long id, @RequestBody Tag tag){
        return giftCertificateService.deleteTagFromCertificate(tag, id);
    }

    @GetMapping("locales")
    public void changeLocale (@RequestParam(name = "locale") String locale ,
                              HttpServletRequest request, HttpServletResponse response){
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale));
    }

}
