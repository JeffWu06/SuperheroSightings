/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
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
public class SuperHeroSightingsJdbcTemplateImpl implements SuperHeroSightingsDao {
    
// JdbcTemplate instance and setter.
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
//==============================================================================
// Prepared Statements
    // SUPERPOWER
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
    
    // SUPERHUMANSUPERPOWER    
    private static final String SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERPOWER
            = "delete from SuperhumanSuperpower where SuperpowerID = ?";
    
    private static final String SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERHUMAN
            = "delete from SuperhumanSuperpower where SuperhumanID = ?";
    
    private static final String SQL_INSERT_SUPERHUMAN_SUPERPOWER
            = "insert into SuperhumanSuperpower (SuperhumanID, SuperpowerID) "
            + "values (?, ?)";
    
    // SUPERHUMAN
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
    
    // LOCATION
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
            
    // ORGANIZATION
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (OrganizationName, OrgDescription, "
            + "Phone, Email, LocationID, Villain) "
            + "values (?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationID = ?";
    
    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set OrganizationName = ?, OrgDescription = ?, "
            + "Phone = ?, Email = ?, LocationID = ?, Villain = ? "
            + "where OrganizationID = ?";
    
    private static final String SQL_SELECT_ORGANIZATION
            = "select * from Organization where OrganizationID = ?";
    
    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from Organization";
    
    private static final String SQL_SELECT_ORGANIZATIONS_BY_SUPERHUMAN
            = "select o.OrganizationID, o.OrganizationName, o.OrgDescription, "
            + "o.Phone, o.Email, o.LocationID, o.Villain from Organization o "
            + "inner join SuperhumanOrganization so on o.OrganizationID = so.OrganizationID "
            + "where SuperhumanID = ?";
    
    private static final String SQL_SELECT_ORGANIZATIONS_BY_LOCATION
            = "select OrganizationID, OrganizationName, OrgDescription, Phone, "
            + "Email, Villain from Organization where LocationID = ?";
    
    // SUPERHUMANORGANIZATION
    private static final String SQL_DELETE_SUPERHUMANORGANIZATION_ORGANIZATION
            = "delete from SuperhumanOrganization where OrganizationID = ?";
    
    private static final String SQL_DELETE_SUPERHUMANORGANIZATION_SUPERHUMAN
            = "delete from SuperhumanOrganization where SuperhumanID = ?";
    
    private static final String SQL_INSERT_SUPERHUMAN_ORGANIZATION
            = "insert into SuperhumanOrganization (SuperhumanID, OrganizationID) "
            + "values (?, ?)";
    
    // SIGHTING
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
    
    // SUPERHUMANSIGHTING
    private static final String SQL_INSERT_SUPERHUMAN_SIGHTING
            = "insert into SuperhumanSighting (SuperhumanID, SightingID) "
            + "values (?, ?)";
    
    private static final String SQL_DELETE_SUPERHUMANSIGHTING_SIGHTING
            = "delete from SuperhumanSighting where SightingID = ?";
    
    private static final String SQL_DELETE_SUPERHUMANSIGHTING_SUPERHUMAN
            = "delete from SuperhumanSighting where SuperhumanID = ?";

//==============================================================================
    // SUPERHUMAN methods
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
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSUPERPOWER_SUPERHUMAN, superhuman.getSuperhumanId());
        insertToSuperhumanSuperpower(superhuman);
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANORGANIZATION_SUPERHUMAN, superhuman.getSuperhumanId());
        insertToSuperhumanOrganization(superhuman);
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANSIGHTING_SUPERHUMAN, superhuman.getSuperhumanId());
        insertSuperhumanToSuperhumanSighting(superhuman);
    }

    @Override
    public Superhuman getSuperhumanById(int id) {
        try {
        // SELECT row from Superhuman table.
            Superhuman superhuman = jdbcTemplate.queryForObject(
                    SQL_SELECT_SUPERHUMAN, new SuperhumanMapper(), id);        
        // Populate Lists of Superpower, Organization objects
        // since they are not explicitly in the Superhuman table.
            superhuman.setSuperpowers(findSuperpowersForSuperhuman(superhuman));
            superhuman.setOrganizations(getOrganizationsBySuperhumanId(
                    superhuman.getSuperhumanId()));
            return superhuman;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Superhuman> getAllSuperhumans() {
        List<Superhuman> superhumans = jdbcTemplate.query(
                SQL_SELECT_ALL_SUPERHUMANS, new SuperhumanMapper());
        return associateSuperhumansWithSuperpowersAndOrganizations(superhumans);
    }

    @Override
    public List<Superhuman> getSuperhumansByLocationId(long locationId) {
        List<Superhuman> superhumans = jdbcTemplate.query(
                SQL_SELECT_SUPERHUMANS_BY_LOCATION, new SuperhumanMapper(), 
                locationId);
        return associateSuperhumansWithSuperpowersAndOrganizations(superhumans);
    }

    @Override
    public List<Superhuman> getSuperhumansByOrganization(int orgId) {
        List<Superhuman> superhumans = jdbcTemplate.query(
                SQL_SELECT_SUPERHUMANS_BY_ORGANIZATION, new SuperhumanMapper(), 
                orgId);
        return associateSuperhumansWithSuperpowersAndOrganizations(superhumans);
    }
    
//==============================================================================
    // SUPERPOWER methods
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
        // Remove existing rows on SuperHumanSuperPower bridge table, and re-add.
        // ...
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
    
//==============================================================================
    // LOCATION methods
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
        /* Delete all Sighting rows at locationId, deleting all 
        SuperhumanSighting rows with that sightingId first. */
        List<Sighting> sightings = jdbcTemplate.query(
                SQL_SELECT_SIGHTINGS_BY_LOCATION, new SightingMapper(), locationId);
        for (Sighting sighting : sightings) {
            jdbcTemplate.update(SQL_DELETE_SUPERHUMANSIGHTING_SIGHTING, sighting.getSightingId());
            jdbcTemplate.update(SQL_DELETE_SIGHTING, sighting.getSightingId());
        }
        
        /* Delete all Organization rows with locationId, deleting all 
        SuperhumanOrganization rows with that organizationId first. */
        List<Organization> organizations = jdbcTemplate.query(
                SQL_SELECT_ORGANIZATIONS_BY_LOCATION, new OrganizationMapper(), locationId);
        for (Organization org : organizations) {
            jdbcTemplate.update(SQL_DELETE_SUPERHUMANORGANIZATION_ORGANIZATION, org.getOrganizationId());
            jdbcTemplate.update(SQL_DELETE_ORGANIZATION, org.getOrganizationId());
        }
        
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

//==============================================================================
    // ORGANIZATION methods
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION, 
                organization.getOrganizationName(),
                organization.getOrgDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getLocation().getLocationId(),
                organization.isVillain());
        organization.setOrganizationId(jdbcTemplate.queryForObject(
                "select LAST_INSERT_ID()", Integer.class));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteOrganization(int organizationId) {
        /* Delete all instances of organizationID on SuperhumanOrganization 
        bridge table, then delete from the Organization table. */
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANORGANIZATION_ORGANIZATION, 
                organizationId);
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    @Override
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION, 
                organization.getOrganizationName(),
                organization.getOrgDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getLocation().getLocationId(),
                organization.isVillain(), organization.getOrganizationId());
        // Remove and re-add SuperhumanOrganization rows.
        // ...
    }

    @Override
    public Organization getOrganizationById(int orgId) {
        try {
            Organization org = jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION, 
                new OrganizationMapper(), orgId);
            org.setLocation(findLocationForOrganization(org));
            return org;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> orgList = jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, 
                new OrganizationMapper());
        return associateLocationWithOrganization(orgList);
    }

    @Override
    public List<Organization> getOrganizationsBySuperhumanId(int superhumanId) {
        List<Organization> orgList = jdbcTemplate.query(
                SQL_SELECT_ORGANIZATIONS_BY_SUPERHUMAN, 
                new OrganizationMapper(), superhumanId);
        return associateLocationWithOrganization(orgList);
    }

