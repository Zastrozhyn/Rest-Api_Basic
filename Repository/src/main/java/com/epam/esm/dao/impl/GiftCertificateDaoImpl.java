package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;
    private final static String CREATE_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES(?,?,?,?,?,?)";
    private final static String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificate SET " +
            "name=?, description=?, price=?, duration=?, last_update_date=? WHERE id=?";
    private final static  String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private final static String FIND_GIFT_CERTIFICATE_BY_ID = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private final static String FIND_GIFT_CERTIFICATE_BY_NAME = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate WHERE name LIKE ('%%' '%s' '%%')";
    private final static String FIND_GIFT_CERTIFICATE_BY_DESCRIPTION = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate WHERE description LIKE ('%%' '%s' '%%')";
    private final static String FIND_ALL_GIFT_CERTIFICATE = "SELECT id, name, description, price, duration," +
            " create_date, last_update_date FROM gift_certificate";
    private final static String FIND_ALL_CERTIFICATE_BY_TAG = "SELECT tag, id, name, description, duration," +
            " create_date, last_update_date, price " +
            "FROM gift_certificate JOIN tags_certificate ON id=tags_certificate.certificate_id WHERE tag=?";


    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(GiftCertificate giftCertificate) {
        jdbcTemplate.update(CREATE_GIFT_CERTIFICATE,giftCertificate.getName(),giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Override
    public void update(GiftCertificate giftCertificate, Map<String, Object> updatedFields) {
        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE, giftCertificate.getName(),giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),  LocalDateTime.now(), giftCertificate.getId());
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public GiftCertificate findById(Long id) {
        return  jdbcTemplate.queryForObject(FIND_GIFT_CERTIFICATE_BY_ID,
                new BeanPropertyRowMapper<>(GiftCertificate.class), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        String sqlQuery = String.format(FIND_GIFT_CERTIFICATE_BY_NAME, name);
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        String sqlQuery = String.format(FIND_GIFT_CERTIFICATE_BY_NAME, description);
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public List<GiftCertificate> findAllCertificateByTag(String tagName){
        return jdbcTemplate.query(FIND_ALL_CERTIFICATE_BY_TAG,
                new BeanPropertyRowMapper<>(GiftCertificate.class), tagName);
    }

}
