package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Repository
public class OrderDaoImpl implements OrderDao {
    public static final String ORDER_ID = "id";
    public static final String USER = "user";
    @PersistenceContext
    private EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.where(criteriaBuilder.equal(root.get("id"), id));
        Order order = entityManager.createQuery(query).getSingleResult();
        entityManager.detach(order);
        order.setCertificateList(setCertificateByRev(order.getCertificateList(), order.getOrderDate()));
        return order;
    }

    @Override
    public List<Order> findAll(Integer offset, Integer limit) {
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.orderBy(criteriaBuilder.asc(root.get(ORDER_ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public Order update(Order order) {
        return entityManager.merge(order);
    }

    @Override
    public List<Order> findAllUsersOrder(User user, Integer offset, Integer limit){
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get(USER), user));
        query.orderBy(criteriaBuilder.asc(root.get(ORDER_ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    private List<GiftCertificate> setCertificateByRev(List<GiftCertificate> certificates, LocalDateTime date){
        List<GiftCertificate> revCertificates = new ArrayList<>();
        for (GiftCertificate certificate : certificates){
            GiftCertificate revCertificate = getCertificateByRev(certificate, date);
            revCertificate.setLastUpdateDate(certificate.getLastUpdateDate());
            revCertificate.setCreateDate(certificate.getCreateDate());
            revCertificate.setTags(certificate.getTags());
            revCertificates.add(revCertificate);
        }
        return revCertificates;
    }

    private GiftCertificate getCertificateByRev(GiftCertificate giftCertificate, LocalDateTime date){
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        Number rev = findRevByDate(getRevDates(giftCertificate), date);
        return auditReader.find(GiftCertificate.class, giftCertificate.getId(), rev);
    }

    private HashMap<Number,LocalDateTime> getRevDates(GiftCertificate giftCertificate){
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        HashMap<Number,LocalDateTime> revDates = new HashMap<>();
        List<Number> revisionNumbers = auditReader.getRevisions(GiftCertificate.class,giftCertificate.getId());
        for (Number rev : revisionNumbers) {
            revDates.put(rev, dateConverter(auditReader.getRevisionDate(rev)));
        }
        return revDates;
    }

    private LocalDateTime dateConverter(Date date){
        return  date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private Number findRevByDate(HashMap<Number,LocalDateTime> revDates, LocalDateTime date){
        LocalDateTime nearestDate = revDates
                .values()
                .stream()
                .filter(o -> o.isBefore(date))
                .max(LocalDateTime::compareTo)
                .get();
        return revDates.
                entrySet()
                .stream()
                .filter(entry -> nearestDate.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().
                get();
    }
}
