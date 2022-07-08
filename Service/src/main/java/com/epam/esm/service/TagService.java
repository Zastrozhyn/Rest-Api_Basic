package com.epam.esm.service;


import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    Tag create(Tag tag);
    Tag findById(Long id);
    void delete(Long id);
    boolean isTagValid(Tag tag);
    boolean isTagExist(Tag tag);
    Tag findTagByName(String name);
    List<Tag> findAll(Integer pageSize, Integer page);
}
