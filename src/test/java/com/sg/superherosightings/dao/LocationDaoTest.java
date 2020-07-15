/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superhuman;
import com.sg.superherosightings.model.Superpower;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jeff
 */
public class LocationDaoTest {
    LocationDao dao;
    OrganizationDao orgDao;
    SuperpowerDao powerDao;
    SuperhumanDao heroDao;
    SightingDao sightingDao;
    Organization testOrg = new Organization();
    Location testLoc = new Location();
    /* When they are ready, instantiate Superhuman, and Sighting
    services so you can add sample Objects for each to make sure the 
    getLocationBy... methods work. See what Pat did in his PlayerDaoImplTest.
    */
    
    public LocationDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("locationDao", LocationDao.class);
        orgDao = ctx.getBean("organizationDao", OrganizationDao.class);
        powerDao = ctx.getBean("superpowerDao", SuperpowerDao.class);
        heroDao = ctx.getBean("superhumanDao", SuperhumanDao.class);
        sightingDao = ctx.getBean("sightingDao", SightingDao.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        /* Delete all existing Superpower database rows, if any, and initialize
        values for the testPower. */

        List<Organization> orgs = orgDao.getAllOrganizations();
        for (Organization org : orgs) {
            orgDao.deleteOrganization(org.getOrganizationId());
        }
        
        List<Superpower> superpowers = powerDao.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            powerDao.deleteSuperpower(currentPower.getSuperpowerId());
        }
                
        List<Superhuman> heroes = heroDao.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroDao.deleteSuperhuman(hero.getSuperhumanId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSighting(sighting.getSightingId());
        }
        
        List<Location> locations = dao.getAllLocations();
        for (Location currentLoc : locations) {
            dao.deleteLocation(currentLoc.getLocationId());
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
        
        testOrg.setOrganizationName("League of Extraordinary Devs");
        testOrg.setOrgDescription("Earth's mightiest devs.");
        testOrg.setPhone("5555555555");
        testOrg.setEmail("info@leagueofdevs.com");
        testOrg.setVillain(false);
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test of addLocation method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testAddGetLocation() {
        dao.addLocation(testLoc);
        Location fromDao = dao.getLocationById(testLoc.getLocationId());
        assertEquals(fromDao.getLocationId(), testLoc.getLocationId());
        assertEquals(fromDao.getLocationName(), testLoc.getLocationName());
        assertEquals(fromDao.getCity(), testLoc.getCity());
    }

    /**
     * Test of deleteLocation method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testDeleteLocation() {
        dao.addLocation(testLoc);
        Location fromDao = dao.getLocationById(testLoc.getLocationId());
        assertEquals(fromDao.getLocationId(), testLoc.getLocationId());
        assertEquals(fromDao.getLocationName(), testLoc.getLocationName());
        assertEquals(fromDao.getCity(), testLoc.getCity());
        
        dao.deleteLocation(fromDao.getLocationId());
        assertNull(dao.getLocationById(testLoc.getLocationId()));
    }

    /**
     * Test of updateLocation method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testUpdateLocation() {
        dao.addLocation(testLoc);
        Location fromDao = dao.getLocationById(testLoc.getLocationId());
        assertEquals(fromDao.getLocationId(), testLoc.getLocationId());
        assertEquals(fromDao.getLocationName(), testLoc.getLocationName());
        assertEquals(fromDao.getCity(), testLoc.getCity());
        
        Location revisedLoc = new Location();
        revisedLoc.setLocationName("Test Place");
        revisedLoc.setLocationDescription("HQ for the League of Extraordinary Devs");
        revisedLoc.setStreet("123 main st.");
        revisedLoc.setCity("Testville");
        revisedLoc.setState("CA");
        revisedLoc.setZip("98765");
        revisedLoc.setCountry("US");
        revisedLoc.setLatitude(new BigDecimal("78.888544"));
        revisedLoc.setLongitude(new BigDecimal("98.888888"));
        revisedLoc.setLocationId(testLoc.getLocationId());
        
        dao.updateLocation(revisedLoc);
        fromDao = dao.getLocationById(revisedLoc.getLocationId());
        assertEquals(fromDao.getLocationId(), revisedLoc.getLocationId());
        assertEquals(fromDao.getLocationDescription(), revisedLoc.getLocationDescription());
        assertEquals(fromDao.getStreet(), revisedLoc.getStreet());
        assertEquals(fromDao.getLatitude(), revisedLoc.getLatitude());
    }

    /**
     * Test of getAllLocations method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetAllLocations() {
        dao.addLocation(testLoc);
        List<Location> locations = dao.getAllLocations();
        assertEquals(1, locations.size());
        
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
        
        dao.addLocation(testLoc2);
        locations = dao.getAllLocations();
        assertEquals(2, locations.size());        
    }

    /**
     * Test of getLocationByOrganizationId method, of class LocationDao.
     */
    @Test
    public void testGetLocationByOrganizationId() throws Exception {
        dao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        
        Location fromDao = dao.getLocationByOrganizationId(testOrg.getOrganizationId());
        assertEquals(fromDao.getLocationName(), testLoc.getLocationName());
    }

    /**
     * Test of getLocationBySightingId method, of class LocationDao.
     */
    @Test
    public void testGetLocationBySightingId() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        Superhuman testHero = new Superhuman();
        Superpower testPower = new Superpower();
        Sighting testSighting = new Sighting();
        testPower.setSuperpowerDescription("Super coding power");
        testHero.setAlterEgo("Supercoder");
        testHero.setDescription("World's most powerful coder.");
        testHero.setVillain(false);
        testLoc.setLocationName("Test Place");
        testLoc.setLocationDescription("HQ for the League of Superdevs");
        testLoc.setStreet("123 main st.");
        testLoc.setCity("Testville");
        testLoc.setState("CA");
        testLoc.setZip("98765");
        testLoc.setCountry("US");
        testLoc.setLatitude(new BigDecimal("70.000000"));
        testLoc.setLongitude(new BigDecimal("98.000000"));
        testOrg.setOrganizationName("League of Extraordinary Devs");
        testOrg.setOrgDescription("Earth's mightiest devs.");
        testOrg.setPhone("5555555555");
        testOrg.setEmail("info@leagueofdevs.com");
        testOrg.setVillain(false);
        
        powerDao.addSuperpower(testPower);
        dao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        List<Superhuman> heroes = heroDao.getAllSuperhumans();
        testSighting.setHeroes(heroes);
        sightingDao.addSighting(testSighting);
        
        Superhuman fromDao = heroDao.getSuperhumanById(testHero.getSuperhumanId());
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        
        Location testSightingLoc = dao.getLocationBySightingId(testSighting.getSightingId());
        assertEquals(testSightingLoc.getLocationId(), testLoc.getLocationId());
        assertEquals(testSightingLoc.getLocationName(), testLoc.getLocationName());
    }
}
