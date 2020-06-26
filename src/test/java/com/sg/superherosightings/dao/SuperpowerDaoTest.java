/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Superpower;
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
public class SuperpowerDaoTest {
    SuperpowerDao dao;
    Superpower testPower = new Superpower();
    
    public SuperpowerDaoTest() {
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
        dao = ctx.getBean("superpowerDao", SuperpowerDao.class);
        
        /* Delete all existing Superpower database rows, if any, and initialize
        values for the testPower. */
        List<Superpower> superpowers = dao.getAllSuperpowers();
        for (Superpower currentPower : superpowers) {
            dao.deleteSuperpower(currentPower.getSuperpowerId());
        }
        testPower.setSuperpowerDescription("Super unit testing");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addSuperpower method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testAddGetSuperpower() {
//        Superpower testPower = new Superpower();
//        testPower.setSuperpowerDescription("Super unit testing");
        dao.addSuperpower(testPower);
        
        Superpower fromDao = dao.getSuperpowerById(testPower.getSuperpowerId());
        assertEquals(fromDao, testPower);
    }

    /**
     * Test of deleteSuperpower method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testDeleteSuperpower() {
//        Superpower testPower = new Superpower();
//        testPower.setSuperpowerDescription("Super unit testing");
        dao.addSuperpower(testPower);
        
        Superpower fromDao = dao.getSuperpowerById(testPower.getSuperpowerId());
        assertEquals(fromDao, testPower);
        
        dao.deleteSuperpower(testPower.getSuperpowerId());
        assertNull(dao.getSuperpowerById(testPower.getSuperpowerId()));
    }

    /**
     * Test of updateSuperpower method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testUpdateSuperpower() {
//        Superpower testPower = new Superpower();
//        testPower.setSuperpowerDescription("Super unit testing");
        dao.addSuperpower(testPower);
        
        Superpower fromDao = dao.getSuperpowerById(testPower.getSuperpowerId());
        assertEquals(fromDao, testPower);
        
        Superpower revisedPower = new Superpower();
        revisedPower.setSuperpowerDescription("Extraordinary coding speed");
        revisedPower.setSuperpowerId(testPower.getSuperpowerId());
        dao.updateSuperpower(revisedPower);
        
        fromDao = dao.getSuperpowerById(revisedPower.getSuperpowerId());
        assertEquals(fromDao, revisedPower);
        assertEquals(fromDao.getSuperpowerDescription(), revisedPower.getSuperpowerDescription());
    }

    /**
     * Test of getAllSuperpowers method, of class SuperHeroSightingsDao.
     */
    @Test
    public void testGetAllSuperpowers() {
        dao.addSuperpower(testPower);
        
        List<Superpower> powers = dao.getAllSuperpowers();
        assertEquals(1, powers.size());
    }
}
