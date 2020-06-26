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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jeff
 */
public class SuperhumanServiceTest {
    
    private SuperhumanService heroService;
    private SuperpowerService powerService;
    private LocationService locService;
    private OrganizationService orgService; 
    
    Superhuman testHero = new Superhuman();
    Superhuman invalidHero = new Superhuman();
    Location testLoc = new Location();
    Organization testOrg = new Organization();
    Superpower testPower = new Superpower();
    
    public SuperhumanServiceTest() {
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
        heroService = ctx.getBean("superhumanService", SuperhumanService.class);
        powerService = ctx.getBean("superpowerService", SuperpowerService.class);
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        locService = ctx.getBean("locationService", LocationService.class);
        
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
        
        testPower.setSuperpowerDescription("Super coding power");
        testHero.setAlterEgo("Supercoder");
        testHero.setDescription("World's most powerful coder.");
        testHero.setVillain(false);
        invalidHero.setAlterEgo("Invalid Hero");
        invalidHero.setDescription("Not legitimate at all.");
        invalidHero.setVillain(false);
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
        testOrg.setLocation(testLoc);
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
     * Test of addSuperhuman method, of class SuperhumanService.
     */
    @Test
    public void testAddGetSuperhumanValid() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        Superhuman fromDao = heroService.getSuperhumanById(testHero.getSuperhumanId());
        
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.getDescription(), testHero.getDescription());  
    }
    
    @Test
    public void testAddGetSuperhumanNoPowerList() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        invalidHero.setOrganizations(orgService.getAllOrganizations());
        try {
            heroService.addSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    @Test
    public void testAddGetSuperhumanNoOrgList() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        invalidHero.setSuperpowers(powerService.getAllSuperpowers());
        try {
            heroService.addSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testAddGetSuperhumanNoAlterEgo() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        invalidHero.setSuperpowers(powerService.getAllSuperpowers());
        invalidHero.setOrganizations(orgService.getAllOrganizations());
        invalidHero.setAlterEgo("");
        try {
            heroService.addSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testAddGetSuperhumanTooLongAlterEgo() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        invalidHero.setSuperpowers(powerService.getAllSuperpowers());
        invalidHero.setOrganizations(orgService.getAllOrganizations());
        invalidHero.setAlterEgo("Waytoolongalteregomanmanmanmanmanmanmanman");
        try {
            heroService.addSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testAddGetSuperhumanBadDescription() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        invalidHero.setSuperpowers(powerService.getAllSuperpowers());
        invalidHero.setOrganizations(orgService.getAllOrganizations());
        invalidHero.setDescription(null);
        try {
            heroService.addSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }    

    /**
     * Test of deleteSuperhuman method, of class SuperhumanService.
     */
    @Test
    public void testDeleteSuperhuman() {
    }

    /**
     * Test of updateSuperhuman method, of class SuperhumanService.
     */
    @Test
    public void testUpdateSuperhumanValid() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        Superhuman fromDao = heroService.getSuperhumanById(testHero.getSuperhumanId());
        
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.getDescription(), testHero.getDescription()); 
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo("Supercoder");
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setOrganizations(orgService.getAllOrganizations());
        revisedSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
        revisedSuperhuman.setSuperhumanId(testHero.getSuperhumanId());
        
        heroService.updateSuperhuman(revisedSuperhuman);
        fromDao = heroService.getSuperhumanById(revisedSuperhuman.getSuperhumanId());
        assertEquals(fromDao.getAlterEgo(), revisedSuperhuman.getAlterEgo());
        assertEquals(fromDao.getDescription(), revisedSuperhuman.getDescription());        
    }
    
    @Test
    public void testUpdateSuperhumanNoPowerList() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        Superhuman fromDao = heroService.getSuperhumanById(testHero.getSuperhumanId());
        
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.getDescription(), testHero.getDescription()); 
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo("Supercoder");
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setOrganizations(orgService.getAllOrganizations());
        revisedSuperhuman.setSuperhumanId(testHero.getSuperhumanId());
        try {
            heroService.updateSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    @Test
    public void testUpdateSuperhumanNoOrgList() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        Superhuman fromDao = heroService.getSuperhumanById(testHero.getSuperhumanId());
        
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.getDescription(), testHero.getDescription()); 
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo("Supercoder");
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
        revisedSuperhuman.setSuperhumanId(testHero.getSuperhumanId());
        try {
            heroService.updateSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateSuperhumanNoAlterEgo() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        Superhuman fromDao = heroService.getSuperhumanById(testHero.getSuperhumanId());
        
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.getDescription(), testHero.getDescription()); 
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo(null);
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setOrganizations(orgService.getAllOrganizations());
        revisedSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
        revisedSuperhuman.setSuperhumanId(testHero.getSuperhumanId());
        try {
            heroService.updateSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateSuperhumanTooLongAlterEgo() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        Superhuman fromDao = heroService.getSuperhumanById(testHero.getSuperhumanId());
        
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.getDescription(), testHero.getDescription()); 
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo("Supercodercodercodercodercoderr");
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setOrganizations(orgService.getAllOrganizations());
        revisedSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
        revisedSuperhuman.setSuperhumanId(testHero.getSuperhumanId());
        try {
            heroService.updateSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateSuperhumanBadDescription() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        Superhuman fromDao = heroService.getSuperhumanById(testHero.getSuperhumanId());
        
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.getDescription(), testHero.getDescription()); 
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo("Supercoder");
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer..."
                + "this is going to be far far far far far too long because "
                + "we are trying to test that the validation methods in the "
                + "service layer will catch a description that would be too "
                + "long for the database to accept.");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setOrganizations(orgService.getAllOrganizations());
        revisedSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
        revisedSuperhuman.setSuperhumanId(testHero.getSuperhumanId());
        try {
            heroService.updateSuperhuman(invalidHero);
            fail("Expected SuperhumanInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of getAllSuperhumans method, of class SuperhumanService.
     */
    @Test
    public void testGetAllSuperhumans() throws Exception {
        List<Superhuman> superhumans = heroService.getAllSuperhumans();
        assertEquals(0, superhumans.size());
        
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        superhumans = heroService.getAllSuperhumans();
        assertEquals(1, superhumans.size());
    }

    /**
     * Test of getSuperhumansByLocationId method, of class SuperhumanService.
     */
    @Test
    public void testGetSuperhumansByLocationId() throws Exception {
    }

    /**
     * Test of getSuperhumansByOrganization method, of class SuperhumanService.
     */
    @Test
    public void testGetSuperhumansByOrganization() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        
        heroService.addSuperhuman(testHero);
        List<Superhuman> superhumans = heroService.getSuperhumansByOrganization(
                testOrg.getOrganizationId());
        assertEquals(1, superhumans.size());
    }    
}
