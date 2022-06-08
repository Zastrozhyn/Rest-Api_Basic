package com.epam.esm.service;


import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    TagDto create(TagDto tag);
    TagDto findTag(Long id);
    void delete(Long id);
    boolean isTagValid(Tag tag);
    boolean isTagExist(Tag tag);
    Tag findTagByName(String name);
    List<TagDto> findAll(Integer pageSize, Integer page);
}
