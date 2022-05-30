package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exception.ExceptionCode.NOT_VALID_TAG_DATA;
import static com.epam.esm.exception.ExceptionCode.TAG_NOT_FOUND;

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
    public Tag create(Tag tag) {
        if(isTagValid(tag) && tagDao.findTagByName(tag.getName()) == null){
            return tagDao.create(tag);
        }
        return findTagByName(tag.getName());
    }

    @Override
    public Tag findTag(Long id) {
        Tag tag = tagDao.findTag(id);
        if (tag == null){
            throw new EntityException(TAG_NOT_FOUND.getErrorCode());
        }
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public void delete(Long id) {
        if (tagDao.findTag(id) != null){
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
