/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Superhuman;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface SuperhumanDao {
    
    public void addSuperhuman(Superhuman superhuman);
    
    public void deleteSuperhuman(int superhumanId);
    
    public void updateSuperhuman(Superhuman superhuman);
    
    public Superhuman getSuperhumanById(int id);
    
    public List<Superhuman> getAllSuperhumans();
    
    public List<Superhuman> getSuperhumansByLocationId(long locationId);
    
    public List<Superhuman> getSuperhumansByOrganization(int orgId);

    public List<Integer> getSuperhumanIdsUsingPower(int superpowerId);

    public List<Superhuman> getSuperhumansBySightingId(long sightingId);
}
