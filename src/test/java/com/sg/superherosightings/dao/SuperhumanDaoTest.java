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
import com.sg.superherosightings.service.LocationService;
import com.sg.superherosightings.service.OrganizationService;
import com.sg.superherosightings.service.SuperpowerService;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class SuperhumanDaoTest {
    
    private SuperhumanDao heroDao;
    private SuperpowerService powerService;
    private LocationService locService;
    private OrganizationService orgService;
    
    Superhuman testHero = new Superhuman();
    Location testLoc = new Location();
    Organization testOrg = new Organization();
    Superpower testPower = new Superpower();
    
    public SuperhumanDaoTest() {
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
        heroDao = ctx.getBean("superhumanDao", SuperhumanDao.class);
        powerService = ctx.getBean("superpowerService", SuperpowerService.class);
        locService = ctx.getBean("locationService", LocationService.class);
        orgService = ctx.getBean("organizationService", OrganizationService.class);
        
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
                
        List<Superhuman> heroes = heroDao.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroDao.deleteSuperhuman(hero.getSuperhumanId());
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
                
        List<Superhuman> heroes = heroDao.getAllSuperhumans();
        for (Superhuman hero : heroes) {
            heroDao.deleteSuperhuman(hero.getSuperhumanId());
        }
    }

    /**
     * Test of addSuperhuman method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testAddGetSuperhumanById() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        Superhuman fromDao = heroDao.getSuperhumanById(testHero.getSuperhumanId());
        assertEquals(fromDao.getAlterEgo(), testHero.getAlterEgo());
    }

    /**
     * Test of deleteSuperhuman method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testDeleteSuperhuman() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
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
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
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
        orgService.addOrganization(revisedOrg);
        
        List<Organization> orgs = new ArrayList<>();
        orgs.add(revisedOrg);
        
        Superhuman revisedSuperhuman = new Superhuman();
        revisedSuperhuman.setAlterEgo("Supercoder");
        revisedSuperhuman.setDescription("Faster than a speeding supercomputer");
        revisedSuperhuman.setVillain(true);
        revisedSuperhuman.setOrganizations(orgs);
        revisedSuperhuman.setSuperpowers(powerService.getAllSuperpowers());
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
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        assertEquals(1, heroDao.getAllSuperhumans().size());
        
        Organization revisedOrg = new Organization();
        revisedOrg.setOrganizationName("Brotherhood of Hackers");
        revisedOrg.setOrgDescription("Earth's most deviant devs.");
        revisedOrg.setPhone("5559876543");
        revisedOrg.setEmail("contact@hackerbros.com");
        revisedOrg.setVillain(true);
        revisedOrg.setLocation(testLoc);
        orgService.addOrganization(revisedOrg);
        
        List<Organization> orgs = new ArrayList<>();
        orgs.add(revisedOrg);
        
        Superhuman testSuperhuman2 = new Superhuman();
        testSuperhuman2.setAlterEgo("Supercoder");
        testSuperhuman2.setDescription("Faster than a speeding supercomputer");
        testSuperhuman2.setVillain(true);
        testSuperhuman2.setOrganizations(orgs);
        testSuperhuman2.setSuperpowers(powerService.getAllSuperpowers());
        testSuperhuman2.setSuperhumanId(testHero.getSuperhumanId());
        
        heroDao.addSuperhuman(testSuperhuman2);
        assertEquals(2, heroDao.getAllSuperhumans().size());
    }

    /**
     * Test of getSuperhumansByLocationId method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetSuperhumansByLocationId() {
//        dao.addSuperpower(testPower);
//        dao.addLocation(testLoc);
//        dao.addOrganization(testOrg);
//        testSuperhuman.setSuperpowers(dao.getAllSuperpowers());
//        testSuperhuman.setOrganizations(dao.getAllOrganizations());
//        dao.addSuperhuman(testSuperhuman);
//        List<Superhuman> superhumans = dao.getAllSuperhumans();
//        testSighting.setHeroes(superhumans);
//        
//        dao.addSighting(testSighting);
//        Sighting fromDao = dao.getSightingById(testSighting.getSightingId());
//        assertEquals(fromDao, testSighting);
//        
//        superhumans = dao.getSuperhumansByLocationId(testLoc.getLocationId());
//        assertEquals(superhumans.get(0), testSuperhuman);
//        assertNotNull(superhumans.get(0).getOrganizations());
//        assertNotNull(superhumans.get(0).getSuperpowers());        
    }

    /**
     * Test of getSuperhumansByOrganization method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetSuperhumansByOrganization() throws Exception {
        powerService.addSuperpower(testPower);
        locService.addLocation(testLoc);
        testOrg.setLocation(testLoc);
        orgService.addOrganization(testOrg);
        testHero.setSuperpowers(powerService.getAllSuperpowers());
        testHero.setOrganizations(orgService.getAllOrganizations());
        heroDao.addSuperhuman(testHero);
        
        Organization testOrg2 = new Organization();
        testOrg2.setOrganizationName("Brotherhood of Hackers");
        testOrg2.setOrgDescription("Earth's most deviant devs.");
        testOrg2.setPhone("5559876543");
        testOrg2.setEmail("contact@hackerbros.com");
        testOrg2.setVillain(true);
        testOrg2.setLocation(testLoc);
        orgService.addOrganization(testOrg2);
        
        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg2);
        
        Superhuman testSuperhuman2 = new Superhuman();
        testSuperhuman2.setAlterEgo("Captain Hacker");
        testSuperhuman2.setDescription("Deviant dev");
        testSuperhuman2.setVillain(true);
        testSuperhuman2.setOrganizations(orgs);
        testSuperhuman2.setSuperpowers(powerService.getAllSuperpowers());
        heroDao.addSuperhuman(testSuperhuman2);
        
        List<Superhuman> fromDao = heroDao.getSuperhumansByOrganization(testOrg.getOrganizationId());
        assertEquals(fromDao.get(0).getAlterEgo(), testHero.getAlterEgo());
        assertEquals(fromDao.get(0).getSuperhumanId(), testHero.getSuperhumanId());
        
        fromDao = heroDao.getSuperhumansByOrganization(testOrg2.getOrganizationId());
        assertEquals(fromDao.get(0).getAlterEgo(), testSuperhuman2.getAlterEgo());
        assertEquals(fromDao.get(0).getDescription(), testSuperhuman2.getDescription());
    }
}
