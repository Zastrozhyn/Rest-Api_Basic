package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Log4j2
@Repository
public class TagDaoImpl implements TagDao {
    public static final String TAG_ID = "id";
    private static final String SELECT_ID_FROM_TAG = "SELECT id FROM tag WHERE id=?";
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
    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag findTagByName(String name){
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.where(criteriaBuilder.equal(root.get("name"), name));
        try{
            return entityManager.createQuery(query).getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Tag> findAll(Integer offset, Integer limit) {
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.orderBy(criteriaBuilder.asc(root.get(TAG_ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public boolean exists(Long id) {
        try {
            return entityManager.createNativeQuery(SELECT_ID_FROM_TAG).setParameter(1, id).getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }
}
