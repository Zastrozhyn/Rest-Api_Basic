package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.NotValidCertificateDataException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void create(GiftCertificate giftCertificate) {
        if (giftCertificateValidator.isValid(giftCertificate)){
        giftCertificateDao.create(giftCertificate);
        }
        if (!giftCertificateValidator.isValid(giftCertificate)){
            throw new NotValidCertificateDataException();
        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public GiftCertificate findById(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id);
        if (giftCertificate != null){
            giftCertificate.setTags(tagDao.findAllTagInCertificate(giftCertificate.getId()));
        }
        if (giftCertificate == null){
            throw new CertificateNotFoundException();
        }
        return giftCertificate;
    }

    @Override
    @Transactional
    public void delete(long id) {
        giftCertificateDao.delete(id);
        tagDao.deleteAllTagFromCertificate(id);
    }

    @Override
    public List<GiftCertificate> findAllCertificateByTag(String tagName){
        return giftCertificateDao.findAllCertificateByTag(tagName);
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
    public List<GiftCertificate> findCertificateByName(String name){
        return giftCertificateDao.findByName(name);
    }

    @Override
    public List<GiftCertificate> findCertificateByDescription(String description){
        return giftCertificateDao.findByName(description);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate updatedGiftCertificate) {
        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(id);
        Map<String,Object> updatedFields = getUpdatedField(updatedGiftCertificate, currentGiftCertificate);
        giftCertificateDao.update(updatedGiftCertificate, updatedFields);
        return null;
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
