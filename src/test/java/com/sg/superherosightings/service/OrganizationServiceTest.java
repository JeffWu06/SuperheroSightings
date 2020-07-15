/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
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
public class OrganizationServiceTest {
    
    OrganizationService orgService;
    LocationService locService;
    SuperhumanService heroService;
    SuperpowerService powerService;
    Organization validOrg = new Organization();
    Organization invalidOrg = new Organization();
    Location testLoc = new Location();
    
    public OrganizationServiceTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        locService = ctx.getBean("locationService", LocationService.class);
        heroService = ctx.getBean("superhumanService", SuperhumanService.class);
        powerService = ctx.getBean("superpowerService", SuperpowerService.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Organization> orgs = orgService.getAllOrganizations();
        for (Organization org : orgs) {
            orgService.deleteOrganization(org.getOrganizationId());
        }
        
        List<Location> locs = locService.getAllLocations();
        for (Location loc : locs) {
            locService.deleteLocation(loc.getLocationId());
        }
        
        List<Superpower> superpowers = powerService.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            powerService.deleteSuperpower(currentPower.getSuperpowerId());
        }
        
        List<Superhuman> heroes = heroService.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroService.deleteSuperhuman(hero.getSuperhumanId());
        }
        
        testLoc.setLocationName("Coder's Lair");
        testLoc.setLocationDescription("Where supercoders converge");
        testLoc.setStreet("123 test rd");
        testLoc.setCity("Somewhereville");
        testLoc.setState("ST");
        testLoc.setZip("99999");
        testLoc.setCountry("US");
        testLoc.setLatitude(new BigDecimal("50.25"));
        testLoc.setLongitude(new BigDecimal("100.25"));
        
        validOrg.setOrganizationName("League of Extraordinary Devs");
        validOrg.setOrgDescription("Earth's mightiest devs.");
        validOrg.setPhone("5555555555");
        validOrg.setEmail("info@leagueofdevs.com");
        validOrg.setVillain(false);
    }
    
    @After
    public void tearDown() {
        List<Organization> orgs = orgService.getAllOrganizations();
        for (Organization org : orgs) {
            orgService.deleteOrganization(org.getOrganizationId());
        }
        
        List<Location> locs = locService.getAllLocations();
        for (Location loc : locs) {
            locService.deleteLocation(loc.getLocationId());
        }
    }

    /**
     * Test of addOrganization method, of class OrganizationService.
     */
    @Test
    public void testAddGetValidOrganization() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
    }
    
    @Test
    public void testAddGetOrganizationNoLocation() throws Exception {
        locService.addLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }
    
    @Test
    public void testAddGetOrganizationInvalidEmail() throws Exception {
        validOrg.setEmail("info.leagueofdevs.com");
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }
    
    @Test
    public void testAddGetOrganizationTooLongEmail() throws Exception {
        validOrg.setEmail("info@leagueofdevs.cominfo@leagueofdevs.com");
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }
    
    @Test
    public void testAddGetOrganizationTooLongPhone() throws Exception {
        validOrg.setPhone("info@leagueofdevs.com");
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }
    
    @Test
    public void testAddGetOrganizationTooLongName() throws Exception {
        validOrg.setOrganizationName("League of Extraordinary Extraordinary Developerssss");
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }
    
    @Test
    public void testAddGetOrganizationNoName() throws Exception {
        validOrg.setOrganizationName(null);
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }
    
    @Test
    public void testAddGetOrganizationTooLongDesc() throws Exception {
        validOrg.setOrganizationName("League of Extraordinary Extraordinary "
                + "Developerssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
                + "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }
    
    @Test
    public void testAddGetOrganizationNoDesc() throws Exception {
        validOrg.setOrganizationName(null);
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        try {
            orgService.addOrganization(validOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e){
            return;
        }
    }

    /**
     * Test of deleteOrganization method, of class OrganizationService.
     */
    @Test
    public void testDeleteOrganization() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        orgService.deleteOrganization(validOrg.getOrganizationId());
        assertNull(orgService.getOrganizationById(validOrg.getOrganizationId()));
    }

    /**
     * Test of updateOrganization method, of class OrganizationService.
     */
    @Test
    public void testUpdateOrganizationValidOrg() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Devs");
        revisedOrg.setOrgDescription("Earth's mightiest devs.");
        revisedOrg.setPhone("5555555555");
        revisedOrg.setEmail("info@leagueofdevs.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        revisedOrg.setOrganizationId(validOrg.getOrganizationId());
        
        orgService.updateOrganization(revisedOrg);
        fromDao = orgService.getOrganizationById(revisedOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), revisedOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), revisedOrg.getOrganizationName());
        assertEquals(fromDao.getPhone(), revisedOrg.getPhone());
    }
    
    @Test
    public void testUpdateOrganizationInValidLocation() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Devs");
        revisedOrg.setOrgDescription("Earth's mightiest devs.");
        revisedOrg.setPhone("5555555555");
        revisedOrg.setEmail("info@leagueofdevs.com");
        revisedOrg.setVillain(true);
        revisedOrg.setOrganizationId(validOrg.getOrganizationId());
        
        try {
            orgService.updateOrganization(revisedOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateOrganizationInValidName() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("");
        revisedOrg.setOrgDescription("Earth's mightiest devs.");
        revisedOrg.setPhone("5555555555");
        revisedOrg.setEmail("info@leagueofdevs.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        revisedOrg.setOrganizationId(validOrg.getOrganizationId());
        
        try {
            orgService.updateOrganization(revisedOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateOrganizationInvalidDesc() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Devs");
        revisedOrg.setOrgDescription("");
        revisedOrg.setPhone("5555555555");
        revisedOrg.setEmail("info@leagueofdevs.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        revisedOrg.setOrganizationId(validOrg.getOrganizationId());
        
        try {
            orgService.updateOrganization(revisedOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateOrganizationInvalidPhone() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Devs");
        revisedOrg.setOrgDescription("Earth's mightiest devs");
        revisedOrg.setPhone("555555555512342142342");
        revisedOrg.setEmail("info@leagueofdevs.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        revisedOrg.setOrganizationId(validOrg.getOrganizationId());
        
        try {
            orgService.updateOrganization(revisedOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testUpdateOrganizationInvalidEmail() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Devs");
        revisedOrg.setOrgDescription("Earth's mightiest devs");
        revisedOrg.setPhone("5555555555");
        revisedOrg.setEmail("info/.leagueofdevs.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        revisedOrg.setOrganizationId(validOrg.getOrganizationId());
        
        try {
            orgService.updateOrganization(revisedOrg);
            fail("Expected OrganizationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }    

    /**
     * Test of getAllOrganizations method, of class OrganizationService.
     */
    @Test
    public void testGetAllOrganizations() throws Exception {
        locService.addLocation(testLoc);
        validOrg.setLocation(testLoc);
        orgService.addOrganization(validOrg);
        Organization fromDao = orgService.getOrganizationById(validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationId(), validOrg.getOrganizationId());
        assertEquals(fromDao.getOrganizationName(), validOrg.getOrganizationName());
        
        List<Organization> orgs = orgService.getAllOrganizations();
        assertEquals(1, orgs.size());
    }

    /**
     * Test of getOrganizationsBySuperhumanId method, of class OrganizationService.
     */
    @Test
    public void testGetOrganizationsBySuperhumanId() throws Exception {
//        Superpower testPower = new Superpower();
//        testPower.setSuperpowerDescription("Super coding power");
//        powerService.addSuperpower(testPower);
//        Superhuman testSuperhuman = new Superhuman();
//        testSuperhuman.setAlterEgo("Supercoder");
//        testSuperhuman.setDescription("World's most powerful coder.");
//        testSuperhuman.setVillain(false);
//        testSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
//        locService.addLocation(testLoc);
//        validOrg.setLocation(testLoc);
//        orgService.addOrganization(validOrg);
//        testSuperhuman.setOrganizations(orgService.getAllOrganizations());   
//
//        heroService.addSuperhuman(testSuperhuman);
//        
//        List<Organization> fromDao = orgService.getOrganizationsBySuperhumanId(
//                testSuperhuman.getSuperhumanId());
//        assertEquals(1, fromDao.size());
    }
    
}
