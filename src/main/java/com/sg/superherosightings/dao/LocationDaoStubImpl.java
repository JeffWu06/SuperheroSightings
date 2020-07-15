/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Location;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jeff
 */
public class LocationDaoStubImpl implements LocationDao {
    
    private Map<Long, Location> locations = new HashMap<>();
    private long idCounter = 1;

    @Override
    public void addLocation(Location location) {
        location.setLocationId(idCounter);
        idCounter++;
        locations.put(location.getLocationId(), location);
    }

    @Override
    public void deleteLocation(long locationId) {
        locations.remove(locationId);
    }

    @Override
    public void updateLocation(Location location) {
        locations.put(location.getLocationId(), location);
    }

    @Override
    public Location getLocationById(long locationId) {
        return locations.get(locationId);
    }

    @Override
    public List<Location> getAllLocations() {
        Collection<Location> allLocations = locations.values();
        return new ArrayList(allLocations);
    }

    @Override
    public List<Location> getLocationsBySuperhumanId(int superhumanId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location getLocationByOrganizationId(int organizationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Location getLocationBySightingId(long sightingId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
