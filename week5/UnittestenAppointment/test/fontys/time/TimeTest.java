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
public class TimeTest {
     Time instance;
    public TimeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        instance = new Time(2016, 9, 25, 17, 34);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException()
    {
        instance = new Time(2016, 13, 1, 1, 34);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException1_1()
    {
        instance = new Time(2016, 0, 1, 1, 34);
    }
    
    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException2()
    {
        instance = new Time(2016, 10, 32, 17, 34);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException2_1()
    {
        instance = new Time(2016, 10, -1, 17, 34);
    }

    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException3()
    {
        instance = new Time(2016, 10, 1, 24, 34);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException3_1()
    {
        instance = new Time(2016, 10, 1, -1, 34);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException4()
    {
        instance = new Time(2016, 10, 1, 23, 60);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testIllegalArgumentsException4_1()
    {
        instance = new Time(2016, 10, 1, 23, -1);
    }

    /**
     * Test of getDayInWeek method, of class Time.
     */
    @Test
    public void testGetDayInWeek() {
        System.out.println("getDayInWeek");
        DayInWeek expResult = DayInWeek.SUN;
        DayInWeek result = instance.getDayInWeek();
        assertEquals(expResult, result);
        
        instance = new Time(2016, 9, 26, 17, 34);
        expResult = DayInWeek.MON;
        result = instance.getDayInWeek();
        assertEquals(expResult, result);
        
        instance = new Time(2016, 9, 27, 17, 34);
        expResult = DayInWeek.TUE;
        result = instance.getDayInWeek();
        assertEquals(expResult, result);
        
        instance = new Time(2016, 9, 28, 17, 34);
        expResult = DayInWeek.WED;
        result = instance.getDayInWeek();
        assertEquals(expResult, result);
        
        instance = new Time(2016, 9, 29, 17, 34);
        expResult = DayInWeek.THU;
        result = instance.getDayInWeek();
        assertEquals(expResult, result);
        
        instance = new Time(2016, 9, 30, 17, 34);
        expResult = DayInWeek.FRI;
        result = instance.getDayInWeek();
        assertEquals(expResult, result);
        
        instance = new Time(2016, 10, 1, 17, 34);
        expResult = DayInWeek.SAT;
        result = instance.getDayInWeek();
        assertEquals(expResult, result);
    }

    /**
     * Test of getYear method, of class Time.
     */
    @Test
    public void testGetYear() {
        System.out.println("getYear");
        int expResult = 2016;
        int result = instance.getYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMonth method, of class Time.
     */
    @Test
    public void testGetMonth() {
        System.out.println("getMonth");
        int expResult = 9;
        int result = instance.getMonth();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDay method, of class Time.
     */
    @Test
    public void testGetDay() {
        System.out.println("getDay");
        int expResult = 25;
        int result = instance.getDay();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHours method, of class Time.
     */
    @Test
    public void testGetHours() {
        System.out.println("getHours");
        int expResult = 17;
        int result = instance.getHours();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMinutes method, of class Time.
     */
    @Test
    public void testGetMinutes() {
        System.out.println("getMinutes");
        int expResult = 34;
        int result = instance.getMinutes();
        assertEquals(expResult, result);
    }

    /**
     * Test of plus method, of class Time.
     */
    @Test
    public void testPlus() {
        System.out.println("plus");
        int minutes = 5;
        int expResult =  39;
        int result = (instance.plus(minutes)).getMinutes();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareTo method, of class Time.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        ITime t = new Time(2016, 9, 25, 17, 34);
        int expResult = 0;
        int result = instance.compareTo(t);
        assertEquals(expResult, result);
    }

    /**
     * Test of difference method, of class Time.
     */
    @Test
    public void testDifference() {
        System.out.println("difference");
        ITime time = new Time(2016, 9, 25, 17, 35);
        int expResult = 1;
        int result = instance.difference(time);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Time.
     */    
    @Test
    public void testEquals()
    {
        System.out.println("equals");
        ITime time = new Time(2016, 9, 25, 17, 34);
        boolean expResult = true;
        boolean result = instance.equals(time);
        assertEquals(expResult, result);
    }    
    
        /**
     * Test of equals method, of class Time.
     */    
    @Test
    public void testEquals1()
    {
        System.out.println("equals");
        ITime time = new Time(2017, 9, 25, 17, 34);
        boolean result = instance.equals(time);
        assertFalse(result);
    }    

    /**
     * Test of equals method, of class Time.
     */    
    @Test
    public void testEquals2()
    {
        System.out.println("equals");
        ITime time = new Time(2016, 10, 25, 17, 34);
        boolean result = instance.equals(time);
        assertFalse(result);
    }    

    /**
     * Test of equals method, of class Time.
     */    
    @Test
    public void testEquals3()
    {
        System.out.println("equals");
        ITime time = new Time(2016, 9, 26, 17, 34);
        boolean result = instance.equals(time);
        assertFalse(result);
    }    

    /**
     * Test of equals method, of class Time.
     */    
    @Test
    public void testEquals4()
    {
        System.out.println("equals");
        ITime time = new Time(2016, 9, 25, 18, 34);
        boolean result = instance.equals(time);
        assertFalse(result);
    }    

    /**
     * Test of equals method, of class Time.
     */    
    @Test
    public void testEquals5()
    {
        System.out.println("equals");
        ITime time = new Time(2016, 9, 25, 17, 35);
        boolean result = instance.equals(time);
        assertFalse(result);
    }    

}