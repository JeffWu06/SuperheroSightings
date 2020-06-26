/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.dao.OrganizationDao;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhuman;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeff
 */
public class OrganizationServiceImpl implements OrganizationService {
    
    private final int MAX_NAME = 30;
    private final int MAX_DESC = 140;
    private final int MAX_PHONE = 15;
    private final int MAX_EMAIL = 40;
    
    OrganizationDao dao;

    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    public void addOrganization(Organization organization) throws OrganizationInvalidDataException {
        validateOrganizationData(organization);
        dao.addOrganization(organization);
    }

    @Override
    public void deleteOrganization(int organizationId) {
        dao.deleteOrganization(organizationId);
    }

    @Override
    public void updateOrganization(Organization organization) throws OrganizationInvalidDataException {
        validateOrganizationData(organization);
        dao.updateOrganization(organization);
    }
    
    @Override
    public Organization getOrganizationById(int id) {
        return dao.getOrganizationById(id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return dao.getAllOrganizations();
    }

    @Override
    public List<Organization> getOrganizationsBySuperhumanId(int superhumanId) {
        return dao.getOrganizationsBySuperhumanId(superhumanId);
    }

    @Override
    public List<Organization> getOrganizationsAtLocation(long locationId) {
        return dao.getOrganizationIdsAtLocation(locationId);
    }

    @Override
    public List<Organization> getUnbelongedOrganizationsBySuperhuman(Superhuman superhuman) {
        List<Organization> unbelongedOrgs = new ArrayList<>();
        List<Organization> memberOrgs = superhuman.getOrganizations();
        List<Organization> allOrgs = getAllOrganizations();
        for(Organization org : allOrgs) {
            int id = org.getOrganizationId();
            boolean notMember = true;
            for(Organization memberOrg : memberOrgs) {
                if(memberOrg.getOrganizationId() == id) {
                    notMember = false;
                }
            }
            if (notMember) {
                unbelongedOrgs.add(org);
            }
        }
        return unbelongedOrgs;
    }
    
    private void validateOrganizationData(Organization org) 
            throws OrganizationInvalidDataException {
        checkStringLength(org.getOrganizationName(), MAX_NAME, "Organization name");
        checkStringLength(org.getOrgDescription(), MAX_DESC, "Organization description");
        checkPhone(org.getPhone(), MAX_PHONE);
        checkEmail(org.getEmail(), MAX_EMAIL);
        if (org.getLocation() == null) {
            throw new OrganizationInvalidDataException("Organization must have valid location.");
        }
    }
    
    private void checkStringLength(String string, int max, String fieldName) 
            throws OrganizationInvalidDataException {
        try {
            if (string.length() > max || string.length() == 0) {
                throw new OrganizationInvalidDataException(fieldName + " must be no "
                        + "more than " + max + " characters in length.");
            }
        } catch (NullPointerException e) {
            throw new OrganizationInvalidDataException(fieldName + " is required.");
        }
    }
    
    private void checkPhone(String phone, int max) 
            throws OrganizationInvalidDataException {
        try {
            if (phone.length() != 0) {
                if (phone.length() > max) {
                    throw new OrganizationInvalidDataException("Phone must be no "
                            + "more than " + max + " characters in length.");
                }
            }
        } catch (NullPointerException e) {
            return;
        }
    }

    private void checkEmail(String email, int max) 
            throws OrganizationInvalidDataException {
        try {
            if (email.length() != 0) {
                if (email.length() > max || !email.contains("@")) {
                    throw new OrganizationInvalidDataException("Email must be no "
                            + "more than " + max + " characters in length and "
                                    + "contain '@' symbol.");
                }                
            }
        } catch (NullPointerException e) {
            return;
        } 
    }
}
