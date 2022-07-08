package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("test")
class GiftCertificateDaoImplTest {
    private final GiftCertificateDaoImpl certificateDao;
    private Tag tag;
    private GiftCertificate certificate;
    private GiftCertificate deletedCertificate;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
        this.certificateDao = certificateDao;
    }

    @BeforeEach
    void setUp() {
        tag = Tag.builder().id(1L).name("NewTagName").build();
        certificate = GiftCertificate.builder()
                .name("NewCertificate")
                .description("Description")
                .price(new BigDecimal("10"))
                .duration(5)
                .createDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .lastUpdateDate(LocalDateTime.of(1990, 10, 10, 10, 10))
                .tags(Set.of(tag))
                .build();
        deletedCertificate = GiftCertificate.builder()
                .id(1L)
                .build();
    }

    @Test
    void update() {
        certificate.setId(3L);
        certificateDao.update(certificate);
        assertTrue(true);
    }

    @Test
    void findByIdWithExistentEntity() {
        GiftCertificate actual = certificateDao.findById(1L);
        assertNotNull(actual);
    }

    @Test
    void findByIdWithNonExistentEntity() {
        GiftCertificate actual = certificateDao.findById(100L);
        assertNull(actual);
    }

    @Test
    void findAll() {
        List<GiftCertificate> actual = certificateDao.findAll(0, 3);
        assertEquals(3, actual.size());
    }


    @Test
    void delete() {
        certificateDao.delete(deletedCertificate.getId());
        assertTrue(true);
    }

}
