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
public class TimeSpan2Test
{
    private TimeSpan2 instance;
    
    public TimeSpan2Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()
    {
        instance = new TimeSpan2(new Time(2016, 9, 25, 17, 34), new Time(2016, 10, 29, 17, 36));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of timeSpan2 constructor of class TimeSpan2
     */
    @Test(expected=IllegalArgumentException.class)
    public void testTimeSpan2()
    {
        System.out.println("timeSpan2");
        instance = new TimeSpan2(new Time(2, 2, 2, 2, 2), new Time(1, 1, 1, 1, 1));
    }
    
    
    /**
     * Test of getBeginTime method, of class TimeSpan2.
     */
    @Test
    public void testGetBeginTime() {
        System.out.println("getBeginTime");
        ITime expResult = new Time(2016, 9, 25, 17, 34);
        ITime result = instance.getBeginTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEndTime method, of class TimeSpan2.
     */
    @Test
    public void testGetEndTime() {
        System.out.println("getEndTime");
        ITime expResult = new Time(2016, 10, 29, 17, 36);
        ITime result = instance.getEndTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of length method, of class TimeSpan2.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        instance = new TimeSpan2(new Time(10, 5, 2, 10, 0), new Time(10, 5, 2, 10, 1));
        int expResult = 1;
        int result = instance.length();
        assertEquals(expResult, result);
    }

    /**
     * Test of setBeginTime method, of class TimeSpan2.
     */
    @Test
    public void testSetBeginTime() {
        System.out.println("setBeginTime");
        ITime beginTime = new Time(2016, 9, 25, 17, 35);
        instance.setBeginTime(beginTime);
        assertEquals(beginTime, instance.getBeginTime());
    }
    
    /**
     * Test of setBeginTime method, of class TimeSpan2.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetBeginTime2() {
        System.out.println("setBeginTime");
        instance = new TimeSpan2(new Time(2, 2, 2, 2, 2), new Time(4, 4, 4, 4, 4));
        ITime beginTime = new Time(5, 5, 5, 5, 5);
        instance.setBeginTime(beginTime);
    }
    
    /**
     * Test of setEndTime method, of class TimeSpan2.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetEndTime3() {
        System.out.println("setBeginTime");
        instance = new TimeSpan2(new Time(2, 2, 2, 2, 2), new Time(4, 4, 4, 4, 4));
        ITime beginTime = new Time(1, 1, 1, 1, 1);
        instance.setEndTime(beginTime);
    }

    /**
     * Test of setEndTime method, of class TimeSpan2.
     */
    @Test
    public void testSetEndTime() {
        System.out.println("setEndTime");
        ITime endTime = new Time(2016, 10, 29, 17, 37);
        instance.setEndTime(endTime);
        assertEquals(endTime, instance.getEndTime());
    }

    /**
     * Test of move method, of class TimeSpan2.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        int minutes = 5;
        TimeSpan2 expectedResult = new TimeSpan2(new Time(2016, 9, 25, 17, 39), new Time(2016, 10, 29, 17, 41));
        instance.move(minutes);
        assertEquals(expectedResult, instance);
    }
    
        /**
     * Test of move method, of class TimeSpan2.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testMove2()
    {
        System.out.println("move");
        int minutes = -1;
        instance.move(minutes);
    }

    /**
     * Test of changeLengthWith method, of class TimeSpan2.
     */
    @Test
    public void testChangeLengthWith() {
        System.out.println("changeLengthWith");
        int minutes = 5;
        TimeSpan2 expectedResult = new TimeSpan2(new Time(2016, 9, 25, 17, 34), new Time(2016, 10, 29, 17, 41));
        instance.changeLengthWith(minutes);
        assertEquals(expectedResult, instance);
    }

    
    /**
     * Test of changeLengthWith method, of class TimeSpan2.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testChangeLengthWith2() {
        System.out.println("changeLengthWith");
        int minutes = -1;
        TimeSpan2 expectedResult = new TimeSpan2(new Time(2016, 9, 25, 17, 34), new Time(2016, 10, 29, 17, 41));
        instance.changeLengthWith(minutes);
        assertEquals(expectedResult, instance);
    }

    /**
     * Test of isPartOf method, of class TimeSpan2.
     */
    @Test
    public void testIsPartOf() {
        System.out.println("isPartOf");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 29, 17, 20), new Time(2016, 10, 29, 17, 50));
        instance = new TimeSpan2(new Time(2016, 10, 29, 17, 30), new Time(2016, 10, 29, 17, 40));
        boolean expResult = true;
        boolean result = instance.isPartOf(timeSpan2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isPartOf method, of class TimeSpan2.
     */
    @Test
    public void testIsPartOf2() {
        System.out.println("isPartOf");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 29, 17, 20), new Time(2016, 10, 29, 17, 50));
        instance = new TimeSpan2(new Time(2016, 10, 29, 17, 10), new Time(2016, 10, 29, 17, 40));
        boolean expResult = false;
        boolean result = instance.isPartOf(timeSpan2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isPartOf method, of class TimeSpan2.
     */
    @Test
    public void testIsPartOf3() {
        System.out.println("isPartOf");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 29, 17, 20), new Time(2016, 10, 29, 17, 50));
        instance = new TimeSpan2(new Time(2016, 10, 29, 17, 20), new Time(2016, 10, 29, 17, 59));
        boolean expResult = false;
        boolean result = instance.isPartOf(timeSpan2);
        assertEquals(expResult, result);
    }


