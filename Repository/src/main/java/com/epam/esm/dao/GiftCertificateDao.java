package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate create(GiftCertificate giftCertificate);
    GiftCertificate update(GiftCertificate giftCertificate);
    List<GiftCertificate> findAll(Integer offset, Integer limit);
    GiftCertificate findById(Long id);
    void delete(Long id);
    List<GiftCertificate> findByAttributes(String tagName, String searchPart, String sortingField, String orderSort,
                                           Integer offset, Integer limit);
}
