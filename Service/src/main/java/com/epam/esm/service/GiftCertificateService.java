package com.epam.esm.service;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto create(GiftCertificateDto giftCertificate);
    GiftCertificateDto findById(Long id);
    void delete(long id);
    GiftCertificateDto addTagToCertificate(TagDto tag, long idCertificate);
    GiftCertificateDto deleteTagFromCertificate(TagDto tag, long idCertificate);
    GiftCertificateDto update(Long id, GiftCertificateDto giftCertificate);
    List<GiftCertificateDto> findByAttributes(List<String> tagList, String searchPart, String sortingField,
                                              String orderSort, String search, Integer pageSize, Integer page);
}
