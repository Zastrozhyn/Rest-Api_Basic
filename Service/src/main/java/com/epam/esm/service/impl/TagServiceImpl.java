package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exception.ExceptionCode.NOT_VALID_TAG_DATA;
import static com.epam.esm.exception.ExceptionCode.TAG_NOT_FOUND;
import static com.epam.esm.util.ApplicationUtil.*;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        if(isTagValid(tag) && tagDao.findTagByName(tag.getName()) == null){
            return tagDao.create(tag);
        }
        return findTagByName(tag.getName());
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = tagDao.findById(id);
        if (tag == null){
            throw new EntityException(TAG_NOT_FOUND.getErrorCode());
        }
        return tag;
    }

    @Override
    public List<Tag> findAll(Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return tagDao.findAll(calculateOffset(pageSize,page), pageSize);
    }

    @Override
    @Transactional
    public void create1000() {
        for (int i = 1; i < 1000; i++){
            Tag tag = new Tag();
            tag.setName("Tag".concat(String.valueOf(i)));
            create(tag);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (tagDao.findById(id) != null){
            tagDao.delete(id);
        }
        else throw new EntityException(TAG_NOT_FOUND.getErrorCode());
    }

    @Override
    public boolean isTagExist(Tag tag){
        if(tagDao.findTagByName(tag.getName()) == null){
            throw new EntityException(TAG_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    @Override
    public Tag findTagByName(String name) {
        return tagDao.findTagByName(name);
    }

    @Override
    public boolean isTagValid(Tag tag){
        if(!tagValidator.isValid(tag)){
            throw new EntityException(NOT_VALID_TAG_DATA.getErrorCode());
        }
        return true;
    }
}
