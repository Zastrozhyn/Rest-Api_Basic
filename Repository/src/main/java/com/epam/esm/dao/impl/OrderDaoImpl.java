package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.epam.esm.constant.StringConstant.ID;
import static com.epam.esm.constant.StringConstant.USER;

@Repository
public class OrderDaoImpl implements OrderDao {
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
    public Order findOrder(Long id) {
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.where(criteriaBuilder.equal(root.get("id"), id));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Order> findAll(Integer offset, Integer limit) {
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.orderBy(criteriaBuilder.asc(root.get(ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findOrder(id));
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
        query.orderBy(criteriaBuilder.asc(root.get(ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
