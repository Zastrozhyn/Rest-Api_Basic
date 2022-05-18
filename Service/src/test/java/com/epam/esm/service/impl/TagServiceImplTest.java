package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TagServiceImplTest {
    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Tag";
    private static int expectedErrorCode;
    private static Tag tag;

    private TagServiceImpl tagService;
    private TagDaoImpl tagDao;
    private TagValidator validator;


    @BeforeAll
    static void init(){
        expectedErrorCode = ExceptionCode.TAG_NOT_FOUND.getErrorCode();
        tag = new Tag(TAG_ID, TAG_NAME);
    }

    @BeforeEach
    public void setUp() {
        tagDao = Mockito.mock(TagDaoImpl.class);
        validator = new TagValidator();
        tagService = new TagServiceImpl(tagDao, validator);
    }

    @AfterEach
    public void afterEachTest(){
        verify(tagDao).findTag(TAG_ID);
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    void createTest() {
        when(tagDao.findTagByName(TAG_NAME)).thenReturn(null);
        when(tagDao.create(tag)).thenReturn(tag.getId());
        when(tagDao.findTag(TAG_ID)).thenReturn(tag);
        Tag result = tagService.create(tag);
        assertThat(result, is(equalTo(tag)));

        verify(tagDao).findTagByName(TAG_NAME);
        verify(tagDao).create(tag);
    }

    @Test
    void findTagTest() {
        when(tagDao.findTag(TAG_ID)).thenReturn(tag);
        Tag result = tagService.findTag(TAG_ID);
        assertThat(result, is(equalTo(tag)));
    }

    @Test
    void findTagThrowExceptionTest() {
        when(tagDao.findTag(TAG_ID)).thenReturn(null);
        EntityException actualException = assertThrows(EntityException.class, () -> tagService.findTag(TAG_ID));
        assertThat(actualException.getErrorCode(), is(equalTo(expectedErrorCode)));
    }

    @Test
    void deleteThrowExceptionTest() {
        when(tagDao.findTag(TAG_ID)).thenReturn(null);
        EntityException actualException = assertThrows(EntityException.class, () -> tagService.delete(TAG_ID));
        assertThat(actualException.getErrorCode(), is(equalTo(expectedErrorCode)));
    }
}
