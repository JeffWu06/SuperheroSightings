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
import java.time.Month;
import java.util.ArrayList;
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
public class SightingDaoTest {
    
    private SightingDao dao;
    private SuperhumanDao heroDao;
    private SuperpowerDao powerDao;
    private LocationDao locDao;
    private OrganizationDao orgDao; 
    
    Sighting testSighting = new Sighting();
    Superhuman testHero = new Superhuman();
    Location testLoc = new Location();
    Organization testOrg = new Organization();
    Superpower testPower = new Superpower();
    
    public SightingDaoTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        heroDao = ctx.getBean("superhumanDao", SuperhumanDao.class);
        powerDao = ctx.getBean("superpowerDao", SuperpowerDao.class);
        locDao = ctx.getBean("locationDao", LocationDao.class);
        orgDao = ctx.getBean("organizationDao", OrganizationDao.class);
        dao = ctx.getBean("sightingDao", SightingDao.class);
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
        
        List<Sighting> sightings = dao.getAllSightings();
        for (Sighting sighting : sightings) {
            dao.deleteSighting(sighting.getSightingId());
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
        testLoc.setLatitude(new BigDecimal("70.000000"));
        testLoc.setLongitude(new BigDecimal("98.000000"));
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
        
        List<Superpower> superpowers = powerDao.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            powerDao.deleteSuperpower(currentPower.getSuperpowerId());
        }
                
        List<Superhuman> heroes = heroDao.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroDao.deleteSuperhuman(hero.getSuperhumanId());
        }
        
        List<Sighting> sightings = dao.getAllSightings();
        for (Sighting sighting : sightings) {
            dao.deleteSighting(sighting.getSightingId());
        }
        
        List<Location> locs = locDao.getAllLocations();
        for (Location loc : locs) {
            locDao.deleteLocation(loc.getLocationId());
        }
    }

    /**
     * Test of addSighting method, of class SightingDao.
     */
    @Test
    public void testAddGetSighting() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        dao.addSighting(testSighting);
        Sighting fromDao = dao.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
    }

    /**
     * Test of deleteSighting method, of class SightingDao.
     */
    @Test
    public void testDeleteSighting() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        dao.addSighting(testSighting);
        dao.deleteSighting(testSighting.getSightingId());
        assertNull(dao.getSightingById(testSighting.getSightingId()));
    }

    /**
     * Test of updateSighting method, of class SightingDao.
     */
    @Test
    public void testUpdateSighting() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        dao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingId(testSighting.getSightingId());
        testSighting2.setSightingDate(LocalDate.of(2020, Month.MARCH, 31));
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);
        
        dao.updateSighting(testSighting2);
        Sighting fromDao = dao.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testSighting2.getSightingDate());
    }

    /**
     * Test of getAllSightings method, of class SightingDao.
     */
    @Test
    public void testGetAllSightings() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        dao.addSighting(testSighting);
        List<Sighting> fromDao = dao.getAllSightings();
        assertEquals(1, fromDao.size());
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingId(testSighting.getSightingId());
        testSighting2.setSightingDate(LocalDate.of(2020, Month.MARCH, 31));
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);        
        dao.addSighting(testSighting2);
        
        fromDao = dao.getAllSightings();
        assertEquals(2, fromDao.size());
    }

    /**
     * Test of getSightingsByDate method, of class SightingDao.
     */
    @Test
    public void testGetSightingsByDate() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);
        dao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingDate(testDate);
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);        
        dao.addSighting(testSighting2);
        
        List<Sighting> fromDao = dao.getSightingsByDate(testDate);
        assertEquals(2, fromDao.size());
    }

    /**
     * Test of getLastTenSightings method, of class SightingDao.
     */
    @Test
    public void testGetMostRecentSightings() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);
        dao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingDate(testDate);
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);        
        dao.addSighting(testSighting2);
        
        List<Sighting> fromDao = dao.getRecentSightings();
        assertTrue(fromDao.size() <= 10);
    }

    /**
     * Test of getSightingsByLocation method, of class SightingDao.
     */
    @Test
    public void testGetSightingsByLocation() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);
        dao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingDate(testDate);
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);        
        dao.addSighting(testSighting2);
        
        List<Sighting> fromDao = dao.getSightingsByLocation(testLoc.getLocationId());
        assertEquals(2, fromDao.size());
    }

    /**
     * Test of getSightingsBySuperhuman method, of class SightingDao.
     */
    @Test
    public void testGetSightingsBySuperhuman() {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerDao.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locDao.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroDao.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);
        dao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingDate(testDate);
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);        
        dao.addSighting(testSighting2);
        
        List<Sighting> fromDao = dao.getSightingsBySuperhuman(testHero.getSuperhumanId());
        assertEquals(2, fromDao.size());
    }
}
