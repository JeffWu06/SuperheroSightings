/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Location;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface LocationService {
    
    public void addLocation(Location location) throws LocationInvalidDataException;
    
    public void deleteLocation(long locationId);
    
    public void updateLocation(Location location) throws LocationInvalidDataException;
    
    public Location getLocationById(long locationId);
    
    public List<Location> getAllLocations();
    
    public List<Location> getLocationsBySuperhumanId(int superhumanId);
    
    public Location getLocationByOrganizationId(int organizationId);
    
    public Location getLocationBySightingId(long sightingId);
}
