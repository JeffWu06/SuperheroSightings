/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhuman;
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
public class SuperhumanDaoJdbcImpl implements SuperhumanDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_SUPERHUMAN
            = "insert into Superhuman (AlterEgo, Description, Villain) "
            + "values (?, ?, ?)";
    
    private static final String SQL_DELETE_SUPERHUMAN
            = "delete from Superhuman where SuperhumanID = ?";
    
    private static final String SQL_UPDATE_SUPERHUMAN
            = "update superhuman set AlterEgo = ?, Description = ?, "
            + "Villain = ? where SuperhumanID = ?";
    
    private static final String SQL_SELECT_SUPERHUMAN
            = "select * from Superhuman where SuperhumanID = ?";
    
    private static final String SQL_SELECT_ALL_SUPERHUMANS
            = "select * from Superhuman";
    
    private static final String SQL_SELECT_SUPERHUMANS_BY_LOCATION
            = "select s.SuperhumanID, s.AlterEgo, s.Description, s.Villain "
            + "from Superhuman s "
            + "inner join SuperhumanSighting ss on s.SuperhumanID = ss.SuperhumanID "
            + "inner join Sighting t on ss.SightingID = t.SightingID "
            + "where t.LocationID = ?";
    
    private static final String SQL_SELECT_SUPERHUMANS_BY_ORGANIZATION
            = "select s.SuperhumanID, s.AlterEgo, s.Description, s.Villain "
            + "from Superhuman s "
            + "inner join SuperhumanOrganization so on s.SuperhumanID = so.SuperhumanID "
            + "where so.OrganizationID = ?";
    
    private static final String SQL_SELECT_SUPERHUMANS_BY_SIGHTING
            = "select s.SuperhumanID, s.AlterEgo, s.Description, s.Villain "
            + "from Superhuman s "
            + "inner join SuperhumanSighting ss on s.SuperhumanID = ss.SuperhumanID "
            + "where ss.SightingID = ?";
    
    private static final String SQL_INSERT_SUPERHUMAN_SUPERPOWER
            = "insert into SuperhumanSuperpower (SuperhumanID, SuperpowerID) "
            + "values (?, ?)";
    
    private static final String SQL_INSERT_SUPERHUMAN_ORGANIZATION
            = "insert into SuperhumanOrganization (SuperhumanID, OrganizationID) "
            + "values (?, ?)";
    
    private static final String SQL_DELETE_SUPERHUMANSIGHTING_SUPERHUMAN
            = "delete from SuperhumanSighting where SuperhumanID = ?";
    
    private static final String SQL_DELETE_SUPERHUMANORGANIZATION_SUPERHUMAN
            = "delete from SuperhumanOrganization where SuperhumanID = ?";
    
    private static final String SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERHUMAN
            = "delete from SuperhumanSuperpower where SuperhumanID = ?";
    
    private static final String SQL_SELECT_SIGHTINGS_BY_SUPERHUMAN
            = "select s.SightingID from Sighting s "
            + "inner join SuperhumanSighting ss on s.SightingID = ss.SightingID "
            + "where ss.SuperhumanID = ?";
    
    private static final String SQL_INSERT_SUPERHUMAN_SIGHTING
            = "insert into SuperhumanSighting (SuperhumanID, SightingID) "
            + "values (?, ?)";
    
    private static final String SQL_GET_SUPERHUMANS_WITH_POWER
            = "select SuperhumanID from SuperhumanSuperpower "
            + "where SuperpowerID = ?";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperhuman(Superhuman superhuman) {
        // Insert into Superhuman table first and get/set ID.
        jdbcTemplate.update(SQL_INSERT_SUPERHUMAN, superhuman.getAlterEgo(), 
                superhuman.getDescription(), superhuman.isVillain());
        int superhumanId = jdbcTemplate.queryForObject(
                "select LAST_INSERT_ID()", Integer.class);
        superhuman.setSuperhumanId(superhumanId);
        
        // Then, insert rows to SuperhumanSuperpower and SuperhumanOrganization bridges.
        insertToSuperhumanSuperpower(superhuman);
        insertToSuperhumanOrganization(superhuman);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperhuman(int superhumanId) {
        // Delete rows with superhumanId in SuperhumanSuperpower bridge.
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERHUMAN, superhumanId);
        
        // Delete rows with superhumanId in SuperhumanOrganization bridge.
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANORGANIZATION_SUPERHUMAN, superhumanId);
        
        // Delete rows with superhumanId in SuperhumanSighting bridge.
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSIGHTING_SUPERHUMAN, superhumanId);
        
        // Delete sightings.
        
        // Then, the Superhuman row can be deleted.
        jdbcTemplate.update(SQL_DELETE_SUPERHUMAN, superhumanId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperhuman(Superhuman superhuman) {
        // Update Superhuman table.
        jdbcTemplate.update(SQL_UPDATE_SUPERHUMAN, superhuman.getAlterEgo(), 
                superhuman.getDescription(), superhuman.isVillain(), 
                superhuman.getSuperhumanId());
        
        // Then, delete and re-add rows to bridges.
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERHUMAN, 
                superhuman.getSuperhumanId());
        insertToSuperhumanSuperpower(superhuman);
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANORGANIZATION_SUPERHUMAN, 
                superhuman.getSuperhumanId());
        insertToSuperhumanOrganization(superhuman);
    }

    @Override
    public Superhuman getSuperhumanById(int id) {
        try {
            Superhuman superhuman = jdbcTemplate.queryForObject(
                    SQL_SELECT_SUPERHUMAN, new SuperhumanMapper(), id);
            return superhuman;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Superhuman> getAllSuperhumans() {
        return jdbcTemplate.query(SQL_SELECT_ALL_SUPERHUMANS, 
                new SuperhumanMapper());
    }

    @Override
    public List<Superhuman> getSuperhumansByLocationId(long locationId) {
        return jdbcTemplate.query(SQL_SELECT_SUPERHUMANS_BY_LOCATION, 
                new SuperhumanMapper(), locationId);
    }

    @Override
    public List<Superhuman> getSuperhumansByOrganization(int orgId) {
        return jdbcTemplate.query(SQL_SELECT_SUPERHUMANS_BY_ORGANIZATION, 
                new SuperhumanMapper(), orgId);
    }
    
    @Override
    public List<Integer> getSuperhumanIdsUsingPower(int superpowerId) {
        return jdbcTemplate.queryForList(SQL_GET_SUPERHUMANS_WITH_POWER, 
                Integer.class, superpowerId);
    }

    @Override
    public List<Superhuman> getSuperhumansBySightingId(long sightingId) {
        return jdbcTemplate.query(SQL_SELECT_SUPERHUMANS_BY_SIGHTING, 
                new SuperhumanMapper(), sightingId);
    }
    
    // Method to add Superhuman/Superpower rows to SuperhumanSuperpower bridge by Superhuman.
    private void insertToSuperhumanSuperpower(Superhuman superhuman) {
        final int superhumanId = superhuman.getSuperhumanId();
        final List<Superpower> powers = superhuman.getSuperpowers();
        
        for (Superpower power : powers) {
            jdbcTemplate.update(SQL_INSERT_SUPERHUMAN_SUPERPOWER, superhumanId,
                    power.getSuperpowerId());
        }
    }
    
    // Method to add Superhuman/Organization rows to SuperhumanOrganization bridge by Superhuman.
    private void insertToSuperhumanOrganization(Superhuman superhuman) {
        final int superhumanId = superhuman.getSuperhumanId();
        final List<Organization> orgs = superhuman.getOrganizations();
        
        for (Organization org : orgs) {
            jdbcTemplate.update(SQL_INSERT_SUPERHUMAN_ORGANIZATION, superhumanId,
                    org.getOrganizationId());
        }
    }
    
    // Method to add Superhuman/Sighting rows to SuperhumanSighting bridge by Superhuman.
    private void insertSuperhumanToSuperhumanSighting(Superhuman superhuman) {
        final int superhumanId = superhuman.getSuperhumanId();
        final List<Long> sightingIds = findSightingsForSuperhuman(superhuman);
        
        for (Long sightingId : sightingIds) {
            jdbcTemplate.update(SQL_INSERT_SUPERHUMAN_SIGHTING, superhumanId,
                    sightingId);
        }
    }
    
    // Method to get Sighting list by Superhuman. Change type to List<Long>.
    private List<Long> findSightingsForSuperhuman(Superhuman superhuman) {
        return jdbcTemplate.queryForList(SQL_SELECT_SIGHTINGS_BY_SUPERHUMAN, 
                Long.class, superhuman.getSuperhumanId());
    }
    
    private static final class SuperhumanMapper implements RowMapper<Superhuman> {

        @Override
        public Superhuman mapRow(ResultSet rs, int i) throws SQLException {
            Superhuman sh = new Superhuman();
            sh.setAlterEgo(rs.getString("AlterEgo"));
            sh.setDescription(rs.getString("Description"));
            sh.setVillain(rs.getBoolean("Villain"));
            sh.setSuperhumanId(rs.getInt("SuperhumanID"));
            return sh;            
        }        
    }
}
