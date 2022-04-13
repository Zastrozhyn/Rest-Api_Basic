package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;

    private final static String CREATE_TAG = "INSERT INTO tag(name) VALUES(?)";
    private final static String FIND_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    private final static String FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";
    private final static String FIND_ALL_TAG = "SELECT id, name FROM tag";
    private final static String DELETE_TAG = "DELETE FROM tag WHERE id = ?";
    private final String ADD_TAG_TO_CERTIFICATE = "INSERT INTO tags_certificate (tag, certificate_id) VALUES (?,?)";
    private final static  String DELETE_TAG_FROM_CERTIFICATE = "DELETE FROM tags_certificate WHERE certificate_id=?" +
            "AND tag=?";
    private final static String FIND_ALL_TAG_IN_CERTIFICATE = "SELECT id, name FROM tags_certificate " +
            "JOIN tag ON tag_id=id WHERE certificate_id=?";
    private final static String DELETE_ALL_TAG_FROM_CERTIFICATE = "DELETE FROM tags_certificate WHERE certificate_id=?";


    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Tag tag) {
        jdbcTemplate.update(CREATE_TAG, tag.getName());
    }

    @Override
    public Tag findTag(Long id) {
        return  jdbcTemplate.query(FIND_TAG_BY_ID,
                new BeanPropertyRowMapper<>(Tag.class), id).stream().findFirst().orElse(null);
    }

    @Override
    public Tag findTagByName(String name){
        return jdbcTemplate.queryForObject(FIND_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAG, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_TAG, id);
    }

    @Override
    public void addTagToCertificate(Tag tag, Long idCertificate){
        jdbcTemplate.update(ADD_TAG_TO_CERTIFICATE, tag.getName(), idCertificate);
    }

    @Override
    public void deleteTagFromCertificate(Tag tag, Long idCertificate){
        jdbcTemplate.update(DELETE_TAG_FROM_CERTIFICATE, idCertificate, tag.getName());
    }

    @Override
    public List<Tag> findAllTagInCertificate(Long idCertificate){
        return jdbcTemplate.query(FIND_ALL_TAG_IN_CERTIFICATE, new BeanPropertyRowMapper<>(Tag.class),
                idCertificate);
    }

    @Override
    public void deleteAllTagFromCertificate(Long idCertificate){
        jdbcTemplate.update(DELETE_ALL_TAG_FROM_CERTIFICATE, idCertificate);
    }

}
