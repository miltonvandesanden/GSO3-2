/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.Appointment;

import fontys.time.ITimeSpan;
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
public class AppointmentTest {
    
    private Appointment instance;
    private Contact contact;
    public AppointmentTest() {
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
        instance = new Appointment("Hallo" , new TimeSpan(new Time(2016, 9, 28, 16, 10), new Time(2016, 9, 28, 16, 20)));
        contact = new Contact("Harry");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of appointment constructor, of class Appointment
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAppointment()
    {
        System.out.println("appointment");
        Appointment appointment = new Appointment(null, new TimeSpan(new Time(2016, 9, 28, 16, 10), new Time(2016, 9, 28, 16, 20)));
    }
    
    /**
     * Test of appointment constructor, of class Appointment
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAppointment2()
    {
        System.out.println("appointment");
        Appointment appointment = new Appointment("", new TimeSpan(new Time(2016, 9, 28, 16, 10), new Time(2016, 9, 28, 16, 20)));
    }
    
    /**
     * Test of appointment constructor, of class Appointment
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAppointment3()
    {
        System.out.println("appointment");
        Appointment appointment = new Appointment("hi", null);
    }


    
    /**
     * Test of getSubject method, of class Appointment.
     */
    @Test
    public void testGetSubject() {
        System.out.println("getSubject");
        String expResult = "Hallo";
        String result = instance.getSubject();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTimeSpan method, of class Appointment.
     */
    @Test
    public void testGetTimeSpan() {
        System.out.println("getTimeSpan");
        ITimeSpan expResult = new TimeSpan(new Time(2016, 9, 28, 16, 10), new Time(2016, 9, 28, 16, 20));
        ITimeSpan result = instance.getTimeSpan();
        assertEquals(expResult, result);
    }

    /**
     * Test of invitees method, of class Appointment.
     */
    @Test
    public void testInvitees() {
        System.out.println("invitees");
        instance.addContact(contact);
        int expResult = 1;
        int result = 0;
        while(instance.invitees().hasNext())
        {
            result++;
            instance.removeContact(contact);
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of addContact method, of class Appointment.
     */
    @Test
    public void testAddContact() {
        System.out.println("addContact");
        boolean result = instance.addContact(contact);
        assertTrue(result);
    }
    
    /**
     * Test of addContact method, of class Appointment.
     */
    @Test
    public void testAddContact2() {
        System.out.println("addContact");
        contact.addAppointment(new Appointment("Hallo" , new TimeSpan(new Time(2016, 9, 28, 16, 11), new Time(2016, 9, 28, 16, 21))));
        boolean result = instance.addContact(contact);
        assertFalse(result);
    }    

    /**
     * Test of removeContact method, of class Appointment.
     */
    @Test
    public void testRemoveContact() {
        System.out.println("removeContact");
        instance.addContact(contact);
        
        int expectedResult = 0;
        
        instance.removeContact(contact);
        
        int result = 0;
        while(instance.invitees().hasNext())
        {
            result++;
            instance.invitees().next();
        }
        
        assertEquals(expectedResult, result);
    }
    
}
