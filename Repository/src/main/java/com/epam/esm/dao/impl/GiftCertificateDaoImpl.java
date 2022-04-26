package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateExtractor;
import com.epam.esm.mapper.IdMapper;
import com.epam.esm.util.SqlQueryBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateExtractor extractor;
    private final IdMapper idMapper;
    private final static String CREATE_GIFT_CERTIFICATE_QUERY = "INSERT INTO gift_certificate (name, description, price, " +
            "duration, create_date, last_update_date) VALUES(?,?,?,?,?,?) RETURNING id AS new_id";
    private final static  String DELETE_CERTIFICATE_QUERY = "DELETE FROM gift_certificate WHERE id=?";
    private final static String FIND_GIFT_CERTIFICATE_BY_ID_QUERY =
            "SELECT certificate.id,  certificate.name, certificate.description, certificate.price," +
            "certificate.duration, certificate.create_date, certificate.last_update_date," +
            "tag.id AS tag_id, tag.name AS tag_name " +
            "FROM gift_certificate AS certificate " +
            "LEFT JOIN tag_certificate AS tag_map ON tag_map.certificate_id=certificate.id " +
            "LEFT JOIN tag ON tag_map.tag_id=tag.id " +
            "WHERE certificate.id=? ";
    private final static String FIND_ALL_GIFT_CERTIFICATE_QUERY =
            "SELECT certificate.id,  certificate.name, certificate.description, certificate.price," +
            "certificate.duration, certificate.create_date, certificate.last_update_date," +
            "tag.id AS tag_id, tag.name AS tag_name " +
            "FROM gift_certificate AS certificate " +
            "LEFT JOIN tag_certificate AS tag_map ON tag_map.certificate_id=certificate.id " +
            "LEFT JOIN tag ON tag_map.tag_id=tag.id ";
    private final static String UPDATE_LAST_UPDATE_DATE_QUERY = "UPDATE gift_certificate SET last_update_date=? WHERE id=? ";
    private final static String LAST_UPDATE_DATE_FIELD = "last_update_date";


    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateExtractor extractor, IdMapper idMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.extractor = extractor;
        this.idMapper = idMapper;
    }

    @Override
    public Long create(GiftCertificate giftCertificate) {
        return jdbcTemplate.queryForObject(CREATE_GIFT_CERTIFICATE_QUERY, idMapper, giftCertificate.getName(),giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), LocalDateTime.now(), LocalDateTime.now());
    }

    @Override
    public void update(Long id, Map<String, Object> updatedFields) {
        updatedFields.put(LAST_UPDATE_DATE_FIELD, LocalDateTime.now());
        String query = SqlQueryBuilder.buildCertificateQueryForUpdate(updatedFields.keySet());
        List<Object> fields = new ArrayList<>(updatedFields.values());
        fields.add(id);
        jdbcTemplate.update(query, fields.toArray());
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_QUERY, extractor);
    }

    @Override
    public GiftCertificate findById(Long id) {
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATE_BY_ID_QUERY, extractor, id).stream().findAny().orElse(null);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_CERTIFICATE_QUERY, id);
    }


    @Override
    public List<GiftCertificate> findByAttributes(String tagName, String searchPart, String sortingField, String orderSort) {
        String query = SqlQueryBuilder.buildCertificateQueryForSearchAndSort(tagName, searchPart, sortingField, orderSort);
        return jdbcTemplate.query(query, extractor);
    }
    @Override
    public void updateDate(Long id) {
        jdbcTemplate.update(UPDATE_LAST_UPDATE_DATE_QUERY, LocalDateTime.now(), id);
    }
}
