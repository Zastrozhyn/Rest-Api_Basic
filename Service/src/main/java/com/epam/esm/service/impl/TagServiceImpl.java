package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.TagAlreadyExistsException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void create(Tag tag) {
        if(tagDao.findTagByName(tag.getName()) != null){
            throw new TagAlreadyExistsException();
        }
        if(tagValidator.isValid(tag)){
            tagDao.create(tag);
        }
        if (!tagValidator.isValid(tag)){
            throw  new EntityNotFoundException();
        }
    }

    @Override
    public Tag findTag(Long id) {
        if (tagDao.findTag(id) == null){
            throw new EntityNotFoundException();
        }
        return tagDao.findTag(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public void delete(Long id) {
        tagDao.delete(id);
    }

}
