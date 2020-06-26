/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface SuperpowerService {
    
    public void addSuperpower(Superpower superpower) throws SuperpowerInvalidDataException;

    public void updateSuperpower(Superpower superpowerToEdit) throws SuperpowerInvalidDataException;

    public void deleteSuperpower(int superpowerId);
    
    public Superpower getSuperpowerById(int id);
    
    public List<Superpower> getAllSuperpowers();
    
    public List<Superpower> getSuperpowersBySuperhumanId(int superhumanId);
    
    public List<Superpower> getSuperpowersNotPossessedBySuperhumanId(Superhuman superhuman);
}
