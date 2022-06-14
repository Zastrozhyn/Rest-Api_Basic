package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TestConfig.class})
@Transactional
@ActiveProfiles("test")
public class TagDaoImplTest {
    private final TagDaoImpl tagDao;
    private Tag tag;

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @BeforeEach
    void setUp() {
        tag = Tag.builder().name("NewTag").build();
    }

    @Test
    void create() {
        Tag actual = tagDao.create(tag);
        assertEquals("NewTag", actual.getName());
    }


    @Test
    void findById() {
        Tag actual = tagDao.findTag(1L);
        assertNotNull(actual);
    }

    @Test
    void FindByIdReturnsEmptyWithNonExistentTag() {
        Tag actual = tagDao.findTag(100L);
        assertNull(actual);
    }

    @Test
    void findByName() {
        Tag actual = tagDao.findTagByName("HR");
        assertNotNull(actual);
    }

    @Test
    void findByNameReturnsEmptyWithNonExistentTag() {
        Tag actual = tagDao.findTagByName("HR222");
        assertNull(actual);
    }

    @Test
    void findAll() {
        List<Tag> tags = tagDao.findAll(0, 4);
        assertEquals(4, tags.size());
    }

}
