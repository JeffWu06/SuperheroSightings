/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
import com.sg.superherosightings.service.SuperhumanService;
import com.sg.superherosightings.service.SuperpowerService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jeff
 */
public class OrganizationDaoTest {
    
    OrganizationDao orgDao;
    LocationDao locDao;
    SuperhumanService heroService;
    SuperpowerService powerService;
    Organization testOrg = new Organization();
    Location testLoc = new Location();
    
    public OrganizationDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        orgDao = ctx.getBean("organizationDao", OrganizationDao.class);
        locDao = ctx.getBean("locationDao", LocationDao.class);
        heroService = ctx.getBean("superhumanService", SuperhumanService.class);
        powerService = ctx.getBean("superpowerService", SuperpowerService.class);
        
        List<Organization> orgs = orgDao.getAllOrganizations();
        for (Organization org : orgs) {
            orgDao.deleteOrganization(org.getOrganizationId());
        }
        
        List<Location> locs = locDao.getAllLocations();
        for (Location loc : locs) {
            locDao.deleteLocation(loc.getLocationId());
        }
        
        List<Superpower> superpowers = powerService.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            powerService.deleteSuperpower(currentPower.getSuperpowerId());
        }
        
        List<Superhuman> heroes = heroService.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroService.deleteSuperhuman(hero.getSuperhumanId());
        }
        
        testLoc.setLocationName("Test Place");
        testLoc.setLocationDescription("HQ for the League of Superdevs");
        testLoc.setStreet("123 main st.");
        testLoc.setCity("Testville");
        testLoc.setState("CA");
        testLoc.setZip("98765");
        testLoc.setCountry("US");
        testLoc.setLatitude(new BigDecimal("78.888544"));
        testLoc.setLongitude(new BigDecimal("98.888888"));
        
        testOrg.setOrganizationName("League of Extraordinary Devs");
        testOrg.setOrgDescription("Earth's mightiest devs.");
        testOrg.setPhone("5555555555");
        testOrg.setEmail("info@leagueofdevs.com");
        testOrg.setVillain(false);
    }
    
    @After
    public void tearDown() {
        List<Organization> orgs = orgDao.getAllOrganizations();
        for (Organization org : orgs) {
            orgDao.deleteOrganization(org.getOrganizationId());
        }
        
        List<Location> locs = locDao.getAllLocations();
        for (Location loc : locs) {
            locDao.deleteLocation(loc.getLocationId());
        }
        
        List<Superpower> superpowers = powerService.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            powerService.deleteSuperpower(currentPower.getSuperpowerId());
        }
        
        List<Superhuman> heroes = heroService.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroService.deleteSuperhuman(hero.getSuperhumanId());
        }
    }

    /**
     * Test of addOrganization method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testAddGetOrganization() {
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);        
        Organization fromDao = orgDao.getOrganizationById(testOrg.getOrganizationId());        
        assertEquals(fromDao.getOrganizationId(), testOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), testOrg.getOrganizationName());
    }
    
    /**
     * Test of addOrganization method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testAddGetOrganizationNoLocation() {
        try {
            orgDao.addOrganization(testOrg);
            fail("Expected DataIntegrityViolationException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }

    /**
     * Test of deleteOrganization method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testDeleteOrganization() {
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);        
        Organization fromDao = orgDao.getOrganizationById(testOrg.getOrganizationId());        
        assertEquals(fromDao.getOrganizationId(), testOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), testOrg.getOrganizationName());
        
        orgDao.deleteOrganization(testOrg.getOrganizationId());
        assertNull(orgDao.getOrganizationById(testOrg.getOrganizationId()));
    }

    /**
     * Test of updateOrganization method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testUpdateOrganization() {
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);        
        Organization fromDao = orgDao.getOrganizationById(testOrg.getOrganizationId());        
        assertEquals(fromDao.getOrganizationId(), testOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), testOrg.getOrganizationName());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Devs");
        revisedOrg.setOrgDescription("Earth's mightiest devs.");
        revisedOrg.setPhone("5555555555");
        revisedOrg.setEmail("info@leagueofdevs.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        revisedOrg.setOrganizationId(testOrg.getOrganizationId());
        
        orgDao.updateOrganization(revisedOrg);
        fromDao = orgDao.getOrganizationById(testOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), revisedOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), revisedOrg.getOrganizationName());     
    }

    /**
     * Test of getAllOrganizations method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetAllOrganizations() {
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg); 
        
        Location testLoc2 = new Location();
        testLoc2.setLocationName("Test Place");
        testLoc2.setLocationDescription("HQ for the League of Extraordinary Devs");
        testLoc2.setStreet("123 main st.");
        testLoc2.setCity("Testville");
        testLoc2.setState("CA");
        testLoc2.setZip("98765");
        testLoc2.setCountry("US");
        testLoc2.setLatitude(new BigDecimal("78.888544"));
        testLoc2.setLongitude(new BigDecimal("98.888888"));
        locDao.addLocation(testLoc2);
        
        Organization testOrg2 = new Organization();
        testOrg2.setOrganizationName("League of Extraordinary Devs");
        testOrg2.setOrgDescription("Earth's mightiest devs.");
        testOrg2.setPhone("5555555555");
        testOrg2.setEmail("info@leagueofdevs.com");
        testOrg2.setVillain(true);
        testOrg2.setLocation(testLoc2);
        orgDao.addOrganization(testOrg2);
        
        List<Organization> orgList = orgDao.getAllOrganizations();
        assertEquals(2, orgList.size());
    }

    /**
     * Test of getOrganizationsBySuperhumanId method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetOrganizationsBySuperhumanId() throws Exception {
        Superpower testPower = new Superpower();
        testPower.setSuperpowerDescription("Super coding power");
        powerService.addSuperpower(testPower);
        Superhuman testSuperhuman = new Superhuman();
        testSuperhuman.setAlterEgo("Supercoder");
        testSuperhuman.setDescription("World's most powerful coder.");
        testSuperhuman.setVillain(false);
        testSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testSuperhuman.setOrganizations(orgDao.getAllOrganizations());   

        heroService.addSuperhuman(testSuperhuman);
        
        List<Organization> fromDao = orgDao.getOrganizationsBySuperhumanId(
                testSuperhuman.getSuperhumanId());
        assertEquals(1, fromDao.size());
    }
}
