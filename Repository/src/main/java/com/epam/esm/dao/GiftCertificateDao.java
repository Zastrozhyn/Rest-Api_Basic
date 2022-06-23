package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface GiftCertificateDao {
    GiftCertificate create(GiftCertificate giftCertificate);
    GiftCertificate update(GiftCertificate giftCertificate);
    List<GiftCertificate> findAll(Integer offset, Integer limit);
    GiftCertificate findById(Long id);
    void delete(Long id);
    List<GiftCertificate> findByAttributes(List<String> tagList, String searchPart, String sortingField,
                                           String orderSort, Integer offset, Integer limit);
    void addTagToCertificate(Tag tag, Long idCertificate);
}
