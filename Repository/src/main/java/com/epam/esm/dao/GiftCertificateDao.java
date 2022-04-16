package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao {
    void create(GiftCertificate giftCertificate);
    boolean update(Long id, Map<String, Object> updatedFields);
    List<GiftCertificate> findAll();
    GiftCertificate findById(Long id);
    void delete(Long id);
    List<GiftCertificate> findAllCertificateByTag(String tagName);
    List<GiftCertificate> findByAttributes(String tagName, String searchPart, String sortingField, String orderSort);
}
