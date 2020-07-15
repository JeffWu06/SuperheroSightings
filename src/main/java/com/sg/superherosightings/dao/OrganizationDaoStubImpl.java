/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Organization;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jeff
 */
public class OrganizationDaoStubImpl implements OrganizationDao {
    
    private Map<Integer, Organization> orgs = new HashMap<>();
    private int idCounter = 1;

    @Override
    public void addOrganization(Organization organization) {
        organization.setOrganizationId(idCounter);
        idCounter++;
        orgs.put(organization.getOrganizationId(), organization);
    }

    @Override
    public void deleteOrganization(int organizationId) {
        orgs.remove(organizationId);
    }

    @Override
    public void updateOrganization(Organization organization) {
        orgs.put(organization.getOrganizationId(), organization);
    }

    @Override
    public Organization getOrganizationById(int orgId) {
        return orgs.get(orgId);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        Collection<Organization> allOrgs = orgs.values();
        return new ArrayList(allOrgs);
    }

    @Override
    public List<Organization> getOrganizationsBySuperhumanId(int superhumanId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Organization> getOrganizationIdsAtLocation(long locationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
