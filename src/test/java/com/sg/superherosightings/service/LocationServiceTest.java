/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class LocationServiceTest {
    
    private LocationService service;
    private OrganizationService orgService;
    Location validLoc = new Location();
    Location invalidLoc = new Location();
    Organization testOrg = new Organization();
    
    public LocationServiceTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        service = ctx.getBean("locationService", LocationService.class);
        orgService = ctx.getBean("organizationService", OrganizationService.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        validLoc.setLocationName("Coder's Lair");
        validLoc.setLocationDescription("Where supercoders converge");
        validLoc.setStreet("123 test rd");
        validLoc.setCity("Somewhereville");
        validLoc.setState("ST");
        validLoc.setZip("99999");
        validLoc.setCountry("US");
        validLoc.setLatitude(new BigDecimal("50.25"));
        validLoc.setLongitude(new BigDecimal("100.25"));
        
        List<Organization> orgs = orgService.getAllOrganizations();
        for (Organization org : orgs) {
            orgService.deleteOrganization(org.getOrganizationId());
        }
        
        List<Location> locations = service.getAllLocations();
        for(Location loc : locations) {
            service.deleteLocation(loc.getLocationId());
        }
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
        
        List<Location> locs = service.getAllLocations();
        for (Location loc : locs) {
            service.deleteLocation(loc.getLocationId());
        }
    }

    /**
     * Test of addLocation method, of class LocationService.
     */
    @Test
    public void testAddGetValidLocation() throws Exception {
        service.addLocation(validLoc);
        Location fromDao = service.getLocationById(validLoc.getLocationId());
        assertEquals(fromDao.getLocationName(), validLoc.getLocationName());
    }
    
    @Test
    public void testAddGetLocationNullData() throws Exception {
        try {
            service.addLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
    @Test
    public void testAddGetLocationBadCoordinates() throws Exception {
        invalidLoc.setLocationName("Coder's Lair");
        invalidLoc.setLocationDescription("Where supercoders converge");
        invalidLoc.setStreet("123 test rd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("5000.25"));
        invalidLoc.setLongitude(new BigDecimal("1000.25"));
        try {
            service.addLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
    @Test
    public void testAddGetLocationBadName() throws Exception {
        invalidLoc.setLocationName("TOOOOOOOOOOOOOOOOOOLLOOOOOOOONNNNNNGGGGGGGG");
        invalidLoc.setLocationDescription("Where supercoders converge");
        invalidLoc.setStreet("123 test rd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("50.25"));
        invalidLoc.setLongitude(new BigDecimal("100.25"));
        try {
            service.addLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
    @Test
    public void testAddGetLocationBadDesc() throws Exception {
        invalidLoc.setLocationName("Coder's Lair");
        invalidLoc.setLocationDescription("Where supercoders convergeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        invalidLoc.setStreet("123 test rd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("50.25"));
        invalidLoc.setLongitude(new BigDecimal("100.25"));
        try {
            service.addLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
        @Test
    public void testAddGetLocationBadStreet() throws Exception {
        invalidLoc.setLocationName("Coder's Lair");
        invalidLoc.setLocationDescription("Where supercoders converge");
        invalidLoc.setStreet("123 test rddddddddddddddddddddddddddddddddddddd"
                + "ddddddddddddddddddddddddddddddddddddddddddddddddddd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("50.25"));
        invalidLoc.setLongitude(new BigDecimal("100.25"));
        try {
            service.addLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }

    /**
     * Test of deleteLocation method, of class LocationService.
     */
    @Test
    public void testDeleteLocation() throws Exception {
        service.addLocation(validLoc);
        Location fromDao = service.getLocationById(validLoc.getLocationId());
        assertEquals(fromDao.getLocationName(), validLoc.getLocationName());
        service.deleteLocation(validLoc.getLocationId());
        assertNull(service.getLocationById(validLoc.getLocationId()));
    }

    @Test
    public void testUpdateGetValidLocation() throws Exception {
        service.addLocation(validLoc);
        Location revisedLoc = new Location();
        revisedLoc.setLocationName("Coder's Other Lair");
        revisedLoc.setLocationDescription("Where supercoders converge");
        revisedLoc.setStreet("123 test rd");
        revisedLoc.setCity("Somewhereville");
        revisedLoc.setState("ST");
        revisedLoc.setZip("99999");
        revisedLoc.setCountry("US");
        revisedLoc.setLatitude(new BigDecimal("50.25"));
        revisedLoc.setLongitude(new BigDecimal("100.25"));
        revisedLoc.setLocationId(validLoc.getLocationId());
        service.updateLocation(revisedLoc);
        Location fromDao = service.getLocationById(revisedLoc.getLocationId());
        assertEquals(fromDao.getLocationName(), revisedLoc.getLocationName());
    }
    
    @Test
    public void testUpdateLocationNullData() throws Exception {
        service.addLocation(validLoc);
        try {
            invalidLoc.setLocationId(validLoc.getLocationId());
            service.updateLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
    @Test
    public void testUpdateLocationBadCoordinates() throws Exception {
        invalidLoc.setLocationName("Coder's Lair");
        invalidLoc.setLocationDescription("Where supercoders converge");
        invalidLoc.setStreet("123 test rd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("5000.25"));
        invalidLoc.setLongitude(new BigDecimal("1000.25"));
        service.addLocation(validLoc);
        try {
            invalidLoc.setLocationId(validLoc.getLocationId());
            service.updateLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
    @Test
    public void testUpdateLocationBadName() throws Exception {
        invalidLoc.setLocationName("TOOOOOOOOOOOOOOOOOOLLOOOOOOOONNNNNNGGGGGGGG");
        invalidLoc.setLocationDescription("Where supercoders converge");
        invalidLoc.setStreet("123 test rd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("50.25"));
        invalidLoc.setLongitude(new BigDecimal("100.25"));
        service.addLocation(validLoc);
        try {
            invalidLoc.setLocationId(validLoc.getLocationId());
            service.updateLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
    @Test
    public void testUpdateLocationBadDesc() throws Exception {
        invalidLoc.setLocationName("Coder's Lair");
        invalidLoc.setLocationDescription("Where supercoders convergeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
                + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        invalidLoc.setStreet("123 test rd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("50.25"));
        invalidLoc.setLongitude(new BigDecimal("100.25"));
        service.addLocation(validLoc);
        try {
            invalidLoc.setLocationId(validLoc.getLocationId());
            service.updateLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    
    @Test
    public void testUpdateLocationBadStreet() throws Exception {
        invalidLoc.setLocationName("Coder's Lair");
        invalidLoc.setLocationDescription("Where supercoders converge");
        invalidLoc.setStreet("123 test rddddddddddddddddddddddddddddddddddddd"
                + "ddddddddddddddddddddddddddddddddddddddddddddddddddd");
        invalidLoc.setCity("Somewhereville");
        invalidLoc.setState("ST");
        invalidLoc.setZip("99999");
        invalidLoc.setCountry("US");
        invalidLoc.setLatitude(new BigDecimal("50.25"));
        invalidLoc.setLongitude(new BigDecimal("100.25"));
        service.addLocation(validLoc);
        try {
            invalidLoc.setLocationId(validLoc.getLocationId());
            service.updateLocation(invalidLoc);
            fail("Expected LocationInvalidDataException not thrown.");
        } catch (Exception e) {
            return;
        }        
    }
    /**
     * Test of getAllLocations method, of class LocationService.
     */
    @Test
    public void testGetAllLocations() throws Exception {
        service.addLocation(validLoc);
        List<Location> fromDao = service.getAllLocations();
        assertEquals(1, fromDao.size());
    }
}
