/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface SuperHeroSightingsService {

    public List<Sighting> getRecentSightings();

    public List<Superhuman> getAllSuperhumans();

    public List<Superpower> getAllSuperpowers();

    public List<Organization> getAllOrganizations();

    public Superpower getSuperpowerById(int id);

    public Organization getOrganizationById(int id);

    public void addSuperhuman(Superhuman superhuman) 
            throws SuperhumanInvalidDataException;

    public Superhuman getSuperhumanById(int superhumanId);

    public void updateSuperhuman(Superhuman superhuman)
            throws SuperhumanInvalidDataException;

    public void deleteSuperhuman(int superhumanId) throws SuperhumanInUseException;

    public void addSuperpower(Superpower superpower) 
            throws SuperpowerInvalidDataException;

    public void updateSuperpower(Superpower superpowerToEdit) 
            throws SuperpowerInvalidDataException;

    public void deleteSuperpower(int superpowerId) throws SuperpowerInUseException;

    public List<Location> getAllLocations();

    public List<Sighting> getAllSightings();
    
    public List<Superpower> getSuperpowersNotPossessedBySuperhuman(Superhuman superhuman);
    
    public List<Organization> getNonmemberOrganizationsBySuperhuman(Superhuman superhuman);

    public Location getLocationById(long locationId);

    public void updateLocation(Location locationToEdit)throws LocationInvalidDataException;

    public void addLocation(Location location) throws LocationInvalidDataException;

    public void deleteLocation(long locationId) throws LocationInUseException;

    public void addOrganization(Organization organization) throws OrganizationInvalidDataException;

    public void updateOrganization(Organization organizationToEdit) throws OrganizationInvalidDataException;

    public void deleteOrganization(int organizationId) throws OrganizationInUseException;

    public Sighting getSightingById(long sightingId);

    public void addSighting(Sighting sighting) throws SightingInvalidDataException;

    public void updateSighting(Sighting sightingToEdit) throws SightingInvalidDataException;

    public void deleteSighting(long sightingId) throws SightingInUseException;
    
}
