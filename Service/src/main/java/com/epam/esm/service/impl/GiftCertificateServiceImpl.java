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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.epam.esm.exception.ExceptionCode.*;

@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_DURATION = "duration";

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagService tagService,
                                      GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public GiftCertificate create(GiftCertificate giftCertificate) {
        if (!giftCertificateValidator.isValid(giftCertificate)){
            throw new EntityException(NOT_VALID_GIFT_CERTIFICATE_DATA.getErrorCode());
        }
        Long newId = giftCertificateDao.create(giftCertificate);
        Set<Tag> tags = giftCertificate.getTags();
        if(tags != null){
            for(Tag tag : tags){
                addTagToCertificate(tag, newId);
            }
        }
        return findById(newId);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id);
        if (giftCertificate == null){
            throw new EntityException(GIFT_CERTIFICATE_NOT_FOUND.getErrorCode());
        }
        return giftCertificate;
    }

    @Override
    public void delete(long id) {
         if (isGiftCertificateExist(id)){
             giftCertificateDao.delete(id);
         }
    }

    @Override
    @Transactional
    public GiftCertificate addTagToCertificate(Tag tag, long idCertificate){
        if(tagService.isTagValid(tag) && isGiftCertificateExist(idCertificate) && (tagService.findTagByName(tag.getName()) == null)) {
            tagService.create(tag);
        }
        tagService.addTagToCertificate(tagService.findTagByName(tag.getName()), idCertificate);
        return giftCertificateDao.findById(idCertificate);
    }

    @Override
    public GiftCertificate deleteTagFromCertificate(Tag tag, long idCertificate){
        if(tagService.isTagValid(tag) && isGiftCertificateExist(idCertificate) && tagService.isTagExist(tag)){
            tagService.deleteTagFromCertificate(tagService.findTagByName(tag.getName()), idCertificate);
        }
        return giftCertificateDao.findById(idCertificate);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate updatedGiftCertificate) {
        if(isGiftCertificateValid(updatedGiftCertificate) && isGiftCertificateExist(id)){
            GiftCertificate currentGiftCertificate = giftCertificateDao.findById(id);
            Map<String,Object> updatedFields = getUpdatedField(updatedGiftCertificate, currentGiftCertificate);
            giftCertificateDao.update(id, updatedFields);
        }
        return giftCertificateDao.findById(id);
    }

    @Override
    public List<GiftCertificate> findByAttributes(String tagName, String searchPart, String sortingField,
                                                  String orderSort, String search) {
        if (search ==  null){
            return giftCertificateDao.findAll();
        }
        if (giftCertificateValidator.isGiftCertificateFieldValid(sortingField)
                && giftCertificateValidator.isOrderSortValid(orderSort)) {
            return giftCertificateDao.findByAttributes(tagName, searchPart, sortingField, orderSort);
        }
        throw new EntityException(WRONG_FIND_PARAMETERS.getErrorCode());
    }

    private Map<String,Object> getUpdatedField(GiftCertificate updatedGiftCertificate,
                                               GiftCertificate currentGiftCertificate){
        Map<String,Object> updatedFields = new HashMap<>();
        if(updatedGiftCertificate.getName() !=null &&
                !updatedGiftCertificate.getName().equals(currentGiftCertificate.getName())){
            updatedFields.put(FIELD_NAME, updatedGiftCertificate.getName());
        }
        if(updatedGiftCertificate.getDescription() !=null &&
                !updatedGiftCertificate.getDescription().equals(currentGiftCertificate.getDescription())){
            updatedFields.put(FIELD_DESCRIPTION, updatedGiftCertificate.getDescription());
        }
        if(updatedGiftCertificate.getDuration() != 0 &&
                updatedGiftCertificate.getDuration() != currentGiftCertificate.getDuration()){
            updatedFields.put(FIELD_DURATION, updatedGiftCertificate.getDuration());
        }
        if(updatedGiftCertificate.getPrice() !=null &&
                !updatedGiftCertificate.getPrice().equals(currentGiftCertificate.getPrice())){
            updatedFields.put(FIELD_PRICE, updatedGiftCertificate.getPrice());
        }
        return updatedFields;
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

}
