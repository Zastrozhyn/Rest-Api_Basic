package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao {
    void create(GiftCertificate giftCertificate);
    void update(GiftCertificate giftCertificate, Map<String, Object> updatedFields);
    List<GiftCertificate> findAll();
    GiftCertificate findById(Long id);
    void delete(Long id);
    List<GiftCertificate> findAllCertificateByTag(String tagName);
    List<GiftCertificate> findByDescription(String description);
    List<GiftCertificate> findByName(String name);

}
