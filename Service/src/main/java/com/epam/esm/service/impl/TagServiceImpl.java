package com.epam.esm.service.impl;

import com.epam.esm.dao.dataJpa.TagDaoJpa;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exception.ExceptionCode.NOT_VALID_TAG_DATA;
import static com.epam.esm.exception.ExceptionCode.TAG_NOT_FOUND;
import static com.epam.esm.util.PaginationUtil.checkPage;
import static com.epam.esm.util.PaginationUtil.checkPageSize;

@Service
public class TagServiceImpl implements TagService {
    private final TagDaoJpa tagDao;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDaoJpa tagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.tagValidator = tagValidator;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Tag create(Tag tag) {
        if(isTagValid(tag) && tagDao.findByName(tag.getName()).isEmpty()){
            return tagDao.save(tag);
        }
        return findTagByName(tag.getName());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Tag findById(Long id) {
        return tagDao.findById(id).orElseThrow(() -> new EntityException(TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<Tag> findAll(Integer pageSize, Integer page) {
        page = checkPage(page);
        pageSize = checkPageSize(pageSize);
        return tagDao.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long id) {
        isTagExist(id);
        tagDao.deleteById(id);
    }

    @Override
    public boolean isTagExist(Tag tag){
        if(tagDao.findByName(tag.getName()).isEmpty()){
            throw new EntityException(TAG_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    public boolean isTagExist(Long tagId){
        if(!tagDao.existsById(tagId)){
            throw new EntityException(TAG_NOT_FOUND.getErrorCode());
        }
        return true;
    }

    @Override
    public Tag findTagByName(String name) {
        return tagDao.findByName(name).orElse(null);
    }

    @Override
    public boolean isTagValid(Tag tag){
        if(!tagValidator.isValid(tag)){
            throw new EntityException(NOT_VALID_TAG_DATA.getErrorCode());
        }
        return true;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Tag> getMostPopularTag(){
        return tagDao.getMostPopularTag();
    }
}
