/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Superpower;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jeff
 */
public class SuperpowerDaoStubImpl implements SuperpowerDao {
    
    private Map<Integer, Superpower> superpowers = new HashMap<>();
    private int idCounter = 1;

    @Override
    public void addSuperpower(Superpower superpower) {
        superpower.setSuperpowerId(idCounter);
        idCounter++;
        superpowers.put(superpower.getSuperpowerId(), superpower);
    }

    @Override
    public void deleteSuperpower(int superpowerId) {
        superpowers.remove(superpowerId);
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        superpowers.put(superpower.getSuperpowerId(), superpower);
    }

    @Override
    public Superpower getSuperpowerById(int superpowerId) {
        return superpowers.get(superpowerId);
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        Collection<Superpower> allPowers = superpowers.values();
        return new ArrayList(allPowers);
    }

    @Override
    public List<Superpower> getSuperpowersBySuperhumanId(int superhumanId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
