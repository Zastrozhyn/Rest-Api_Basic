package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateExtractor;
import com.epam.esm.util.SqlQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateExtractor extractor;
    private final static String CREATE_GIFT_CERTIFICATE = "INSERT INTO gift_certificate " +
            "(name, description, price, duration, create_date, last_update_date) VALUES(?,?,?,?,?,?)";
    private final static  String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private final static String FIND_GIFT_CERTIFICATE_BY_ID = "SELECT gift_certificate.id,  gift_certificate.name, " +
            "description, price, duration, create_date, last_update_date, tag.id AS tag_id, tag.name AS tag_name " +
            "FROM gift_certificate " +
            "JOIN tag_certificate ON certificate_id=gift_certificate.id " +
            "JOIN tag ON tag_id=tag.id " +
            "WHERE gift_certificate.id=? ";
    private final static String FIND_ALL_GIFT_CERTIFICATE = "SELECT gift_certificate.id,  gift_certificate.name, " +
            "description, price, duration, create_date, last_update_date, tag.id AS tag_id, tag.name AS tag_name " +
            "FROM gift_certificate " +
            "JOIN tag_certificate ON certificate_id=gift_certificate.id " +
            "JOIN tag ON tag_id=tag.id ";
    private final static String FIND_ALL_CERTIFICATE_BY_TAG = "SELECT tag, id, name, description, duration," +
            " create_date, last_update_date, price " +
            "FROM gift_certificate JOIN tags_certificate ON id=tag_certificate.certificate_id WHERE tag=?";
    private final static String LAST_UPDATE_DATE_FIELD = "last_update_date";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateExtractor extractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.extractor = extractor;
    }

    @Override
    public void create(GiftCertificate giftCertificate) {
        jdbcTemplate.update(CREATE_GIFT_CERTIFICATE,giftCertificate.getName(),giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Override
    public boolean update(Long id, Map<String, Object> updatedFields) {
        updatedFields.put(LAST_UPDATE_DATE_FIELD, LocalDateTime.now());
        String query = SqlQueryBuilder.buildCertificateQueryForUpdate(updatedFields.keySet());
        List<Object> fields = new ArrayList<>(updatedFields.values());
        fields.add(id);
        return jdbcTemplate.update(query, fields.toArray()) == 1;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE, extractor);
    }

    @Override
    public GiftCertificate findById(Long id) {
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATE_BY_ID, extractor, id).stream().findAny().orElse(null);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }


    @Override
    public List<GiftCertificate> findByAttributes(String tagName, String searchPart, String sortingField, String orderSort) {
        String query = SqlQueryBuilder.buildCertificateQueryForSearchAndSort(tagName, searchPart, sortingField, orderSort);
        return jdbcTemplate.query(query, extractor);
    }

    @Override
    public List<GiftCertificate> findAllCertificateByTag(String tagName){
        return jdbcTemplate.query(FIND_ALL_CERTIFICATE_BY_TAG,
                new BeanPropertyRowMapper<>(GiftCertificate.class), tagName);
    }

}
