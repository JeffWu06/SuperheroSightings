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
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface SuperHeroSightingsDao {
    // Superhuman methods.
    public void addSuperhuman(Superhuman superhuman);
    
    public void deleteSuperhuman(int superhumanId);
    
    public void updateSuperhuman(Superhuman superhuman);
    
    public Superhuman getSuperhumanById(int id);
    
    public List<Superhuman> getAllSuperhumans();
    
    public List<Superhuman> getSuperhumansByLocationId(long locationId);
    
    public List<Superhuman> getSuperhumansByOrganization(int orgId);
    
    // Superpower methods.
    public void addSuperpower(Superpower superpower);
    
    public void deleteSuperpower(int superpowerId);
    
    public void updateSuperpower(Superpower superpower);
    
    public Superpower getSuperpowerById(int superpowerId);
    
    public List<Superpower> getAllSuperpowers();
    
    // Location methods.
    public void addLocation(Location location);
    
    public void deleteLocation(long locationId);
    
    public void updateLocation(Location location);
    
    public Location getLocationById(long locationId);
    
    public List<Location> getAllLocations();
    
    public List<Location> getLocationsBySuperhumanId(int superhumanId);
    
    // Organization methods.
    public void addOrganization(Organization organization);
    
    public void deleteOrganization(int organizationId);
    
    public void updateOrganization(Organization organization);
    
    public Organization getOrganizationById(int orgId);
    
    public List<Organization> getAllOrganizations();
    
    public List<Organization> getOrganizationsBySuperhumanId(int superhumanId);
    
    // Sighting methods.
    public void addSighting(Sighting sighting);
    
    public void deleteSighting(long sightingId);
    
    public void updateSighting(Sighting sighting);
    
    public Sighting getSightingById(long sightingId);
    
    public List<Sighting> getAllSightings();
    
    public List<Sighting> getSightingsByDate(LocalDate date);
    
    public List<Sighting> getLastTenSightings();
}
