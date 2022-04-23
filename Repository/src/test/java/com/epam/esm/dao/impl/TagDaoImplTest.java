package com.epam.esm.dao.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagDaoImplTest {
    private static final long EXISTING_TAG_ID = 1;
    private static final Integer AMOUNT_OF_TAGS_IN_DB = 4;
    private static final String EXISTING_TAG_NAME = "IT";
    private final TagDaoImpl tagDao;
    private static Tag expectedTag;

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @BeforeEach
    void setUp(){
        expectedTag = new Tag(EXISTING_TAG_ID, EXISTING_TAG_NAME);
    }

    @Test
    @Order(1)
    void findByIdTest() {
        Tag actual = tagDao.findTag(EXISTING_TAG_ID);
        assertThat(actual, is(equalTo(expectedTag)));
    }

    @Test
    @Order(2)
    void FindByIdReturnsEmptyWithNonExistingTag() {
        Tag actual = tagDao.findTag(100L);
        assertThat(actual, is(equalTo(null)));
    }

    @Test
    @Order(3)
    void findAllTest() {
        List<Tag> tags = tagDao.findAll();
        assertThat(tags.size(), is(equalTo(AMOUNT_OF_TAGS_IN_DB)));
    }

    @Test
    @Order(4)
    void findTagsByNameTes() {
        Set<Tag> tags = Set.of(expectedTag);
        List<Tag> tagList = tagDao.findTagsByName(tags);
        assertThat(tagList.get(0), is(equalTo(expectedTag)));
    }

    @Test
    @Order(5)
    void findByNameTest() {
        Tag actual = tagDao.findTagByName(EXISTING_TAG_NAME);
        assertThat(actual, is(equalTo(expectedTag)));
    }

    @Test
    @Order(6)
    void deleteTest() {
        tagDao.delete(1L);
        List<Tag> tags = tagDao.findAll();
        assertThat(tags.size(), is(equalTo(AMOUNT_OF_TAGS_IN_DB - 1)));
    }
}
