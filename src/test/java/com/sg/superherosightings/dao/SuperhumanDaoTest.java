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
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class SuperhumanDaoTest {
    
    private SuperhumanDao heroDao;
    private SuperpowerDao powerDao;
    private LocationDao locDao;
    private OrganizationDao orgDao;
    private SightingDao sightingDao;
    
    Superhuman testHero = new Superhuman();
    Location testLoc = new Location();
    Organization testOrg = new Organization();
    Superpower testPower = new Superpower();
    Sighting testSighting = new Sighting();
    
    public SuperhumanDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        heroDao = ctx.getBean("superhumanDao", SuperhumanDao.class);
        powerDao = ctx.getBean("superpowerDao", SuperpowerDao.class);
        locDao = ctx.getBean("locationDao", LocationDao.class);
        orgDao = ctx.getBean("organizationDao", OrganizationDao.class);
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
        
        List<Location> locs = locDao.getAllLocations();
        for (Location loc : locs) {
            locDao.deleteLocation(loc.getLocationId());
        }
        
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
        
        List<Location> locs = locDao.getAllLocations();
        for (Location loc : locs) {
            locDao.deleteLocation(loc.getLocationId());
        }
    }

    /**
     * Test of addSuperhuman method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testAddGetSuperhumanById() throws Exception {
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        Superhuman fromDao = heroDao.getSuperhumanById(testHero.getSuperhumanId());
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
    }

    /**
     * Test of deleteSuperhuman method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testDeleteSuperhuman() throws Exception {
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        Superhuman fromDao = heroDao.getSuperhumanById(testHero.getSuperhumanId());
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        
        heroDao.deleteSuperhuman(testHero.getSuperhumanId());
        assertNull(heroDao.getSuperhumanById(testHero.getSuperhumanId()));
    }

    /**
     * Test of updateSuperhuman method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testUpdateSuperhuman() throws Exception {
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        Superhuman fromDao = heroDao.getSuperhumanById(testHero.getSuperhumanId());
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Hackers");
        revisedOrg.setOrgDescription("Earth's most deviant devs.");
        revisedOrg.setPhone("5559876543");
        revisedOrg.setEmail("contact@hackerbros.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        orgDao.addOrganization(revisedOrg);
        
        List<Organization> orgs = new ArrayList<>();
        orgs.add(revisedOrg);
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo("Supercoder");
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setOrganizations(orgs);
        revisedSuperhuman.setSuperpowers(powerDao.getAllSuperpowers());
        revisedSuperhuman.setSuperhumanId(testHero.getSuperhumanId());
        
        heroDao.updateSuperhuman(revisedSuperhuman);
        fromDao = heroDao.getSuperhumanById(revisedSuperhuman.getSuperhumanId());
        assertEquals(fromDao.getSuperhumanId(), revisedSuperhuman.getSuperhumanId());
        assertEquals(fromDao.getAlterEgo(), revisedSuperhuman.getAlterEgo());
    }

    /**
     * Test of getAllSuperhumans method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetAllSuperhumans() throws Exception {
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        assertEquals(1, heroDao.getAllSuperhumans().size());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Hackers");
        revisedOrg.setOrgDescription("Earth's most deviant devs.");
        revisedOrg.setPhone("5559876543");
        revisedOrg.setEmail("contact@hackerbros.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        orgDao.addOrganization(revisedOrg);
        
        List<Organization> orgs = new ArrayList<>();
        orgs.add(revisedOrg);
        
        Superhuman testSuperhuman2 = new Superhuman();
        testSuperhuman2.setAlterEgo("Supercoder");
        testSuperhuman2.setDescription("Faster than a speeding supercomputer");
        testSuperhuman2.setVillain(true);
        testSuperhuman2.setOrganizations(orgs);
        testSuperhuman2.setSuperpowers(powerDao.getAllSuperpowers());
        testSuperhuman2.setSuperhumanId(testHero.getSuperhumanId());
        
        heroDao.addSuperhuman(testSuperhuman2);
        assertEquals(2, heroDao.getAllSuperhumans().size());
    }

    /**
     * Test of getSuperhumansByLocationId method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetSuperhumansByLocationId() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        List<Superhuman> superhumans = heroDao.getAllSuperhumans();
        testSighting.setHeroes(superhumans);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        sightingDao.addSighting(testSighting);
        
        List<Superhuman> heroesAtLocation = heroDao.getSuperhumansByLocationId(testLoc.getLocationId());
        assertEquals(1, heroesAtLocation.size()); 
    }

    /**
     * Test of getSuperhumansByOrganization method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetSuperhumansByOrganization() throws Exception {
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        Organization testOrg2 = new Organization();
        testOrg2.setOrganizationName("Brotherhood of Hackers");
        testOrg2.setOrgDescription("Earth's most deviant devs.");
        testOrg2.setPhone("5559876543");
        testOrg2.setEmail("contact@hackerbros.com");
        testOrg2.setVillain(true);
        testOrg2.setLocation(testLoc);
        orgDao.addOrganization(testOrg2);
        
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg2);
        
        Superhuman testSuperhuman2 = new Superhuman();
        testSuperhuman2.setAlterEgo("Captain Hacker");
        testSuperhuman2.setDescription("Deviant dev");
        testSuperhuman2.setVillain(true);
        testSuperhuman2.setOrganizations(orgs);
        testSuperhuman2.setSuperpowers(powerDao.getAllSuperpowers());
        heroDao.addSuperhuman(testSuperhuman2);
        
        List<Superhuman> fromDao = heroDao.getSuperhumansByOrganization(testOrg.getOrganizationId());
        assertEquals(fromDao.get(0).getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.get(0).getSuperhumanId(), testHero.getSuperhumanId());
        
        fromDao = heroDao.getSuperhumansByOrganization(testOrg2.getOrganizationId());
        assertEquals(fromDao.get(0).getAlterEgo(), testSuperhuman2.getAlterEgo());
        assertEquals(fromDao.get(0).getDescription(), testSuperhuman2.getDescription());
    }

    /**
     * Test of testGetSuperhumanIdsUsingPower method, of class SuperhumanDao.
     */
    @Test
    public void testGetSuperhumanIdsUsingPower() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        List<Superhuman> superhumans = heroDao.getAllSuperhumans();
        testSighting.setHeroes(superhumans);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        sightingDao.addSighting(testSighting);
        
        List<Integer> heroesWithPower = heroDao.getSuperhumanIdsUsingPower(testPower.getSuperpowerId());
        assertEquals(1, heroesWithPower.size()); 
    }

    /**
     * Test of testGetSuperhumansBySightingId method, of class SuperhumanDao.
     */
    @Test
    public void testGetSuperhumanBySightingId() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        locDao.addLocation(testLoc);
        orgDao.addOrganization(testOrg);
        testHero.setSuperpowers(powerDao.getAllSuperpowers());
        testHero.setOrganizations(orgDao.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        List<Superhuman> superhumans = heroDao.getAllSuperhumans();
        testSighting.setHeroes(superhumans);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        sightingDao.addSighting(testSighting);
        
        List<Superhuman> heroesAtSighting = heroDao.getSuperhumansBySightingId(testSighting.getSightingId());
        assertEquals(1, heroesAtSighting.size()); 
    }
}
