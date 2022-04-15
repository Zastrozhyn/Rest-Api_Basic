package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagDao {
    void create(Tag tag);
    Tag findTag(Long id);
    Tag findTagByName(String name);
    List<Tag> findAll();
    void delete(Long id);
    void addTagToCertificate(Tag tag, Long idCertificate);
    void deleteTagFromCertificate(Tag tag, Long idCertificate);
    Set<Tag> findAllTagInCertificate(Long idCertificate);
    void deleteAllTagFromCertificate(Long idCertificate);
}
