/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Superpower;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface SuperpowerDao {
    
    public void addSuperpower(Superpower superpower);
    
    public void deleteSuperpower(int superpowerId);
    
    public void updateSuperpower(Superpower superpower);
    
    public Superpower getSuperpowerById(int superpowerId);
    
    public List<Superpower> getAllSuperpowers();
    
    public List<Superpower> getSuperpowersBySuperhumanId(int superhumanId);
}
