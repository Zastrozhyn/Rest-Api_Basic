package com.epam.esm.dao.dataJpa;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateDaoJpa extends JpaRepository<GiftCertificate, Long> {
    Page<GiftCertificate> findAllByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
    Page<GiftCertificate> findAllByTagsIn(List<Tag> tags, Pageable pageable);
}
