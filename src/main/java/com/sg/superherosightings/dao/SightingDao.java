/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface SightingDao {
    
    public void addSighting(Sighting sighting);
    
    public void deleteSighting(long sightingId);
    
    public void updateSighting(Sighting sighting);
    
    public Sighting getSightingById(long sightingId);
    
    public List<Sighting> getAllSightings();
    
    public List<Sighting> getSightingsByDate(LocalDate date);
    
    public List<Sighting> getRecentSightings();    

    public List<Sighting> getSightingsByLocation(long locationId);

    public List<Sighting> getSightingsBySuperhuman(int superhumanId);
}
