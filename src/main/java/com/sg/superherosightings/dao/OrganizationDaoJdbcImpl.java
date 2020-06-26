/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jeff
 */
public class OrganizationDaoJdbcImpl implements OrganizationDao {
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (OrganizationName, OrgDescription, "
            + "Phone, Email, LocationID, Villain) "
            + "values (?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_DELETE_ORGANIZATION
            = "delete from Organization where OrganizationID = ?";
    
    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set OrganizationName = ?, OrgDescription = ?, "
            + "Phone = ?, Email = ?, LocationID = ?, Villain = ? "
            + "where OrganizationID = ?";
    
    private static final String SQL_SELECT_ORGANIZATION
            = "select * from Organization where OrganizationID = ?";
    
    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from Organization";
    
    private static final String SQL_SELECT_ORGANIZATIONS_BY_SUPERHUMAN
            = "select o.OrganizationID, o.OrganizationName, o.OrgDescription, "
            + "o.Phone, o.Email, o.LocationID, o.Villain from Organization o "
            + "inner join SuperhumanOrganization so on o.OrganizationID = so.OrganizationID "
            + "where SuperhumanID = ?";
    
    private static final String SQL_SELECT_ORGANIZATIONS_BY_LOCATION
            = "select OrganizationID, OrganizationName, OrgDescription, Phone, "
            + "Email, Villain from Organization where LocationID = ?";
    
    private static final String SQL_DELETE_SUPERHUMANORGANIZATION_ORGANIZATION
            = "delete from SuperhumanOrganization where OrganizationID = ?";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION, 
                organization.getOrganizationName(),
                organization.getOrgDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getLocation().getLocationId(),
                organization.isVillain());
        organization.setOrganizationId(jdbcTemplate.queryForObject(
                "select LAST_INSERT_ID()", Integer.class));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteOrganization(int organizationId) {
        /* Delete all instances of organizationID on SuperhumanOrganization 
        bridge table, then delete from the Organization table. */
        jdbcTemplate.update(SQL_DELETE_SUPERHUMANORGANIZATION_ORGANIZATION, 
                organizationId);
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    @Override
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION, 
                organization.getOrganizationName(),
                organization.getOrgDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getLocation().getLocationId(),
                organization.isVillain(), organization.getOrganizationId());
    }

    @Override
    public Organization getOrganizationById(int orgId) {
        try {
            Organization org = jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION, 
                new OrganizationMapper(), orgId);
            return org;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, 
                new OrganizationMapper());
    }

    @Override
    public List<Organization> getOrganizationsBySuperhumanId(int superhumanId) {
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_SUPERHUMAN, 
                new OrganizationMapper(), superhumanId);
    }

    @Override
    public List<Organization> getOrganizationIdsAtLocation(long locationId) {
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_LOCATION, 
                new OrganizationMapper(), locationId);
    }
    
    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationName(rs.getString("OrganizationName"));
            org.setOrgDescription(rs.getString("OrgDescription"));
            org.setPhone(rs.getString("Phone"));
            org.setEmail(rs.getString("Email"));
            org.setVillain(rs.getBoolean("Villain"));
            org.setOrganizationId(rs.getInt("OrganizationID"));
            return org;
        }
    }
}
