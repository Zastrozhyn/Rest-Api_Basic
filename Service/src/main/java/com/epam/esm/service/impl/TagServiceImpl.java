package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.TagDtoConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exception.ExceptionCode.NOT_VALID_TAG_DATA;
import static com.epam.esm.exception.ExceptionCode.TAG_NOT_FOUND;
import static com.epam.esm.util.ApplicationUtil.*;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagValidator tagValidator, TagDtoConverter converter) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
        this.converter = converter;
    }

    private final TagDtoConverter converter;



    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = converter.convertFromDto(tagDto);
        if(isTagValid(tag) && tagDao.findTagByName(tag.getName()) == null){
            return converter.convertToDto(tagDao.create(tag));
        }
        return converter.convertToDto(findTagByName(tag.getName()));
    }

    @Override
    public TagDto findTag(Long id) {
        Tag tag = tagDao.findTag(id);
        if (tag == null){
            throw new EntityException(TAG_NOT_FOUND.getErrorCode());
        }
        return converter.convertToDto(tag);
    }

    @Override
    public List<TagDto> findAll(Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return tagDao.findAll(calculateOffset(pageSize,page), pageSize)
                .stream()
                .map(converter::convertToDto)
                .toList();
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
