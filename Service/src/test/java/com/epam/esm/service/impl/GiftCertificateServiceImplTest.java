package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class GiftCertificateServiceImplTest<GiftCertificateDto> {
    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Tag";

    private static final long CERTIFICATE_ID = 1;
    private static final String CERTIFICATE_NAME = "Certificate";
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal PRICE = new BigDecimal("100");
    private static final int DURATION = 50;
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now();

    private GiftCertificateDao giftCertificateDao;
    private TagService tagService;
    private TagDao tagDao;
    private GiftCertificateValidator giftCertificateValidator;
    private TagValidator tagValidator;
    private GiftCertificateServiceImpl service;


    private Tag tag;
    private Set<Tag> tagList;
    private GiftCertificate certificate;
    private String sortingField;
    private List<GiftCertificate> expectedList;

    @BeforeEach
    void setUp() {
        tag = new Tag(TAG_ID, TAG_NAME);
        certificate = new GiftCertificate(CERTIFICATE_ID, CERTIFICATE_NAME, DESCRIPTION, PRICE, CREATION_DATE
                , LAST_UPDATE_DATE,DURATION, tagList);
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        tagService = Mockito.mock(TagServiceImpl.class);
        tagDao = Mockito.mock(TagDaoImpl.class);
        giftCertificateValidator = new GiftCertificateValidator();
        tagValidator = new TagValidator();
        service = new GiftCertificateServiceImpl(giftCertificateDao, tagDao, tagService, giftCertificateValidator,
                tagValidator);
    }

    @Test
    void findByIdTest() {
        doReturn(certificate).when(giftCertificateDao).findById(any());
        service.findById(CERTIFICATE_ID);
        assertEquals(certificate, service.findById(CERTIFICATE_ID));
    }

    @Test
    void findByIdTestThrowException() {
        doReturn(null).when(giftCertificateDao).findById(any());
        assertThrows(EntityException.class, () -> service.findById(CERTIFICATE_ID));
    }

    @Test
    void deleteTestThrowException(){
        doReturn(null).when(giftCertificateDao).findById(any());
        assertThrows(EntityException.class, () -> service.delete(CERTIFICATE_ID));
    }

    @Test
    void createTest() {

    }

}
