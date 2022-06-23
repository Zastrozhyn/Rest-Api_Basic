package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao {
    Tag create(Tag tag);
    Tag findById(Long id);
    Tag findTagByName(String name);
    void delete(Long id);
    List<Tag> findAll(Integer offset, Integer limit);
}
