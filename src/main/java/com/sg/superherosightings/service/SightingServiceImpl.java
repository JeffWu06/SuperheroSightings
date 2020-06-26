/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.SightingDao;
import com.sg.superherosightings.model.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Jeff
 */
public class SightingServiceImpl implements SightingService {
    
    SightingDao dao;

    public SightingServiceImpl(SightingDao dao) {
        this.dao = dao;
    }

    @Override
    public void addSighting(Sighting sighting) throws SightingInvalidDataException {
        validateSightingData(sighting);
        dao.addSighting(sighting);
    }

    @Override
    public void deleteSighting(long sightingId) {
        dao.deleteSighting(sightingId);
    }

    @Override
    public void updateSighting(Sighting sighting) throws SightingInvalidDataException {
        validateSightingData(sighting);
        dao.updateSighting(sighting);
    }

    @Override
    public Sighting getSightingById(long sightingId) {
        return dao.getSightingById(sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return dao.getAllSightings();
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        return dao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getRecentSightings() {
        return dao.getRecentSightings();
    }
    
    @Override
    public List<Sighting> getSightingsAtLocation(long locationId) {
        return dao.getSightingsByLocation(locationId);
    }

    @Override
    public List<Sighting> getSightingsBySuperhuman(int superhumanId) {
        return dao.getSightingsBySuperhuman(superhumanId);
    }

    private void validateSightingData(Sighting sighting) 
            throws SightingInvalidDataException {
        try {
            if (sighting.getHeroes() == null || sighting.getLocation() == null || 
                    sighting.getSightingDate() == null) {
                throw new SightingInvalidDataException("List of heroes spotted and "
                        + "a valid location and date must be provided.");
            }
        } catch (NullPointerException e) {
            throw new SightingInvalidDataException("List of heroes spotted and "
                    + "a valid location and date must be provided.");
        }
    }
}
