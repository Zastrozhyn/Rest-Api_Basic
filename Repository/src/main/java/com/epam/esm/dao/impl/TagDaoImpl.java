package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Log4j2
@Repository
@Transactional
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Tag findTag(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag findTagByName(String name){
        return entityManager.find(Tag.class, name);
    }

    @Override
    public List<Tag> findAll() {
        CriteriaQuery<Tag> criteria = criteriaBuilder.createQuery(Tag.class);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findTag(id));
    }

}
