package com.epam.esm.service.impl;

import com.epam.esm.dao.dataJpa.GiftCertificateDaoJpa;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.exception.ExceptionCode.GIFT_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.ExceptionCode.NOT_VALID_GIFT_CERTIFICATE_DATA;
import static com.epam.esm.util.PaginationUtil.checkPage;
import static com.epam.esm.util.PaginationUtil.checkPageSize;

@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;
    private final GiftCertificateDaoJpa daoJpa;

    public GiftCertificateServiceImpl(TagService tagService,
                                      GiftCertificateValidator giftCertificateValidator,
                                      GiftCertificateDaoJpa daoJpa) {
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
        this.daoJpa = daoJpa;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate create(GiftCertificate giftCertificate) {
        if (!giftCertificateValidator.isValid(giftCertificate)){
            throw new EntityException(NOT_VALID_GIFT_CERTIFICATE_DATA.getErrorCode());
        }
        return daoJpa.save(giftCertificate);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public GiftCertificate findById(Long id) {
        return daoJpa.findById(id).orElseThrow(() -> new EntityException(GIFT_CERTIFICATE_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(long id) {
         isGiftCertificateExist(id);
         daoJpa.deleteById(id);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate addTagToCertificate(Tag tag, long idCertificate){
        if(isGiftCertificateExist(idCertificate) && isTagReadyToCreate(tag)) {
            tagService.create(tag);
        }
        GiftCertificate certificate = findById(idCertificate);
        certificate.addTag(tag);
        return daoJpa.save(certificate);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate deleteTagFromCertificate(Tag tag, long idCertificate){
        GiftCertificate certificate = findById(idCertificate);
        if(isTagCanBeDeletedFromCertificate(tag, idCertificate)){
            Tag deletedTag = tagService.findTagByName(tag.getName());
            certificate.deleteTagFromCertificate(deletedTag);
        }
        return daoJpa.save(certificate);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GiftCertificate update(Long id, GiftCertificate giftCertificate) {
        isGiftCertificateExist(id);
        isGiftCertificateValid(giftCertificate);
        giftCertificate.setId(id);
        return daoJpa.save(giftCertificate);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<GiftCertificate> findByAttributes(List<String> tagList, String searchPart, String sortingField,
                                                  String orderSort, String search, Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        List<GiftCertificate> certificates = new ArrayList<>();
        if (search == null){
            certificates = daoJpa.findAll(PageRequest.of(page, pageSize)).getContent();
        }
        if (search != null && giftCertificateValidator.isGiftCertificateFieldValid(sortingField)
                && giftCertificateValidator.isOrderSortValid(orderSort) && searchPart != null) {
            certificates = daoJpa.findAllByNameContainingOrDescriptionContaining(searchPart, sortingField, PageRequest.of(page, pageSize)).getContent();
        }
        if(tagList != null){
            List<Tag> tags = tagList.stream().map(tagService::findTagByName).toList();
            System.out.println(tags);
            certificates = daoJpa.findAllByTagsIn(tags, PageRequest.of(page, pageSize)).getContent();
        }
        return certificates.stream().sorted((o1, o2) -> o1.getId().compareTo(o2.getId())).toList();
    }

    private boolean isGiftCertificateValid(GiftCertificate giftCertificate){
        if(!giftCertificateValidator.isValid(giftCertificate)){
            throw new EntityException(NOT_VALID_GIFT_CERTIFICATE_DATA.getErrorCode());
        }
        return true;
    }

    @Override
    public boolean isGiftCertificateExist(Long id){
        if(!daoJpa.existsById(id)){
            throw new EntityException(GIFT_CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<GiftCertificate> findAllById(List<Long> idList) {
        return daoJpa.findAllById(idList);
    }

    private boolean isTagReadyToCreate(Tag tag){
        return tagService.isTagValid(tag) && tagService.findTagByName(tag.getName()) == null;
    }

    private boolean isTagCanBeDeletedFromCertificate(Tag tag , long idCertificate){
        return tagService.isTagValid(tag) && isGiftCertificateExist(idCertificate) && tagService.isTagExist(tag);
    }

}
