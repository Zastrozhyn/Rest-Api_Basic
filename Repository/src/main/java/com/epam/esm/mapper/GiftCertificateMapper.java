package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private final String TAG_ID = "tag_id";
    private final String TAG_NAME = "tag_name";
    private final static String CERTIFICATE_ID = "id";
    private final static String CERTIFICATE_NAME = "name";
    private final static String CERTIFICATE_DESCRIPTION = "description";
    private final static String CERTIFICATE_PRICE = "price";
    private final static String CERTIFICATE_DURATION = "duration";
    private final static String CERTIFICATE_CREATE_DATE = "create_date";
    private final static String CERTIFICATE_LAST_UPDATE_DATE = "last_update_date";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss.SSSSSS");

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = buildCertificate(rs);
        Tag tag = new Tag(rs.getLong(TAG_ID), rs.getString(TAG_NAME));
        certificate.addTag(tag);
        while (rs.next()){
            tag = new Tag(rs.getLong(TAG_ID), rs.getString(TAG_NAME));
            certificate.addTag(tag);
        }
        return certificate;
    }

    public static GiftCertificate buildCertificate(ResultSet rs) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong(CERTIFICATE_ID));
        certificate.setName(rs.getString(CERTIFICATE_NAME));
        certificate.setDescription(rs.getString(CERTIFICATE_DESCRIPTION));
        certificate.setPrice(rs.getBigDecimal(CERTIFICATE_PRICE));
        certificate.setDuration(rs.getInt(CERTIFICATE_DURATION));
        certificate.setCreateDate(LocalDateTime.parse(rs.getString(CERTIFICATE_CREATE_DATE),FORMATTER));
        certificate.setLastUpdateDate(LocalDateTime.parse(rs.getString(CERTIFICATE_LAST_UPDATE_DATE), FORMATTER));
        return certificate;
    }
}
