package com.epam.esm.service;


import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    Tag create(Tag tag);
    Tag findTag(Long id);
    List<Tag> findAll();
    void delete(Long id);
}
