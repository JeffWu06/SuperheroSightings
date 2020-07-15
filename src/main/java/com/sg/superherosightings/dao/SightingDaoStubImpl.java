/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Sighting;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jeff
 */
public class SightingDaoStubImpl implements SightingDao {
    
    private Map<Long, Sighting> sightings = new HashMap<>();
    private long idCounter = 1;

    @Override
    public void addSighting(Sighting sighting) {
        sighting.setSightingId(idCounter);
        idCounter++;
        sightings.put(sighting.getSightingId(), sighting);
    }

    @Override
    public void deleteSighting(long sightingId) {
        sightings.remove(sightingId);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightings.put(sighting.getSightingId(), sighting);
    }

    @Override
    public Sighting getSightingById(long sightingId) {
        return sightings.get(sightingId);
    }

    @Override
    public List<Sighting> getAllSightings() {
        Collection<Sighting> allSightings = sightings.values();
        return new ArrayList(allSightings);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        List<Sighting> allSightings = getAllSightings();
        List<Sighting> sightingsWithParamDate = new ArrayList<>();
        for (Sighting sighting : allSightings) {
            if (sighting.getSightingDate().isEqual(date)) {
                sightingsWithParamDate.add(sighting);
            }
        }
        return sightingsWithParamDate;
    }

    @Override
    public List<Sighting> getRecentSightings() {
        List<Sighting> recentSightings = new ArrayList<>();
        List<Sighting> allSightings = getAllSightings();
        return recentSightings;
    }

    @Override
    public List<Sighting> getSightingsByLocation(long locationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sighting> getSightingsBySuperhuman(int superhumanId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
