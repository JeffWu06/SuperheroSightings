/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhuman;
import java.util.List;

/**
 *
 * @author Jeff
 */
public interface OrganizationService {
    
    public void addOrganization(Organization organization) throws OrganizationInvalidDataException;
    
    public void deleteOrganization(int organizationId);
    
    public void updateOrganization(Organization organization) throws OrganizationInvalidDataException;
    
    public Organization getOrganizationById(int orgId);
    
    public List<Organization> getAllOrganizations();
    
    public List<Organization> getOrganizationsBySuperhumanId(int superhumanId);
    
    public List<Organization> getUnbelongedOrganizationsBySuperhuman(Superhuman superhuman);

    public List<Organization> getOrganizationsAtLocation(long locationId);
}
