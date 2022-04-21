package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

class TagServiceImplTest {
    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Tag";

    private TagServiceImpl tagService;
    private TagDaoImpl tagDao;
    private TagValidator validator;

    private Tag tag;

    @BeforeEach
    public void setUp() {
        tag = new Tag(TAG_ID, TAG_NAME);
        tagDao = Mockito.mock(TagDaoImpl.class);
        validator = new TagValidator();
        tagService = new TagServiceImpl(tagDao, validator);
    }
    @Test
    void createTest() {
        doReturn(null).when(tagDao).findTagByName(any());
        doReturn(tag).when(tagDao).findTag(any());
        assertEquals(tag, tagService.create(tag));
    }

    @Test
    void findTagTest() {
        doReturn(tag).when(tagDao).findTag(any());
        assertEquals(tag, tagService.findTag(TAG_ID));
    }

    @Test
    void findTagThrowExceptionTest() {
        doReturn(null).when(tagDao).findTag(any());
        assertThrows(EntityException.class, () -> tagService.findTag(TAG_ID));
    }
    @Test
    void deleteTest() {
        doReturn(null).when(tagDao).findTag(any());
        assertThrows(EntityException.class, () -> tagService.delete(TAG_ID));
    }
}
