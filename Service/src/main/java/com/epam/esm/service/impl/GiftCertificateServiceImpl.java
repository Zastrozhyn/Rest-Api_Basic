package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.NotValidEntityDataException;
import com.epam.esm.exception.WrongFindParametersException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateValidator giftCertificateValidator;
    private final static String FIELD_NAME = "name";
    private final static String FIELD_DESCRIPTION = "description";
    private final static String FIELD_PRICE = "price";
    private final static String FIELD_DURATION = "duration";

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        GiftCertificate createdGiftCertificate = null;
        Long newId = null;
        if (giftCertificateValidator.isValid(giftCertificate)){
        newId = giftCertificateDao.create(giftCertificate);
        createdGiftCertificate = findById(newId);
        }
        if (!giftCertificateValidator.isValid(giftCertificate)){
            throw new NotValidEntityDataException();
        }
        return createdGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id);
        if (giftCertificate == null){
            throw new EntityNotFoundException();
        }
        return giftCertificate;
    }

    @Override
    public void delete(long id) {
         if (giftCertificateDao.findById(id) == null){
             throw new EntityNotFoundException();
         }
        giftCertificateDao.delete(id);
    }

    @Override
    public void addTagToCertificate(Tag tag, long idCertificate){
        tagDao.addTagToCertificate(tag, idCertificate);
    }

    @Override
    public void deleteTagFromCertificate(Tag tag, long idCertificate){
        tagDao.deleteTagFromCertificate(tag, idCertificate);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate updatedGiftCertificate) {
        if (!giftCertificateValidator.isValid(updatedGiftCertificate)){
            throw new NotValidEntityDataException();
        }
        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(id);
        Map<String,Object> updatedFields = getUpdatedField(updatedGiftCertificate, currentGiftCertificate);
        giftCertificateDao.update(id, updatedFields);
        return giftCertificateDao.findById(id);
    }

    @Override
    public List<GiftCertificate> findByAttributes(String tagName, String searchPart, String sortingField,
                                                  String orderSort, String search) {
        if (search ==  null){
            return giftCertificateDao.findAll();
        }
        if (giftCertificateValidator.isGiftCertificateFieldListValid(sortingField)
                && giftCertificateValidator.isOrderSortValid(orderSort)) {
            return giftCertificateDao.findByAttributes(tagName, searchPart, sortingField, orderSort);
        }
        throw new WrongFindParametersException();
    }

    private Map<String,Object> getUpdatedField(GiftCertificate updatedGiftCertificate,
                                               GiftCertificate currentGiftCertificate){
        Map<String,Object> updatedFields = new HashMap<String,Object>();
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


}
