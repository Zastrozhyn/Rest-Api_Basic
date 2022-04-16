package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.IdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;
    private final IdMapper idMapper;

    private final static String CREATE_TAG = "INSERT INTO tag(name) VALUES(?) RETURNING id AS new_id";
    private final static String FIND_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    private final static String FIND_TAG_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";
    private final static String FIND_ALL_TAG = "SELECT id, name FROM tag";
    private final static String DELETE_TAG = "DELETE FROM tag WHERE id = ?";
    private final String ADD_TAG_TO_CERTIFICATE = "INSERT INTO tag_certificate (tag_id, certificate_id) VALUES (?,?)";
    private final static  String DELETE_TAG_FROM_CERTIFICATE = "DELETE FROM tag_certificate WHERE certificate_id=?" +
            "AND tag=?";
    private final static String FIND_ALL_TAG_IN_CERTIFICATE = "SELECT id, name FROM tag_certificate " +
            "JOIN tag ON tag_id=id WHERE certificate_id=?";
    private final static String DELETE_ALL_TAG_FROM_CERTIFICATE = "DELETE FROM tag_certificate WHERE certificate_id=?";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, IdMapper idMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.idMapper = idMapper;
    }

    @Override
    public Long create(Tag tag) {
        return jdbcTemplate.queryForObject(CREATE_TAG, idMapper, tag.getName());
    }

    @Override
    public Tag findTag(Long id) {
        Tag tag;
        try{
            tag = jdbcTemplate.queryForObject(FIND_TAG_BY_ID,
                    new BeanPropertyRowMapper<>(Tag.class), id);
        } catch (EmptyResultDataAccessException e) {
            tag = null;
        }
        return tag;
    }

    @Override
    public Tag findTagByName(String name){
        Tag tag;
        try{
           tag = jdbcTemplate.queryForObject(FIND_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name);
        } catch (EmptyResultDataAccessException e) {
            tag = null;
        }
        return tag;
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
    public Set<Tag> findAllTagInCertificate(Long idCertificate){
        return new HashSet<>(jdbcTemplate.query(FIND_ALL_TAG_IN_CERTIFICATE, new BeanPropertyRowMapper<>(Tag.class),
                idCertificate));
    }

    @Override
    public void deleteAllTagFromCertificate(Long idCertificate){
        jdbcTemplate.update(DELETE_ALL_TAG_FROM_CERTIFICATE, idCertificate);
    }

}