package com.epam.esm.service;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificate create(GiftCertificate giftCertificate);
    GiftCertificate findById(Long id);
    void delete(long id);
    GiftCertificate addTagToCertificate(Tag tag, long idCertificate);
    GiftCertificate deleteTagFromCertificate(Tag tag, long idCertificate);
    GiftCertificate update(Long id, GiftCertificate giftCertificate);
    List<GiftCertificate> findByAttributes(List<String> tagList, String searchPart, String sortingField,
                                           String orderSort, String search, Integer pageSize, Integer page);
    boolean isGiftCertificateExist(Long id);
    void create1000();

    List<GiftCertificate> findAllById(List<Long> idList);
}
