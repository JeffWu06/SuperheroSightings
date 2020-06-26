/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Location;
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
public class LocationDaoJdbcImpl implements LocationDao {
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_LOCATION
            = "insert into Location (LocationName, LocationDescription, Street, "
            + "City, State, Zip, Country, Latitude, Longitude) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_DELETE_LOCATION
            = "delete from Location where LocationID = ?";
    
    private static final String SQL_UPDATE_LOCATION
            = "update Location set LocationName = ?, LocationDescription = ?, "
            + "Street = ?, City = ?, State = ?, Zip = ?, Country = ?, "
            + "Latitude = ?, Longitude = ? where LocationID = ?";
    
    private static final String SQL_SELECT_LOCATION
            = "select * from Location where LocationID = ?";
    
    private static final String SQL_SELECT_LOCATION_BY_SIGHTING
            = "select * from Location l "
            + "inner join Sighting s on l.LocationID = s.LocationID "
            + "where SightingID = ?";
    
    private static final String SQL_SELECT_ALL_LOCATIONS
            = "select * from Location";
    
    private static final String SQL_SELECT_LOCATIONS_BY_SUPERHUMAN
            = "select l.LocationName, s.SightingDate from Location l "
            + "inner join Sighting s on l.LocationID = s.LocationID "
            + "inner join SuperhumanSighting ss on s.SightingID = ss.SightingID "
            + "inner join Superhuman h on ss.SuperhumanID = h.SuperhumanID "
            + "where h.SuperhumanID = ?";
    
    private static final String SQL_SELECT_LOCATION_BY_ORGANIZATION
            = "select l.LocationID, l.LocationName, l.LocationDescription, "
            + "l.Street, l.City, l.State, l.Zip, l.Country, l.Latitude, "
            + "l.Longitude from Location l "
            + "inner join Organization o on l.LocationID = o.LocationID "
            + "where o.OrganizationID = ?";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION, location.getLocationName(), 
                location.getLocationDescription(), location.getStreet(), 
                location.getCity(), location.getState(), location.getZip(),
                location.getCountry(), location.getLatitude(), location.getLongitude());
        long locationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", 
                Long.class);
        location.setLocationId(locationId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteLocation(long locationId) {
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
    }

    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION, location.getLocationName(), 
                location.getLocationDescription(), location.getStreet(), 
                location.getCity(), location.getState(), location.getZip(),
                location.getCountry(), location.getLatitude(), 
                location.getLongitude(), location.getLocationId());
    }

    @Override
    public Location getLocationById(long locationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION, 
                    new LocationMapper(), locationId);            
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public List<Location> getLocationsBySuperhumanId(int superhumanId) {
        return jdbcTemplate.query(SQL_SELECT_LOCATIONS_BY_SUPERHUMAN, 
                new LocationMapper(), superhumanId);
    }
    
    @Override
    public Location getLocationByOrganizationId(int organizationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_ORGANIZATION, 
                new LocationMapper(), organizationId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public Location getLocationBySightingId(long sightingId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING, 
                new LocationMapper(), sightingId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setLocationName(rs.getString("LocationName"));
            loc.setLocationDescription(rs.getString("LocationDescription"));
            loc.setStreet(rs.getString("Street"));
            loc.setCity(rs.getString("City"));
            loc.setState(rs.getString("State"));
            loc.setZip(rs.getString("Zip"));
            loc.setCountry(rs.getString("Country"));
            loc.setLatitude(rs.getBigDecimal("Latitude"));
            loc.setLongitude(rs.getBigDecimal("Longitude"));
            loc.setLocationId(rs.getLong("LocationID"));
            return loc;
        }
    }
}
