package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateDtoConverter;
import com.epam.esm.converter.impl.TagDtoConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.exception.ExceptionCode.*;
import static com.epam.esm.util.ApplicationUtil.*;

@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;
    private final GiftCertificateDtoConverter converter;
    private final TagDtoConverter tagConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService,
                                      GiftCertificateValidator giftCertificateValidator,
                                      GiftCertificateDtoConverter converter, TagDtoConverter tagConverter) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
        this.converter = converter;
        this.tagConverter = tagConverter;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = converter.convertFromDto(giftCertificateDto);
        if (!giftCertificateValidator.isValid(certificate)){
            throw new EntityException(NOT_VALID_GIFT_CERTIFICATE_DATA.getErrorCode());
        }
        return converter.convertToDto(giftCertificateDao.create(certificate));
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id);
        if (giftCertificate == null){
            throw new EntityException(GIFT_CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return converter.convertToDto(giftCertificate);
    }

    @Override
    public void delete(long id) {
         if (isGiftCertificateExist(id)){
             giftCertificateDao.delete(id);
         }
    }

    @Override
    @Transactional
    public GiftCertificateDto addTagToCertificate(TagDto tagDto, long idCertificate){
        GiftCertificate certificate = giftCertificateDao.findById(idCertificate);
        Tag tag = tagConverter.convertFromDto(tagDto);
        if(isGiftCertificateExist(idCertificate) && isTagReadyToCreate(tag)) {
            tagService.create(tagDto);
        }
        certificate.addTag(tag);
        return converter.convertToDto(giftCertificateDao.update(certificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto deleteTagFromCertificate(TagDto tagDto, long idCertificate){
        GiftCertificate certificate = giftCertificateDao.findById(idCertificate);
        Tag tag = tagConverter.convertFromDto(tagDto);
        if(isTagCanBeDeletedFromCertificate(tag, idCertificate)){
            certificate.deleteTagFromCertificate(tag);
        }
        return converter.convertToDto(giftCertificateDao.update(certificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id);
        if(isGiftCertificateValid(giftCertificate) && isGiftCertificateExist(id)){
            giftCertificate.setId(id);
        }
        return converter.convertToDto(giftCertificateDao.update(giftCertificate));
    }

    @Override
    public List<GiftCertificateDto> findByAttributes(List<String> tagList, String searchPart, String sortingField,
                                                  String orderSort, String search, Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        List<GiftCertificate> certificates = new ArrayList<>();
        if (search == null){
            certificates = giftCertificateDao.findAll(calculateOffset(pageSize, page), pageSize);
        }
        if (giftCertificateValidator.isGiftCertificateFieldValid(sortingField)
                && giftCertificateValidator.isOrderSortValid(orderSort) && search != null) {
            certificates = giftCertificateDao.findByAttributes(tagList, searchPart, sortingField, orderSort,
                    calculateOffset(pageSize, page), pageSize );
        }
        return certificates
                .stream()
                .map(converter::convertToDto)
                .toList();
    }


    private boolean isGiftCertificateValid(GiftCertificate giftCertificate){
        if(!giftCertificateValidator.isValid(giftCertificate)){
            throw new EntityException(NOT_VALID_GIFT_CERTIFICATE_DATA.getErrorCode());
        }
        return true;
    }

    private boolean isGiftCertificateExist(Long id){
        if(giftCertificateDao.findById(id) == null){
            throw new EntityException(GIFT_CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    private boolean isTagReadyToCreate(Tag tag){
        return tagService.isTagValid(tag) && tagService.findTagByName(tag.getName()) == null;
    }

    private boolean isTagCanBeDeletedFromCertificate(Tag tag , long idCertificate){
        return tagService.isTagValid(tag) && isGiftCertificateExist(idCertificate) && tagService.isTagExist(tag);
    }

}
