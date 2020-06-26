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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Jeff
 */
public class SuperHeroSightingsServiceImpl implements SuperHeroSightingsService {
    
    SightingService sightingService;
    SuperhumanService superhumanService;
    SuperpowerService superpowerService;
    LocationService locationService;
    OrganizationService organizationService;

    public SuperHeroSightingsServiceImpl(SightingService sightingService, 
            SuperhumanService superhumanService, 
            SuperpowerService superpowerService, 
            LocationService locationService, 
            OrganizationService organizationService) {
        this.sightingService = sightingService;
        this.superhumanService = superhumanService;
        this.superpowerService = superpowerService;
        this.locationService = locationService;
        this.organizationService = organizationService;
    }

    @Override
    public List<Superhuman> getAllSuperhumans() {
        List<Superhuman> superhumans = superhumanService.getAllSuperhumans();
        return associateSuperhumansWithSuperpowersAndOrganizations(superhumans);
    }

    @Override
    public void addSuperhuman(Superhuman superhuman) 
            throws SuperhumanInvalidDataException {
        superhumanService.addSuperhuman(superhuman);
    }

    @Override
    public Superhuman getSuperhumanById(int superhumanId) {
        Superhuman superhuman = superhumanService.getSuperhumanById(superhumanId);
        superhuman.setSuperpowers(superpowerService.getSuperpowersBySuperhumanId(superhumanId));
        List<Organization> orgs = organizationService.getOrganizationsBySuperhumanId(superhumanId);
        superhuman.setOrganizations(associateLocationWithOrganizations(orgs));
        return superhuman;
    }

    @Override
    public void updateSuperhuman(Superhuman superhuman) 
            throws SuperhumanInvalidDataException {
        superhumanService.updateSuperhuman(superhuman);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSuperhuman(int superhumanId) throws SuperhumanInUseException {
        List<Sighting> sightings = sightingService.getSightingsBySuperhuman(superhumanId);
        if (sightings.size() > 0) {
            throw new SuperhumanInUseException("Superhuman is referenced by "
                    + "current sighting entries. Please update or delete the"
                    + "sightings first before deleting this superhuman.");
        } else {
            superhumanService.deleteSuperhuman(superhumanId);
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        return superpowerService.getAllSuperpowers();
    }

    @Override
    public List<Superpower> getSuperpowersNotPossessedBySuperhuman(Superhuman superhuman) {
        return superpowerService.getSuperpowersNotPossessedBySuperhumanId(superhuman);
    }

    @Override
    public void addSuperpower(Superpower superpower) 
            throws SuperpowerInvalidDataException {
        superpowerService.addSuperpower(superpower);
    }

    @Override
    public void updateSuperpower(Superpower superpower) 
            throws SuperpowerInvalidDataException {
        superpowerService.updateSuperpower(superpower);
    }

    @Override
    public Superpower getSuperpowerById(int id) {
        return superpowerService.getSuperpowerById(id);
    }

    @Override
    public void deleteSuperpower(int superpowerId) throws SuperpowerInUseException {
        List<Integer> heroIdsWithPower = superhumanService.SuperhumanIdsUsingPower(superpowerId);
        if (heroIdsWithPower.size() > 0) {
            throw new SuperpowerInUseException("Superpower is in use. Please "
                    + "update superhuman entries to deselect the superpower in "
                    + "before deleting");
        } else {
        superpowerService.deleteSuperpower(superpowerId);
        }
    }
    
    @Override
    public void addLocation(Location location) throws LocationInvalidDataException {
        locationService.addLocation(location);
    }
    
    @Override
    public Location getLocationById(long locationId) {
        return locationService.getLocationById(locationId);
    }
    
    @Override
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }
    
    @Override
    public void updateLocation(Location location) throws LocationInvalidDataException {
        locationService.updateLocation(location);
    }
    
    @Override 
    public void deleteLocation(long locationId) throws LocationInUseException {
        List<Organization> orgsAtLocation = organizationService.getOrganizationsAtLocation(locationId);
        List<Sighting> sightingsAtLocation = sightingService.getSightingsAtLocation(locationId);
        if (orgsAtLocation.size() > 0 || sightingsAtLocation.size() > 0) {
            throw new LocationInUseException("Location is referenced by "
                    + "existing organizations. Please update the organizations "
                    + "to remove this organization first.");
        } else {
            locationService.deleteLocation(locationId);
        }
    }

    @Override
    public void addOrganization(Organization organization) throws OrganizationInvalidDataException {
        organizationService.addOrganization(organization);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> orgs = organizationService.getAllOrganizations();
        return associateLocationWithOrganizations(orgs);
    }

    @Override
    public Organization getOrganizationById(int id) {
        Organization org = organizationService.getOrganizationById(id);
        org.setLocation(findLocationForOrganization(org));
        return org;
    }

    @Override
    public void updateOrganization(Organization organizationToEdit) throws OrganizationInvalidDataException {
        organizationService.updateOrganization(organizationToEdit);
    }

    @Override
    public void deleteOrganization(int organizationId) throws OrganizationInUseException {
        List<Superhuman> heroesInOrg = superhumanService.getSuperhumansByOrganization(organizationId);
        if (heroesInOrg.size() > 0) {
            throw new OrganizationInUseException("Organization is referenced by "
                    + "existing superhumans. Please update superhumans to remove"
                    + " this organization first.");
        } else {
            organizationService.deleteOrganization(organizationId);
        }
    }
    
    @Override
    public List<Organization> getNonmemberOrganizationsBySuperhuman(Superhuman superhuman) {
        List<Organization> orgs = organizationService.getUnbelongedOrganizationsBySuperhuman(superhuman);
        return associateLocationWithOrganizations(orgs);
    }
    
    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = sightingService.getAllSightings();
        return associateSightingsWithLocationAndSuperhumans(sightings);
//        return sightings;
    }

