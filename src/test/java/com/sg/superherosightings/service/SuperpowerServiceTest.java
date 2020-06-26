/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import com.sg.superherosightings.model.Superpower;
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
public class SuperpowerServiceTest {
    
    private SuperpowerService service;
    Superpower validPower = new Superpower();
    Superpower invalidPower = new Superpower();
    
    public SuperpowerServiceTest() {
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
        service = ctx.getBean("superpowerService", SuperpowerService.class);
        
        validPower.setSuperpowerDescription("Okay power");
        
        List<Superpower> superpowers = service.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            service.deleteSuperpower(currentPower.getSuperpowerId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addSuperpower method, of class SuperpowerService.
     */
    @Test
    public void testAddGetSuperpowerValid() throws Exception {
        service.addSuperpower(validPower);
        Superpower fromDao = service.getSuperpowerById(validPower.getSuperpowerId());
        assertEquals(fromDao, validPower);
    }
    
    @Test
    public void testAddGetSuperpowerInValidNullDescription() throws Exception {
        try {
            service.addSuperpower(invalidPower);
            fail("Expected SuperpowerValidationException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testAddGetSuperpowerInValidTooLongDescription() throws Exception {
        invalidPower.setSuperpowerDescription("This power will be too longgggg");
        try {
            service.addSuperpower(invalidPower);
            fail("Expected SuperpowerValidationException not thrown.");
        } catch (Exception e) {
            return;
        }
    }
    
    @Test
    public void testAddGetSuperpowerInValidTooShortDescription() throws Exception {
        invalidPower.setSuperpowerDescription("");
        try {
            service.addSuperpower(invalidPower);
            fail("Expected SuperpowerValidationException not thrown.");
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of updateSuperpower method, of class SuperpowerService.
     */
    @Test
    public void testUpdateSuperpowerWithValid() throws Exception {
        service.addSuperpower(validPower);
        Superpower fromDao = service.getSuperpowerById(validPower.getSuperpowerId());
        assertEquals(fromDao, validPower);
        Superpower validPower2 = new Superpower();
        
        validPower2.setSuperpowerDescription("Second okay power");
        validPower2.setSuperpowerId(validPower.getSuperpowerId());
        service.updateSuperpower(validPower2);
        fromDao = service.getSuperpowerById(validPower2.getSuperpowerId());
        assertEquals(fromDao, validPower2);        
    }
    /**
     * Test of updateSuperpower method, of class SuperpowerService.
     */
    @Test
    public void testUpdateSuperpowerWithInValid() throws Exception {
        service.addSuperpower(validPower);
        Superpower fromDao = service.getSuperpowerById(validPower.getSuperpowerId());
        assertEquals(fromDao, validPower);
        invalidPower.setSuperpowerId(validPower.getSuperpowerId());
        
        try {
            service.updateSuperpower(invalidPower);
            fail("Expected SuperpowerValidationException not thrown.");
        } catch (Exception e) {
            fromDao = service.getSuperpowerById(validPower.getSuperpowerId());
            assertEquals(fromDao, validPower);
        }  
    }

    /**
     * Test of deleteSuperpower method, of class SuperpowerService.
     */
    @Test
    public void testDeleteSuperpower() throws Exception {
        service.addSuperpower(validPower);
        Superpower fromDao = service.getSuperpowerById(validPower.getSuperpowerId());
        assertEquals(fromDao, validPower);
        
        service.deleteSuperpower(validPower.getSuperpowerId());
        assertNull(service.getSuperpowerById(validPower.getSuperpowerId()));
    }

    /**
     * Test of getAllSuperpowers method, of class SuperpowerService.
     */
    @Test
    public void testGetAllSuperpowers() throws Exception {
        List<Superpower> fromDao = service.getAllSuperpowers();
        assertEquals(0, fromDao.size());
        service.addSuperpower(validPower);
        fromDao = service.getAllSuperpowers();
        assertEquals(1, fromDao.size());
    }   
}
