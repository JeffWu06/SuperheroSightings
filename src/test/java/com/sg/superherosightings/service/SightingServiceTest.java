/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

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
public class SightingServiceTest {
    
    private SightingService service;
    private SuperhumanService heroService;
    private SuperpowerService powerService;
    private LocationService locService;
    private OrganizationService orgService;
    
    Sighting testSighting = new Sighting();
    Superhuman testHero = new Superhuman();
    Location testLoc = new Location();
    Organization testOrg = new Organization();
    Superpower testPower = new Superpower();
    
    public SightingServiceTest() {
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
        locService = ctx.getBean("locationService", LocationService.class);
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        service = ctx.getBean("sightingService", SightingService.class);

        List<Organization> orgs = orgService.getAllOrganizations();
        for (Organization org : orgs) {
            orgService.deleteOrganization(org.getOrganizationId());
        }
        
        List<Superpower> superpowers = powerService.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            powerService.deleteSuperpower(currentPower.getSuperpowerId());
        }
        
        List<Superhuman> heroes = heroService.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroService.deleteSuperhuman(hero.getSuperhumanId());
        }
        
        List<Sighting> sightings = service.getAllSightings();
        for (Sighting sighting : sightings) {
            service.deleteSighting(sighting.getSightingId());
        }
        
        List<Location> locs = locService.getAllLocations();
        for (Location loc : locs) {
            locService.deleteLocation(loc.getLocationId());
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
        List<Organization> orgs = orgService.getAllOrganizations();
        for (Organization org : orgs) {
            orgService.deleteOrganization(org.getOrganizationId());
        }
        
        List<Superpower> superpowers = powerService.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            powerService.deleteSuperpower(currentPower.getSuperpowerId());
        }
        
        List<Superhuman> heroes = heroService.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroService.deleteSuperhuman(hero.getSuperhumanId());
        }
        
        List<Sighting> sightings = service.getAllSightings();
        for (Sighting sighting : sightings) {
            service.deleteSighting(sighting.getSightingId());
        }
        
        List<Location> locs = locService.getAllLocations();
        for (Location loc : locs) {
            locService.deleteLocation(loc.getLocationId());
        }
    }

    /**
     * Test of addSighting method, of class SightingService.
     */
    @Test
    public void testAddGetSightingValid() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
    }
    
    @Test
    public void testAddGetSightingNoHeroesList() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);

        try {
            service.addSighting(testSighting);
            fail("Expected SightingInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testAddGetSightingNoLocation() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setHeroes(heroes);

        try {
            service.addSighting(testSighting);
            fail("Expected SightingInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testAddGetSightingNoDate() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setHeroes(heroes);
        testSighting.setLocation(testLoc);

        try {
            service.addSighting(testSighting);
            fail("Expected SightingInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of deleteSighting method, of class SightingService.
     */
    @Test
    public void testDeleteSighting() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        service.deleteSighting(testSighting.getSightingId());
        assertNull(service.getSightingById(testSighting.getSightingId()));
    }

    /**
     * Test of updateSighting method, of class SightingService.
     */
    @Test
    public void testUpdateSightingValid() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingId(testSighting.getSightingId());
        testSighting2.setSightingDate(LocalDate.of(2020, Month.MARCH, 31));
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);
        
        service.updateSighting(testSighting2);
        fromDao = service.getSightingById(testSighting2.getSightingId());
        assertEquals(fromDao.getSightingDate(), testSighting2.getSightingDate());
    }
    
    @Test
    public void testUpdateSightingBadDate() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingId(testSighting.getSightingId());
        testSighting2.setLocation(testLoc);
        testSighting2.setHeroes(heroes);
        
        try {
            service.updateSighting(testSighting2);
            fail("Expected SightingInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateSightingNoHeroes() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingId(testSighting.getSightingId());
        testSighting2.setSightingDate(LocalDate.of(2020, Month.MARCH, 31));
        testSighting2.setLocation(testLoc);
        
        try {
            service.updateSighting(testSighting2);
            fail("Expected SightingInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testUpdateSightingNoLocation() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setSightingId(testSighting.getSightingId());
        testSighting2.setSightingDate(LocalDate.of(2020, Month.MARCH, 31));
        testSighting2.setHeroes(heroes);
        
        try {
            service.updateSighting(testSighting2);
            fail("Expected SightingInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    /**
     * Test of getAllSightings method, of class SightingService.
     */
    @Test
    public void testGetAllSightings() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        List<Sighting> sightings = service.getAllSightings();
        assertEquals(1, sightings.size());
    }

    /**
     * Test of getSightingsByDate method, of class SightingService.
     */
    @Test
    public void testGetSightingsByDate() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        List<Sighting> sightings = service.getSightingsByDate(testDate);
        assertEquals(1, sightings.size());
        
        sightings = service.getSightingsByDate(testDate.plusDays(1));
        assertEquals(0, sightings.size());
    }

    /**
     * Test of getRecentSightings method, of class SightingService.
     */
    @Test
    public void testGetRecentSightings() throws Exception {
        LocalDate testDate = LocalDate.of(2020,1,1);
        
        powerService.addSuperpower(testPower);
        List<Superpower> powers = new ArrayList<>();
        powers.add(testPower);
        testHero.setSuperpowers(powers);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);
        heroService.addSuperhuman(testHero);
        List<Superhuman> heroes = new ArrayList<>();
        heroes.add(testHero);
        testSighting.setSightingDate(testDate);
        testSighting.setLocation(testLoc);
        testSighting.setHeroes(heroes);

        service.addSighting(testSighting);
        Sighting fromDao = service.getSightingById(testSighting.getSightingId());
        assertEquals(fromDao.getSightingDate(), testDate);
        
        List<Sighting> sightings = service.getRecentSightings();
        assertTrue(sightings.size() <= 10);
    }
}