    @Override
    public List<Sighting> getRecentSightings() {
        List<Sighting> sightings = sightingService.getRecentSightings();
        return associateSightingsWithLocationAndSuperhumans(sightings);
    }

    @Override
    public Sighting getSightingById(long sightingId) {
        Sighting sighting = sightingService.getSightingById(sightingId);
        sighting.setHeroes(findSuperhumansForSighting(sighting));
        sighting.setLocation(findLocationForSighting(sighting));
        return sighting;
    }

    @Override
    public void addSighting(Sighting sighting) throws SightingInvalidDataException {
        sightingService.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sightingToEdit) throws SightingInvalidDataException {
        sightingService.updateSighting(sightingToEdit);
    }

    @Override
    public void deleteSighting(long sightingId) throws SightingInUseException {
        sightingService.deleteSighting(sightingId);
    }
    
    // Method to get Superpower and Organization lists for Superhuman.
    private List<Superpower> findSuperpowersForSuperhuman(Superhuman superhuman) {
        return superpowerService.getSuperpowersBySuperhumanId(superhuman.getSuperhumanId());
    }
    
    private List<Superhuman> associateSuperhumansWithSuperpowersAndOrganizations(
            List<Superhuman> superhumans) {
        for (Superhuman superhuman : superhumans) {
            superhuman.setSuperpowers(findSuperpowersForSuperhuman(superhuman));
            List<Organization> orgs = organizationService.getOrganizationsBySuperhumanId(superhuman.getSuperhumanId());
            superhuman.setOrganizations(associateLocationWithOrganizations(orgs));
        }
        return superhumans;
    }
    
    // Method to set Location to Organization.
    private Location findLocationForOrganization(Organization organization) {
        return locationService.getLocationByOrganizationId(
                organization.getOrganizationId());
    }
    
    private List<Organization> associateLocationWithOrganizations(List<Organization> orgs) {
        for (Organization currentOrg : orgs) {
            currentOrg.setLocation(findLocationForOrganization(currentOrg));
        }
        return orgs;
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
        return locationService.getLocationBySightingId(sighting.getSightingId());
    }

    // method(s) to set heroes list to Sighting.    
    private List<Superhuman> findSuperhumansForSighting(Sighting sighting) {
        List<Superhuman> superhumans = superhumanService.getSuperhumansBySightingId(
                sighting.getSightingId());   
        // For each Superhuman, get and associate list of Superpowers and Organizations.
        return associateSuperhumansWithSuperpowersAndOrganizations(superhumans);
    }
}
