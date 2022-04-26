package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    private final String CERTIFICATE_ID = "id";
    private final String TAG_ID = "tag_id";
    private final String TAG_NAME = "tag_name";

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> giftCertificates = new LinkedHashMap<>();
        while (rs.next()) {
            Long key = rs.getLong(CERTIFICATE_ID);
            giftCertificates.putIfAbsent(key, GiftCertificateMapper.buildCertificate(rs));
            Tag tag = new Tag(rs.getLong(TAG_ID), rs.getString(TAG_NAME));
            giftCertificates.get(key).addTag(tag);
        }
        return new ArrayList<>(giftCertificates.values());
    }
}