//==============================================================================
    // SIGHTING methods
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
        // Get location by locationId and associate Location object.
            sighting.setLocation(findLocationForSighting(sighting));
        // Get List of Superhumans from SuperhumanSighting bridge table by sightingId, and associate.
            sighting.setHeroes(findSuperhumansForSighting(sighting));
            return sighting;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, 
                new SightingMapper());
        return associateSightingsWithLocationAndSuperhumans(sightings);
    }
    
    @Override
    public List<Sighting> getLastTenSightings() {
        List<Sighting> sightings = jdbcTemplate.query(SQL_SELECT_MOST_RECENT_SIGHTINGS, 
                new SightingMapper());
        return associateSightingsWithLocationAndSuperhumans(sightings);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
//        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());
        List<Sighting> sightings = jdbcTemplate.query(
                SQL_SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), date.toString());
        return associateSightingsWithLocationAndSuperhumans(sightings);
    }

//==============================================================================   
// HELPER METHODS
    // methods to set Location to Organization.
    private List<Organization> associateLocationWithOrganization(List<Organization> orgs) {
        for (Organization currentOrg : orgs) {
            currentOrg.setLocation(findLocationForOrganization(currentOrg));
        }
        return orgs;
    }

    private Location findLocationForOrganization(Organization organization) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_ORGANIZATION, 
                new LocationMapper(), organization.getOrganizationId());
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
        final List<Sighting> sightings = findSightingsForSuperhuman(superhuman);
        
        for (Sighting sighting : sightings) {
            jdbcTemplate.update(SQL_INSERT_SUPERHUMAN_SIGHTING, superhumanId,
                    sighting.getSightingId());
        }
    }
    
    // Methods to set Superpower list to Superhuman.
    private List<Superpower> findSuperpowersForSuperhuman(Superhuman superhuman) {
        return jdbcTemplate.query(SQL_SELECT_SUPERPOWERS_BY_SUPERHUMAN, 
                new SuperpowerMapper(), superhuman.getSuperhumanId());
    }
    
    private List<Superhuman> associateSuperhumansWithSuperpowersAndOrganizations(
            List<Superhuman> superhumans) {
        for (Superhuman superhuman : superhumans) {
            superhuman.setSuperpowers(findSuperpowersForSuperhuman(superhuman));
            superhuman.setOrganizations(getOrganizationsBySuperhumanId(
                    superhuman.getSuperhumanId()));
        }
        return superhumans;
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
    
    private List<Sighting> associateSightingsWithLocationAndSuperhumans(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(findLocationForSighting(sighting));
            sighting.setHeroes(findSuperhumansForSighting(sighting));
        }
        return sightings;
    }
    // Method(s) to associate Location to Sighting.
    private Location findLocationForSighting(Sighting sighting) {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING, 
                new LocationMapper(), sighting.getSightingId());
    }

    // method(s) to set heroes list to Sighting.    
    private List<Superhuman> findSuperhumansForSighting(Sighting sighting) {
        List<Superhuman> superhumans = jdbcTemplate.query(
                SQL_SELECT_SUPERHUMANS_BY_SIGHTING, new SuperhumanMapper(), 
                sighting.getSightingId());        
        // For each Superhuman, get and associate list of Superpowers and Organizations.
        return associateSuperhumansWithSuperpowersAndOrganizations(superhumans);
    }

    // Method to get Sighting list by Superhuman.
    private List<Sighting> findSightingsForSuperhuman(Superhuman superhuman) {
        List<Sighting> sightings = jdbcTemplate.query(
                SQL_SELECT_SIGHTINGS_BY_SUPERHUMAN, new SightingMapper(), 
                superhuman.getSuperhumanId());
        return associateSightingsWithLocationAndSuperhumans(sightings);
    }
 
//==============================================================================
// Mapper classes
    private static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int i) throws SQLException {
            Superpower sp = new Superpower();
            sp.setSuperpowerDescription(rs.getString("PowerDescription"));
            sp.setSuperpowerId(rs.getInt("SuperpowerID"));
            return sp;
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
    
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting st = new Sighting();
            st.setSightingDate(rs.getTimestamp("SightingDate").toLocalDateTime().toLocalDate());
            st.setSightingId(rs.getLong("SightingID"));
            return st;
        }
    }
    
    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationName(rs.getString("OrganizationName"));
            org.setOrgDescription(rs.getString("OrgDescription"));
            org.setPhone(rs.getString("Phone"));
            org.setEmail(rs.getString("Email"));
            org.setVillain(rs.getBoolean("Villain"));
            org.setOrganizationId(rs.getInt("OrganizationID"));
            return org;
        }
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
