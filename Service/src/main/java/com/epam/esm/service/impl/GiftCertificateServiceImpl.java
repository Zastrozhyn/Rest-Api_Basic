package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.exception.ExceptionCode.GIFT_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.ExceptionCode.NOT_VALID_GIFT_CERTIFICATE_DATA;
import static com.epam.esm.util.PaginationUtil.*;

@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService,
                                      GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate create(GiftCertificate giftCertificate) {
        if (!giftCertificateValidator.isValid(giftCertificate)){
            throw new EntityException(NOT_VALID_GIFT_CERTIFICATE_DATA.getErrorCode());
        }
        return giftCertificateDao.create(giftCertificate);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id);
        if (giftCertificate == null){
            throw new EntityException(GIFT_CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return giftCertificate;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(long id) {
         isGiftCertificateExist(id);
         giftCertificateDao.delete(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate addTagToCertificate(Tag tag, long idCertificate){
        if(isGiftCertificateExist(idCertificate) && isTagReadyToCreate(tag)) {
            tagService.create(tag);
        }
        giftCertificateDao.addTagToCertificate(tagService.findTagByName(tag.getName()),idCertificate);
        return findById(idCertificate);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate deleteTagFromCertificate(Tag tag, long idCertificate){
        GiftCertificate certificate = giftCertificateDao.findById(idCertificate);
        if(isTagCanBeDeletedFromCertificate(tag, idCertificate)){
            Tag deletedTag = tagService.findTagByName(tag.getName());
            certificate.deleteTagFromCertificate(deletedTag);
        }
        return giftCertificateDao.update(certificate);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        isGiftCertificateExist(id);
        isGiftCertificateValid(giftCertificate);
        giftCertificate.setId(id);
        return giftCertificateDao.update(giftCertificate);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<GiftCertificate> findByAttributes(List<String> tagList, String searchPart, String sortingField,
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
        return certificates;
    }

    private boolean isGiftCertificateValid(GiftCertificate giftCertificate){
        if(!giftCertificateValidator.isValid(giftCertificate)){
            throw new EntityException(NOT_VALID_GIFT_CERTIFICATE_DATA.getErrorCode());
        }
        return true;
    }

    @Override
    public boolean isGiftCertificateExist(Long id){
        if(!giftCertificateDao.exists(id)){
            throw new EntityException(GIFT_CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<GiftCertificate> findAllById(List<Long> idList) {
        return giftCertificateDao.findAllById(idList);
    }

    private boolean isTagReadyToCreate(Tag tag){
        return tagService.isTagValid(tag) && tagService.findTagByName(tag.getName()) == null;
    }

    private boolean isTagCanBeDeletedFromCertificate(Tag tag , long idCertificate){
        return tagService.isTagValid(tag) && isGiftCertificateExist(idCertificate) && tagService.isTagExist(tag);
    }
}
