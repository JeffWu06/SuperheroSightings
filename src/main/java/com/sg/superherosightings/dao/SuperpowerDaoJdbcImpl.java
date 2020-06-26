/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jeff
 */
public class SuperpowerDaoJdbcImpl implements SuperpowerDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_SUPERPOWER
            = "insert into Superpower (PowerDescription) values (?)";
    
    private static final String SQL_DELETE_SUPERPOWER
            = "delete from Superpower where SuperpowerID = ?";
    
    private static final String SQL_UPDATE_SUPERPOWER
            = "update Superpower set PowerDescription = ? where SuperpowerID = ?";
    
    private static final String SQL_SELECT_SUPERPOWER
            = "select * from Superpower where SuperpowerID = ?";
    
    private static final String SQL_SELECT_ALL_SUPERPOWERS
            = "select * from Superpower";
    
    private static final String SQL_SELECT_SUPERPOWERS_BY_SUPERHUMAN
            = "select s.SuperpowerID, s.PowerDescription from Superpower s "
            + "inner join SuperhumanSuperpower ss on s.SuperpowerID = ss.SuperpowerID "
            + "where ss.SuperhumanID = ?";
    
    private static final String SQL_SELECT_NONPOSSESSED_SUPERPOWERS_BY_SUPERHUMAN
            = "select distinct s.SuperpowerID, s.PowerDescription from Superpower s "
            + "inner join SuperhumanSuperpower ss on s.SuperpowerID = ss.SuperpowerID "
            + "where ss.SuperhumanID <> ?";
    
    private static final String SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERPOWER
            = "delete from SuperhumanSuperpower where SuperpowerID = ?";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperpower(Superpower superpower) {
        jdbcTemplate.update(SQL_INSERT_SUPERPOWER, superpower.getSuperpowerDescription());
        int superpowerId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", 
                Integer.class);
        superpower.setSuperpowerId(superpowerId);;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperpower(int superpowerId) {
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERPOWER, superpowerId);
        jdbcTemplate.update(SQL_DELETE_SUPERPOWER, superpowerId);
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        jdbcTemplate.update(SQL_UPDATE_SUPERPOWER, 
                superpower.getSuperpowerDescription(), 
                superpower.getSuperpowerId());
    }

    @Override
    public Superpower getSuperpowerById(int superpowerId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_SUPERPOWER, 
                    new SuperpowerMapper(), superpowerId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_SUPERPOWERS, new SuperpowerMapper());
    }
    
    @Override 
    public List<Superpower> getSuperpowersBySuperhumanId(int superhumanId) {
        return jdbcTemplate.query(SQL_SELECT_SUPERPOWERS_BY_SUPERHUMAN, 
                new SuperpowerMapper(), superhumanId);
    }
    
    private static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int i) throws SQLException {
            Superpower sp = new Superpower();
            sp.setSuperpowerDescription(rs.getString("PowerDescription"));
            sp.setSuperpowerId(rs.getInt("SuperpowerID"));
            return sp;
        }
    }
}
