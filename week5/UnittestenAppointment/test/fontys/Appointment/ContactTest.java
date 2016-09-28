/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.Appointment;

import fontys.time.Time;
import fontys.time.TimeSpan;
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
public class ContactTest
{
    private Contact instance;
    private Appointment appointment;
    
    public ContactTest() {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        instance = new Contact("Harry");
        appointment = new Appointment("Hallo" , new TimeSpan(new Time(2016, 9, 28, 16, 10), new Time(2016, 9, 28, 16, 20)));
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of contact constructor, of class Contact.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testContact() 
    {
        System.out.println("contact");
        Contact contact = new Contact(null);
    }
    
    /**
     * Test of contact constructor, of class Contact.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testContact2() 
    {
        System.out.println("contact");
        Contact contact = new Contact("");
    }
    
    /**
     * Test of getName method, of class Contact.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "Harry";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of addAppointment method, of class Contact.
     */
    @Test
    public void testAddAppointment() {
        System.out.println("addAppointment");
        boolean result = instance.addAppointment(appointment);
        assertTrue(result);
    }

    /**
     * Test of addAppointment method, of class Contact.
     */
    @Test
    public void testAddAppointment2() {
        System.out.println("addAppointment");
        instance.addAppointment(new Appointment("Hallo" , new TimeSpan(new Time(2016, 9, 28, 16, 11), new Time(2016, 9, 28, 16, 21))));
        boolean result = instance.addAppointment(appointment);
        assertFalse(result);
    }
    
    /**
     * Test of addAppointment method, of class Contact.
     */
    @Test
    public void testAddAppointment3() {
        System.out.println("addAppointment");
        instance.addAppointment(new Appointment("Hallo" , new TimeSpan(new Time(2016, 9, 28, 16, 30), new Time(2016, 9, 28, 16, 40))));
        boolean result = instance.addAppointment(appointment);
        assertTrue(result);
    }
    
    
    /**
     * Test of removeAppointment method, of class Contact.
     */
    @Test
    public void testRemoveAppointment() {
        System.out.println("removeAppointment");
        instance.addAppointment(appointment);
        
        int expectedResult = 0;
        
        instance.removeAppointment(appointment);
        int result = 0;
        
        while(instance.appointments().hasNext())
        {
            result++;
            instance.appointments().next();
        }
        
        assertEquals(expectedResult, result);
    }

    /**
     * Test of appointments method, of class Contact.
     */
    @Test
    public void testAppointments() {
        System.out.println("appointments");
        instance.addAppointment(appointment);
        int expectedResult = 1;
        
        int result = 0;
        
        while(instance.appointments().hasNext())
        {
            result++;
            instance.removeAppointment(appointment);
        }
        
        assertEquals(expectedResult, result);
    }
    
}
