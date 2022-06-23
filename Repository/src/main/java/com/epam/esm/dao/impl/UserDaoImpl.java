package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserWithTotalCost;
import com.epam.esm.util.SqlQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    public static final String USER_ID = "id";
    private static final String GET_MOST_POPULAR_TAG_OF_RICHEST_USER = """
        SELECT t.id, t.name, count(t.id) FROM tag as t
                JOIN tag_certificate as tc ON tc.tag_id=t.id
                JOIN order_certificates as oc ON oc.certificate_id=tc.certificate_id
                JOIN orders as o ON o.id=oc.order_id AND o.user_id=(
                SELECT u.id FROM users as u
                JOIN orders as ord ON ord.user_id=u.id
                GROUP BY u.id ORDER BY sum(ord.cost) DESC LIMIT 1
                ) GROUP BY t.id HAVING count(t.id) =
        (SELECT max(tagsCount) FROM (SELECT count(t.id) AS tagsCount FROM tag as t
                JOIN tag_certificate as tc ON tc.tag_id=t.id
                JOIN order_certificates as oc ON oc.certificate_id=tc.certificate_id
                JOIN orders as o ON o.id=oc.order_id AND o.user_id=(
                SELECT u.id FROM users as u
                JOIN orders as ord ON ord.user_id=u.id
                GROUP BY u.id ORDER BY sum(ord.cost) DESC LIMIT 1
                ) GROUP BY t.id) AS tagsc)""";

    private static final String GET_USERS_WITH_TOTAL_COST = """
        SELECT users.id , users.name, sum(cost) as total_cost FROM users JOIN orders on users.id = orders.user_id 
        group by users.id order by sum(cost) """;

    @PersistenceContext
    private EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll(Integer offset, Integer limit) {
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.orderBy(criteriaBuilder.asc(root.get(USER_ID)));
        return entityManager.createQuery(query).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public User update(User user) {
        entityManager.merge(user);
        return user;
    }

    @Override
    public List<Tag> getMostPopularTag(){
        return entityManager.createNativeQuery(GET_MOST_POPULAR_TAG_OF_RICHEST_USER, Tag.class).getResultList();
    }

    @Override
    public BigDecimal findTotalCost(Long id) {
        return (BigDecimal) entityManager.createNativeQuery(SqlQueryBuilder.buildTotalCostQuery(id)).getSingleResult();
    }

    @Override
    public List<UserWithTotalCost> getUsersWithTotalCost() {
        return entityManager.createNativeQuery(GET_USERS_WITH_TOTAL_COST, UserWithTotalCost.class).getResultList();
    }
}
