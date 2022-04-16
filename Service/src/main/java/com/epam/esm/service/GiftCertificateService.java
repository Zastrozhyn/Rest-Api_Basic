package com.epam.esm.service;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface GiftCertificateService {
    void create(GiftCertificate giftCertificate);
    List<GiftCertificate> findAll();
    GiftCertificate findById(Long id);
    void delete(long id);
    List<GiftCertificate> findAllCertificateByTag(String tagName);
    void addTagToCertificate(Tag tag, long idCertificate);
    void deleteTagFromCertificate(Tag tag, long idCertificate);
    GiftCertificate update(Long id, GiftCertificate giftCertificate);
    List<GiftCertificate> findByAttributes(String tagName, String searchPart, String sortingField, String orderSort);
}
