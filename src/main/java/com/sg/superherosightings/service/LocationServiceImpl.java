/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.LocationDao;
import com.sg.superherosightings.model.Location;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Jeff
 */
public class LocationServiceImpl implements LocationService {
    
    private final int MAX_NAME = 40;
    private final int MAX_DESC = 90;
    private final int MAX_STREET = 52;
    private final int MAX_CITY = 46;
    private final int MAX_STATE_AND_COUNTRY = 2;
    private final int MAX_ZIP = 10; 
    private final BigDecimal MAX_LAT = new BigDecimal("90.000000");
    private final BigDecimal MAX_LON = new BigDecimal("180.000000");
    
    LocationDao dao;

    public LocationServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    @Override
    public void addLocation(Location location) throws LocationInvalidDataException {
        validateLocationData(location);
        dao.addLocation(location);
    }

    @Override
    public void deleteLocation(long locationId) {
        dao.deleteLocation(locationId);
    }

    @Override
    public void updateLocation(Location location) throws LocationInvalidDataException {
        validateLocationData(location);
        dao.updateLocation(location);
    }

    @Override
    public Location getLocationById(long locationId) {
        return dao.getLocationById(locationId);
    }

    @Override
    public List<Location> getAllLocations() {
        return dao.getAllLocations();
    }

    @Override
    public List<Location> getLocationsBySuperhumanId(int superhumanId) {
        return dao.getLocationsBySuperhumanId(superhumanId);
    }

    @Override
    public Location getLocationByOrganizationId(int organizationId) {
        return dao.getLocationByOrganizationId(organizationId);
    }

    @Override
    public Location getLocationBySightingId(long sightingId) {
        return dao.getLocationBySightingId(sightingId);
    }
    
    private void validateLocationData(Location loc) 
            throws LocationInvalidDataException {
        checkStringLength(loc.getLocationName(), MAX_NAME, "Location name");
        checkStringLength(loc.getLocationDescription(), MAX_DESC, "Description");
        checkStringLength(loc.getStreet(), MAX_STREET, "Street");
        checkStringLength(loc.getCity(), MAX_CITY, "City");
        checkStringLength(loc.getCountry(), MAX_STATE_AND_COUNTRY, "Country");
        checkStringLength(loc.getZip(), MAX_ZIP, "Postal code");
        checkCoordinates(loc.getLatitude(), loc.getLongitude(), MAX_LAT, MAX_LON);
    }
    
    private void checkStringLength(String string, int max, String fieldName) 
            throws LocationInvalidDataException {
        try {
            if (string.length() > max || string.length() == 0) {
                throw new LocationInvalidDataException(fieldName + " must be no "
                        + "more than " + max + " characters in length.");
            }
        } catch (NullPointerException e) {
            throw new LocationInvalidDataException(fieldName + " is required.");
        }
    }
    
    private void checkCoordinates(BigDecimal lat, BigDecimal lon, 
            BigDecimal maxLat, BigDecimal maxLong) 
            throws LocationInvalidDataException {
        try {
            if (lat.abs().compareTo(maxLat) == 1 || 
                    lon.abs().compareTo(maxLong) == 1) {
                throw new LocationInvalidDataException("Invalid coordinates.");
            }
        } catch (NullPointerException e) {
            throw new LocationInvalidDataException("Invalid coordinates.");
        }
    }
}
