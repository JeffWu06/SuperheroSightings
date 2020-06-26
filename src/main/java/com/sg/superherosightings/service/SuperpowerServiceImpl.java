/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.SuperpowerDao;
import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeff
 */
public class SuperpowerServiceImpl implements SuperpowerService {
    
    private final int MAX_SUPERPOWER_LENGTH = 30;
    
    SuperpowerDao dao;

    public SuperpowerServiceImpl(SuperpowerDao dao) {
        this.dao = dao;
    }

    @Override
    public void addSuperpower(Superpower superpower) throws SuperpowerInvalidDataException {
        checkStringLength(superpower.getSuperpowerDescription(), MAX_SUPERPOWER_LENGTH);
        dao.addSuperpower(superpower);
    }

    @Override
    public void updateSuperpower(Superpower superpower) throws SuperpowerInvalidDataException {
        checkStringLength(superpower.getSuperpowerDescription(), MAX_SUPERPOWER_LENGTH);
        dao.updateSuperpower(superpower);
    }

    @Override
    public void deleteSuperpower(int superpowerId) {
        dao.deleteSuperpower(superpowerId);
    }
    
    @Override
    public List<Superpower> getAllSuperpowers() {
        return dao.getAllSuperpowers();
    }

    @Override
    public Superpower getSuperpowerById(int id) {
        return dao.getSuperpowerById(id);
    }
    
    @Override
    public List<Superpower> getSuperpowersBySuperhumanId(int superhumanId) {
        return dao.getSuperpowersBySuperhumanId(superhumanId);
    }
    
    @Override
    public List<Superpower> getSuperpowersNotPossessedBySuperhumanId(Superhuman superhuman) {
        List<Superpower> unpossessedPowers = new ArrayList<>();
        List<Superpower> possessedPowers = superhuman.getSuperpowers();
        List<Superpower> allPowers = getAllSuperpowers();
        for (Superpower power : allPowers) {
            int id = power.getSuperpowerId();
            boolean notPossessed = true;
            for (Superpower possessedPower : possessedPowers) {
                if(possessedPower.getSuperpowerId() == id) {
                    notPossessed = false;
                }
            }
            if (notPossessed) {
                unpossessedPowers.add(power);
            }
        }
        return unpossessedPowers;
    }

    private void checkStringLength(String field, int max) 
            throws SuperpowerInvalidDataException {
        try {
            if (field.length() > max || field.length() == 0) {
                throw new SuperpowerInvalidDataException("Superpower must be no "
                        + "more than " + max + " characters in length.");
            }
        } catch (NullPointerException e) {
            throw new SuperpowerInvalidDataException("Field is required.");
        }
    }
}
