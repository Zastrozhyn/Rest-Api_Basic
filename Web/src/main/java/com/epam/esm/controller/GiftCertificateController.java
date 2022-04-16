package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.create(giftCertificate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findAll(){
        return giftCertificateService.findAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        System.out.println(giftCertificateService.findById(id));
        return giftCertificateService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate update(@PathVariable Long id, @RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.update(id, giftCertificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @GetMapping("/search")
    public List<GiftCertificate> findByAttributes(@RequestParam(required = false, name = "tagName") String tagName,
                                                     @RequestParam(required = false, name = "searchPart") String searchPart,
                                                     @RequestParam(required = false, name = "sortingField") String sortingField,
                                                     @RequestParam(required = false, name = "orderSort") String orderSort) {
        return giftCertificateService.findByAttributes(tagName, searchPart, sortingField, orderSort);
    }
}
