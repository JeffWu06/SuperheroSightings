/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhuman;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SightingDaoJdbcImpl implements SightingDao {
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (SightingDate, LocationID) "
            + "values (?, ?)";
    
    private static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where SightingID = ?";
    
    private static final String SQL_UPDATE_SIGHTING
            = "update Sighting set SightingDate = ?, LocationID = ? "
            + "where SightingID = ?";
    
    private static final String SQL_SELECT_SIGHTING
            = "select * from Sighting where SightingID = ?";
    
    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "select * from Sighting"; 
    
    private static final String SQL_SELECT_MOST_RECENT_SIGHTINGS
            // Set to return most recent 10 sightings.
            = "select * from Sighting order by SightingDate DESC limit 0,10";
    
    private static final String SQL_SELECT_SIGHTINGS_BY_DATE
            = "select * from Sighting where SightingDate = ?";
    
    private static final String SQL_SELECT_SIGHTINGS_BY_LOCATION
            = "select s.SightingID, s.SightingDate, s.LocationID from Sighting s "
            + "inner join Location l on s.LocationID = l.LocationID "
            + "where l.LocationID = ?";
    
    private static final String SQL_SELECT_SIGHTINGS_BY_SUPERHUMAN
            = "select s.SightingID, s.SightingDate, s.LocationID from Sighting s "
            + "inner join SuperhumanSighting ss on s.SightingID = ss.SightingID "
            + "where ss.SuperhumanID = ?";
    
    private static final String SQL_INSERT_SUPERHUMAN_SIGHTING
            = "insert into SuperhumanSighting (SuperhumanID, SightingID) "
            + "values (?, ?)";
    
    private static final String SQL_DELETE_SUPERHUMANSIGHTING_SIGHTING
            = "delete from SuperhumanSighting where SightingID = ?";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_INSERT_SIGHTING, sighting.getSightingDate().toString(),
                sighting.getLocation().getLocationId());
        long sightingId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Long.class);
        sighting.setSightingId(sightingId);
        insertSightingToSuperhumanSighting(sighting);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSighting(long sightingId) {
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSIGHTING_SIGHTING, sightingId);
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSighting(Sighting sighting) {
        jdbcTemplate.update(SQL_UPDATE_SIGHTING, 
                sighting.getSightingDate().toString(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingId());
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSIGHTING_SIGHTING, 
                sighting.getSightingId());
        insertSightingToSuperhumanSighting(sighting);
    }

    @Override
    public Sighting getSightingById(long sightingId) {
        try {
            // Get sighting from table.
            Sighting sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING, 
                    new SightingMapper(), sightingId);
            return sighting;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
       return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public List<Sighting> getSightingsByLocation(long locationId) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_LOCATION, 
                new SightingMapper(), locationId);
    }
    
    @Override
    public List<Sighting> getRecentSightings() {
        return jdbcTemplate.query(SQL_SELECT_MOST_RECENT_SIGHTINGS, 
                new SightingMapper());
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_DATE, 
                new SightingMapper(), date.toString());
    }

    @Override
    public List<Sighting> getSightingsBySuperhuman(int superhumanId) {
        return jdbcTemplate.query(SQL_SELECT_SIGHTINGS_BY_SUPERHUMAN, 
                new SightingMapper(), superhumanId);
    }
    
    // Method to add SuperhumanSighting rows to SuperhumanSighting bridge by Sighting.
    private void insertSightingToSuperhumanSighting(Sighting sighting) {
        final long sightingId = sighting.getSightingId();
        final List<Superhuman> superhumans = sighting.getHeroes();
        
        for (Superhuman superhuman : superhumans) {
            jdbcTemplate.update(SQL_INSERT_SUPERHUMAN_SIGHTING, 
                    superhuman.getSuperhumanId(), sightingId);
        }
    }
    
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting st = new Sighting();
            st.setSightingDate(rs.getTimestamp("SightingDate").toLocalDateTime().toLocalDate());
            st.setSightingId(rs.getLong("SightingID"));
            return st;
        }
    }
}
