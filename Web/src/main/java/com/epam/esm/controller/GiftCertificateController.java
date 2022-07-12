package com.epam.esm.controller;

import com.epam.esm.assembler.GiftCertificateModelAssembler;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
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
    private final GiftCertificateModelAssembler assembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, LocaleResolver localeResolver,
                                     GiftCertificateModelAssembler assembler) {
        this.giftCertificateService = giftCertificateService;
        this.localeResolver = localeResolver;
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
        return assembler.toCollectionModel(giftCertificateService.findByAttributes(tagList, searchPart,
                sortingField, orderSort, search, pageSize, page));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateModel create(@RequestBody GiftCertificate giftCertificate) {
        return assembler.toModelWithAllLinks(giftCertificateService.create(giftCertificate));
    }

    @GetMapping("/{id}")
    public GiftCertificateModel findById(@PathVariable Long id) {
        return assembler.toModelWithAllLinks(giftCertificateService.findById(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateModel update(@PathVariable Long id, @RequestBody GiftCertificate giftCertificate) {
        return assembler.toModelWithAllLinks(giftCertificateService.update(id, giftCertificate));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @PutMapping("/{id}/tags")
    public GiftCertificateModel addTagToCertificate(@PathVariable Long id, @RequestBody Tag tag){
        return assembler.toModelWithAllLinks(giftCertificateService.addTagToCertificate(tag, id));
    }

    @DeleteMapping ("/{id}/tags")
    public GiftCertificateModel deleteTagFromCertificate(@PathVariable Long id, @RequestBody Tag tag){
        return assembler.toModelWithAllLinks(giftCertificateService.deleteTagFromCertificate(tag, id));
    }

    @GetMapping("locales")
    public void changeLocale (@RequestParam(name = "locale") String locale ,
                              HttpServletRequest request, HttpServletResponse response){
        localeResolver.setLocale(request, response, StringUtils.parseLocaleString(locale));
    }

}