    /**
     * Test of unionWith method, of class TimeSpan2.
     */
    @Test
    public void testUnionWith() {
        System.out.println("unionWith");
        instance = new TimeSpan2(new Time(2016, 10, 25, 17, 20), new Time(2016, 10, 25, 17, 50));
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 25, 17, 10), new Time(2016, 10, 25, 17, 40));
        ITimeSpan expResult = new TimeSpan2(new Time(2016, 10, 25, 17, 10), new Time(2016, 10, 25, 17, 50));
        ITimeSpan result = instance.unionWith(timeSpan2);
        assertEquals(expResult, result);
    }

        /**
     * Test of unionWith method, of class TimeSpan2.
     */
    @Test
    public void testUnionWith2() {
        System.out.println("unionWith");
        instance = new TimeSpan2(new Time(2016, 10, 25, 17, 20), new Time(2016, 10, 25, 17, 50));
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 25, 17, 30), new Time(2016, 10, 25, 17, 40));
        ITimeSpan expResult = new TimeSpan2(new Time(2016, 10, 25, 17, 20), new Time(2016, 10, 25, 17, 50));
        ITimeSpan result = instance.unionWith(timeSpan2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of unionWith method, of class TimeSpan2.
     */
    @Test
    public void testUnionWith3() {
        System.out.println("unionWith");
        instance = new TimeSpan2(new Time(2016, 10, 25, 17, 20), new Time(2016, 10, 25, 17, 50));
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 25, 17, 10), new Time(2016, 10, 25, 17, 59));
        ITimeSpan expResult = new TimeSpan2(new Time(2016, 10, 25, 17, 10), new Time(2016, 10, 25, 17, 59));
        ITimeSpan result = instance.unionWith(timeSpan2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of unionWith method, of class TimeSpan2.
     */
    @Test
    public void testUnionWith4() {
        System.out.println("unionWith");
        instance = new TimeSpan2(new Time(2016, 10, 25, 17, 20), new Time(2016, 10, 25, 17, 50));
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 25, 16, 10), new Time(2016, 10, 25, 16, 59));
        ITimeSpan expResult = null;
        ITimeSpan result = instance.unionWith(timeSpan2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of unionWith method, of class TimeSpan2.
     */
    @Test
    public void testUnionWith5() {
        System.out.println("unionWith");
        instance = new TimeSpan2(new Time(2016, 10, 25, 17, 20), new Time(2016, 10, 25, 17, 50));
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 25, 18, 10), new Time(2016, 10, 25, 18, 59));
        ITimeSpan expResult = null;
        ITimeSpan result = instance.unionWith(timeSpan2);
        assertEquals(expResult, result);
    }

    /**
     * Test of intersectionWith method, of class TimeSpan2.
     */
    @Test
    public void testIntersectionWith() {
        System.out.println("intersectionWith");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 25, 17, 22), new Time(2016, 10, 29, 17, 35));
        instance = new TimeSpan2(new Time(2016, 10, 25, 17, 20), new Time(2016, 10, 29, 17, 30));
        ITimeSpan expResult = new TimeSpan2(new Time(2016, 10, 25, 17, 22), new Time(2016, 10, 29, 17, 30));;
        ITimeSpan result = instance.intersectionWith(timeSpan2);
        assertEquals(expResult, result);
    }

    /**
     * Test of intersectionWith method, of class TimeSpan2.
     */
    @Test
    public void testIntersectionWith2() {
        System.out.println("intersectionWith");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 22, 17, 22), new Time(2016, 10, 29, 17, 35));
        instance = new TimeSpan2(new Time(2016, 10, 22, 17, 23), new Time(2016, 10, 29, 17, 30));
        ITimeSpan expResult = new TimeSpan2(new Time(2016, 10, 22, 17, 23), new Time(2016, 10, 29, 17, 30));;
        ITimeSpan result = instance.intersectionWith(timeSpan2);
        assertEquals(expResult, result);
    }    

    /**
     * Test of intersectionWith method, of class TimeSpan2.
     */
    @Test
    public void testIntersectionWith3() {
        System.out.println("intersectionWith");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 22, 17, 22), new Time(2016, 10, 29, 17, 35));
        instance = new TimeSpan2(new Time(2016, 10, 22, 17, 20), new Time(2016, 10, 29, 17, 36));
        ITimeSpan expResult = new TimeSpan2(new Time(2016, 10, 22, 17, 22), new Time(2016, 10, 29, 17, 35));
        ITimeSpan result = instance.intersectionWith(timeSpan2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of intersectionWith method, of class TimeSpan2.
     */
    @Test
    public void testIntersectionWith4() {
        System.out.println("intersectionWith");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 10, 22, 17, 22), new Time(2016, 10, 29, 17, 35));
        instance = new TimeSpan2(new Time(2016, 10, 22, 17, 23), new Time(2016, 10, 29, 17, 36));
        ITimeSpan expResult = new TimeSpan2(new Time(2016, 10, 22, 17, 23), new Time(2016, 10, 29, 17, 35));;
        ITimeSpan result = instance.intersectionWith(timeSpan2);
        assertEquals(expResult, result);
    }    
    
    /**
     * Test of equals method, of class TimeSpan2.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 9, 25, 17, 34), new Time(2016, 10, 29, 17, 36));
        boolean result = instance.equals(timeSpan2);
        assertTrue(result);
    }

    /**
     * Test of equals method, of class TimeSpan2.
     */
    @Test
    public void testEquals2() {
        System.out.println("equals");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 9, 25, 17, 35), new Time(2016, 10, 29, 17, 36));
        boolean result = instance.equals(timeSpan2);
        assertFalse(result);
    }

    /**
     * Test of equals method, of class TimeSpan2.
     */
    @Test
    public void testEquals3() {
        System.out.println("equals");
        ITimeSpan timeSpan2 = new TimeSpan2(new Time(2016, 9, 25, 17, 34), new Time(2016, 10, 29, 17, 37));
        boolean result = instance.equals(timeSpan2);
        assertFalse(result);
    }
}
