package com.epam.esm.service;


import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Tag create(Tag tag);
    Tag findTag(Long id);
    List<Tag> findAll();
    void delete(Long id);
    boolean isTagValid(Tag tag);
    boolean isTagExist(Tag tag);
    void addTagToCertificate(Tag tag, Long idCertificate);
    Tag findTagByName(String name);
    void deleteTagFromCertificate(Tag tag, Long idCertificate);
    List<Tag> findTagsByName(Set<Tag> tags);
    void createTags(Set<Tag> tags);
    void addTagSToCertificate(List<Tag> tags, Long idCertificate);
}
