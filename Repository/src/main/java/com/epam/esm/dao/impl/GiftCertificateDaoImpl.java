package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.SqlQueryBuilder;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

;

@Log4j2
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    public static final String CERTIFICATE_ID = "id";
    private static final String ADD_TAG_TO_CERTIFICATE_QUERY = "INSERT INTO tag_certificate (tag_id, certificate_id) VALUES (?,?)";
    private static final String SELECT_ID_FROM_CERTIFICATE = "SELECT id FROM gift_certificate WHERE id=?";
    @PersistenceContext
    private EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public List<GiftCertificate> findAll(Integer offset, Integer limit) {
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);
        query.orderBy(criteriaBuilder.asc(root.get(CERTIFICATE_ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<GiftCertificate> findAllById(List<Long> idList) {
        Session session = entityManager.unwrap(Session.class);
        return session.byMultipleIds(GiftCertificate.class).withBatchSize(idList.size()).multiLoad(idList);
    }

    @Override
    public GiftCertificate findById(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }


    @Override
    public List<GiftCertificate> findByAttributes(List<String> tagList, String searchPart, String sortingField, String orderSort,
                                                  Integer offset, Integer limit) {
        String sqlQuery = SqlQueryBuilder.buildCertificateQueryForSearchAndSort(tagList, searchPart, sortingField, orderSort);
        return entityManager.createNativeQuery(sqlQuery, GiftCertificate.class).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public void addTagToCertificate(Tag tag, Long idCertificate){
        entityManager.createNativeQuery(ADD_TAG_TO_CERTIFICATE_QUERY)
                .setParameter(1, new TypedParameterValue(LongType.INSTANCE, tag.getId()))
                .setParameter(2, new TypedParameterValue(LongType.INSTANCE, idCertificate))
                .executeUpdate();
    }

    @Override
    public boolean exists(Long id) {
        try {
            return entityManager.createNativeQuery(SELECT_ID_FROM_CERTIFICATE).setParameter(1, id).getSingleResult() != null;
        } catch (NoResultException e) {
            return false;
        }
    }
}
