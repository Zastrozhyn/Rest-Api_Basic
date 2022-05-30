package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao {
    Tag create(Tag tag);
    Tag findTag(Long id);
    Tag findTagByName(String name);
    List<Tag> findAll();
    void delete(Long id);
}
