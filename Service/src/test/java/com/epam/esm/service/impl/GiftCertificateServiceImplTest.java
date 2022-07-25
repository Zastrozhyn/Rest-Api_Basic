package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class GiftCertificateServiceImplTest {
    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Tag";
    private static int expectedErrorCode;
    private static final long CERTIFICATE_ID = 1;
    private static final String CERTIFICATE_NAME = "Certificate";
    private static final String CERTIFICATE_NEW_NAME = "new";
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal PRICE = new BigDecimal("100");
    private static final int DURATION = 50;
    private static final LocalDateTime CREATION_DATE = LocalDateTime.now();
    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now();
    private static Tag tag;
    private static Set<Tag> tagSet;
    private static GiftCertificate certificate;
    private GiftCertificateDao giftCertificateDao;
    private TagService tagService;
    private GiftCertificateValidator giftCertificateValidator;
    private GiftCertificateServiceImpl service;


//    @BeforeAll
//    static void init(){
//        expectedErrorCode = ExceptionCode.GIFT_CERTIFICATE_NOT_FOUND.getErrorCode();
//        tag = new Tag(TAG_ID, TAG_NAME);
//        tagSet = new HashSet<>();
//        certificate = new GiftCertificate(CERTIFICATE_ID, CERTIFICATE_NAME, DESCRIPTION, PRICE, CREATION_DATE
//                , LAST_UPDATE_DATE,DURATION, tagSet);
//
//    }
//
//    @BeforeEach
//    void setUp() {
//        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
//        tagService = Mockito.mock(TagServiceImpl.class);
//        giftCertificateValidator = new GiftCertificateValidator();
//        service = new GiftCertificateServiceImpl(giftCertificateDao, tagService, giftCertificateValidator);
//    }
//
//    @AfterEach
//    void afterEachTest(){
//        verifyNoMoreInteractions(giftCertificateDao);
//    }
//
//    @Test
//    void findByIdTest() {
//        when(giftCertificateDao.findById(CERTIFICATE_ID)).thenReturn(certificate);
//        GiftCertificate actualCertificate = service.findById(CERTIFICATE_ID);
//        assertThat(actualCertificate, is(equalTo(certificate)));
//        verify(giftCertificateDao).findById(CERTIFICATE_ID);
//    }
//
//    @Test
//    void findByIdTestThrowException() {
//        when(giftCertificateDao.findById(CERTIFICATE_ID)).thenReturn(null);
//        EntityException actualException = assertThrows(EntityException.class, () -> service.findById(CERTIFICATE_ID));
//        assertThat(actualException.getErrorCode(), is(equalTo(expectedErrorCode)));
//        verify(giftCertificateDao).findById(CERTIFICATE_ID);
//    }
//
//    @Test
//    void deleteTestThrowException(){
//        when(giftCertificateDao.findById(CERTIFICATE_ID)).thenReturn(null);
//        EntityException actualException = assertThrows(EntityException.class, () -> service.delete(CERTIFICATE_ID));
//        assertThat(actualException.getErrorCode(), is(equalTo(expectedErrorCode)));
//        verify(giftCertificateDao).findById(CERTIFICATE_ID);
//    }
//
//    @Test
//    void createTest() {
//        when(giftCertificateDao.findById(CERTIFICATE_ID)).thenReturn(certificate);
//        when(giftCertificateDao.create(certificate)).thenReturn(certificate);
//        GiftCertificate actualCertificate = service.create(certificate);
//        assertThat(actualCertificate, is(equalTo(certificate)));
//        verify(giftCertificateDao).create(certificate);
//    }
//
//    @Test
//    void addTagToCertificateTest(){
//        when(giftCertificateDao.findById(CERTIFICATE_ID)).thenReturn(certificate);
//        when(tagService.isTagValid(tag)).thenReturn(true);
//        when(tagService.findTagByName(TAG_NAME)).thenReturn(tag);
//        certificate.addTag(tag);
//        GiftCertificate actualCertificate = service.addTagToCertificate(tag, CERTIFICATE_ID);
//        assertThat(actualCertificate, is(equalTo(certificate)));
//        verify(giftCertificateDao,times(2)).findById(CERTIFICATE_ID);
//        verify(tagService).isTagValid(tag);
//        verify(tagService, times(2)).findTagByName(TAG_NAME);
//        verify(giftCertificateDao,times(1)).addTagToCertificate(tag, CERTIFICATE_ID);
//    }
//
//    @Test
//    void updateTest(){
//        GiftCertificate updatedCertificate = new GiftCertificate(CERTIFICATE_ID, CERTIFICATE_NEW_NAME, DESCRIPTION, PRICE, CREATION_DATE
//                , LAST_UPDATE_DATE,DURATION, tagSet);
//        when(giftCertificateDao.findById(CERTIFICATE_ID)).thenReturn(certificate);
//        when(giftCertificateDao.update(updatedCertificate)).thenReturn(updatedCertificate);
//        GiftCertificate actualCertificate = service.update(CERTIFICATE_ID, updatedCertificate);
//        assertThat(actualCertificate, is(equalTo(updatedCertificate)));
//        verify(giftCertificateDao,times(1)).findById(CERTIFICATE_ID);
//        verify(giftCertificateDao,times(1)).update(updatedCertificate);
//    }

}
