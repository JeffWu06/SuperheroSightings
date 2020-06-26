/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.SuperhumanDao;
import com.sg.superherosightings.model.Superhuman;
import java.util.List;

/**
 *
 * @author Jeff
 */
public class SuperhumanServiceImpl implements SuperhumanService {
    
    private final int MAX_ALTEREGO = 30;
    private final int MAX_DESCRIPTION = 90;
    
    SuperhumanDao dao;

    public SuperhumanServiceImpl(SuperhumanDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Superhuman> getAllSuperhumans() {
        return dao.getAllSuperhumans();
    }

    @Override
    public void addSuperhuman(Superhuman superhuman) 
            throws SuperhumanInvalidDataException {
        validateSuperhumanData(superhuman);
        dao.addSuperhuman(superhuman);
    }

    @Override
    public Superhuman getSuperhumanById(int superhumanId) {
        return dao.getSuperhumanById(superhumanId);
    }

    @Override
    public void updateSuperhuman(Superhuman superhuman) 
            throws SuperhumanInvalidDataException {
        validateSuperhumanData(superhuman);
        dao.updateSuperhuman(superhuman);
    }

    @Override
    public void deleteSuperhuman(int superhumanId) {
        dao.deleteSuperhuman(superhumanId);
    }

    @Override
    public List<Superhuman> getSuperhumansByLocationId(long locationId) {
        return dao.getSuperhumansByLocationId(locationId);
    }

    @Override
    public List<Superhuman> getSuperhumansByOrganization(int orgId) {
        return dao.getSuperhumansByOrganization(orgId);
    }
    
    @Override public List<Integer> SuperhumanIdsUsingPower(int superpowerId) {
        return dao.getSuperhumanIdsUsingPower(superpowerId);
    }

    @Override
    public List<Superhuman> getSuperhumansBySightingId(long sightingId) {
        return dao.getSuperhumansBySightingId(sightingId);
    }
    
    private void validateSuperhumanData(Superhuman superhuman) 
            throws SuperhumanInvalidDataException {
        checkStringLength(superhuman.getAlterEgo(), MAX_ALTEREGO);
        checkStringLength(superhuman.getDescription(), MAX_DESCRIPTION);
        checkSuperpowerList(superhuman);
        checkOrganizationList(superhuman);
    }
    
    private void checkStringLength(String field, int max) 
            throws SuperhumanInvalidDataException {
        try {
            if (field.length() > max || field.length() == 0) {
                throw new SuperhumanInvalidDataException(
                        "Field must be no more than " + max + " characters in "
                                + "length.");
            }
        } catch (NullPointerException e) {
            throw new SuperhumanInvalidDataException("Field is required.");
        }
    }
    
    private void checkSuperpowerList(Superhuman superhuman) 
            throws SuperhumanInvalidDataException {
        try {
            superhuman.getSuperpowers();
        } catch (NullPointerException e) {
            throw new SuperhumanInvalidDataException(
                    "Must provide list of Superpowers.");
        }
    }
    
    private void checkOrganizationList(Superhuman superhuman) 
            throws SuperhumanInvalidDataException {
        try {
            superhuman.getOrganizations();
        } catch (NullPointerException e) {
            throw new SuperhumanInvalidDataException(
                    "Must provide list of Organizations.");
        }
    }
}
