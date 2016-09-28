/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stefan
 */
public class DayInWeekTest {
    
    public DayInWeekTest() {
    }
     enum testEnum
        {
            SUN, MON, TUE, WED, THU, FRI, SAT
        }
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }  

    /**
     * Test of valueOf method, of class DayInWeek.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "SUN";
        DayInWeek expResult = DayInWeek.SUN;
        DayInWeek result = DayInWeek.valueOf(name);
        assertEquals(expResult, result);
    }
    
}
