/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Superhuman;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jeff
 */
public class SuperhumanDaoStubImpl implements SuperhumanDao {
    
    private Map<Integer, Superhuman> superhumans = new HashMap<>();
    private int idCounter = 1;

    @Override
    public void addSuperhuman(Superhuman superhuman) {
        superhuman.setSuperhumanId(idCounter);
        idCounter++;
        superhumans.put(superhuman.getSuperhumanId(), superhuman);
    }

    @Override
    public void deleteSuperhuman(int superhumanId) {
        superhumans.remove(superhumanId);
    }

    @Override
    public void updateSuperhuman(Superhuman superhuman) {
        superhumans.put(superhuman.getSuperhumanId(), superhuman);
    }

    @Override
    public Superhuman getSuperhumanById(int id) {
        return superhumans.get(id);
    }

    @Override
    public List<Superhuman> getAllSuperhumans() {
        Collection<Superhuman> allSuperhumans = superhumans.values();
        return new ArrayList(allSuperhumans);
    }

    @Override
    public List<Superhuman> getSuperhumansByLocationId(long locationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Superhuman> getSuperhumansByOrganization(int orgId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getSuperhumanIdsUsingPower(int superpowerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Superhuman> getSuperhumansBySightingId(long sightingId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
