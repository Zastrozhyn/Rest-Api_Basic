package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.validator.TagValidator;

class TagServiceImplTest {
    private static final long TAG_ID = 1;
    private static final String TAG_NAME = "Tag";
    private static int expectedErrorCode;
    private static Tag tag;

    private TagServiceImpl tagService;
    private TagDaoImpl tagDao;
    private TagValidator validator;


//    @BeforeAll
//    static void init(){
//        expectedErrorCode = ExceptionCode.TAG_NOT_FOUND.getErrorCode();
//        tag = new Tag(TAG_ID, TAG_NAME);
//    }
//
//    @BeforeEach
//    public void setUp() {
//        tagDao = Mockito.mock(TagDaoImpl.class);
//        validator = new TagValidator();
//        tagService = new TagServiceImpl(tagDao, validator);
//    }
//
//    @AfterEach
//    void afterEachTest(){
//        verifyNoMoreInteractions(tagDao);
//    }
//
//    @Test
//    void createTest() {
//        when(tagDao.findTagByName(TAG_NAME)).thenReturn(null);
//        when(tagDao.create(tag)).thenReturn(tag);
//        when(tagDao.findById(TAG_ID)).thenReturn(tag);
//        Tag result = tagService.create(tag);
//        assertThat(result, is(equalTo(tag)));
//        verify(tagDao).findTagByName(TAG_NAME);
//        verify(tagDao).create(tag);
//    }
//
//    @Test
//    void findTagTest() {
//        when(tagDao.findById(TAG_ID)).thenReturn(tag);
//        Tag result = tagService.findById(TAG_ID);
//        assertThat(result, is(equalTo(tag)));
//        verify(tagDao).findById(TAG_ID);
//    }
//
//    @Test
//    void findTagThrowExceptionTest() {
//        when(tagDao.findById(TAG_ID)).thenReturn(null);
//        EntityException actualException = assertThrows(EntityException.class, () -> tagService.findById(TAG_ID));
//        assertThat(actualException.getErrorCode(), is(equalTo(expectedErrorCode)));
//        verify(tagDao).findById(TAG_ID);
//    }
//
//    @Test
//    void deleteThrowExceptionTest() {
//        when(tagDao.findById(TAG_ID)).thenReturn(null);
//        EntityException actualException = assertThrows(EntityException.class, () -> tagService.delete(TAG_ID));
//        assertThat(actualException.getErrorCode(), is(equalTo(expectedErrorCode)));
//        verify(tagDao).findById(TAG_ID);
//    }

}
