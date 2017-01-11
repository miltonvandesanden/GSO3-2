/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import fontys.util.NumberDoesntExistException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BankTest
{    
    Bank instance;
    
    public BankTest(){}
    
    @BeforeClass
    public static void setUpClass(){}
    
    @AfterClass
    public static void tearDownClass(){}
    
    @Before
    public void setUp()
    {
        try {
            instance = new Bank("Rabobank");
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openRekening method, of class Bank.
     */
    @Test
    public void testOpenRekeningGeenNaam() {
        try {
            System.out.println("testOpenRekeningGeenNaam");
            String name = "";
            String city = "Waspik";
            int expResult = -1;
            int result = instance.openRekening(name, city);
            assertEquals(expResult, result);
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testOpenRekeningGeenStad() {
        try {
            System.out.println("testOpenRekeningGeenStad");
            String name = "Hans";
            String city = "";
            int expResult = -1;
            int result = instance.openRekening(name, city);
            assertEquals(expResult, result);
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testOpenRekeningStadEnNaam() {
        try {
            System.out.println("testOpenRekeningStadEnNaam");
            String name = "Hans";
            String city = "Waspik";
            int expResult = 100000000;
            int result = instance.openRekening(name, city);
            assertEquals(expResult, result);
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Test of getRekening method, of class Bank.
     */
    @Test
    public void testGetRekening() {
        try {
            System.out.println("testGetRekening");
            
            int rekeningnr = instance.openRekening("Hanse", "Waspik");
            IRekening result = instance.getRekening(rekeningnr);
            
            
            IRekening expResult = new Rekening(100000000, new Klant("Hans", "Waspik"), Money.EURO);
            
            assertEquals(expResult, result);
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of maakOver method, of class Bank.
     * @throws java.lang.Exception
     */
    ///TODO: this
    @Test
    (expected = NumberDoesntExistException.class)
    public void testMaakOver1() throws Exception
    {
        System.out.println("testMaakOver");
        
        int source = 300;
        int destination = instance.openRekening("Klant1", "Tilburg");
        Money money = new Money(100, Money.EURO);
        
        instance.maakOver(source, destination, money);
    }
    
    /**
     * Test of maakOver method, of class Bank.
     * @throws java.lang.Exception
     */
    ///TODO: this
    @Test
    (expected = NumberDoesntExistException.class)
    public void testMaakOver2() throws Exception
    {
        System.out.println("testMaakOver");
        
        int source = instance.openRekening("Klant1", "Tilburg");
        int destination = 300;
        Money money = new Money(100, Money.EURO);
        
        instance.maakOver(source, destination, money);
    }
    
    /**
     * Test of maakOver method, of class Bank.
     * @throws java.lang.Exception
     */
    ///TODO: this
    @Test
    (expected = RuntimeException.class)
    public void testMaakOver4() throws Exception
    {
        System.out.println("testMaakOver");
        
        int source = instance.openRekening("Klant1", "Tilburg");
        int destination = source;
        Money money = new Money(100, Money.EURO);
        
        instance.maakOver(source, destination, money);
    }
    
    /**
     * Test of maakOver method, of class Bank.
     * @throws java.lang.Exception
     */
    ///TODO: this
    @Test
    (expected = RuntimeException.class)
    public void testMaakOver5() throws Exception
    {
        System.out.println("testMaakOver");
        
        int source = instance.openRekening("Klant1", "Tilburg");
        int destination = instance.openRekening("Klant2", "Tilburg");
        Money money = new Money(0, Money.EURO);
        
        instance.maakOver(source, destination, money);
    }
    
    /**
     * Test of maakOver method, of class Bank.
     * @throws java.lang.Exception
     */
    ///TODO: this
    @Test
    (expected = RuntimeException.class)
    public void testMaakOver6() throws Exception
    {
        System.out.println("testMaakOver");
        
        int source = instance.openRekening("Klant1", "Tilburg");
        int destination = instance.openRekening("Klant2", "Tilburg");
        Money money = new Money(-400, Money.EURO);
        
        instance.maakOver(source, destination, money);
    }
    
    /**
     * Test of maakOver method, of class Bank.
     * @throws java.lang.Exception
     */
    ///TODO: this
    @Test
    public void testMaakOver7() throws Exception
    {
        System.out.println("testMaakOver");
        
        int source = instance.openRekening("Klant1", "Tilburg");
        int destination = instance.openRekening("Klant2", "Tilburg");
        instance.maakOver(source, destination, new Money(9999, Money.EURO));
        Money money = new Money(400, Money.EURO);
        
        assertFalse(instance.maakOver(source, destination, money));
    }
    
    /**
     * Test of maakOver method, of class Bank.
     * @throws java.lang.Exception
     */
    ///TODO: this
    @Test
    public void testMaakOver8() throws Exception
    {
        System.out.println("testMaakOver");
        
        int source = instance.openRekening("Klant1", "Tilburg");
        int destination = instance.openRekening("Klant2", "Tilburg");
        Money money = new Money(400, Money.EURO);
        
        assertTrue(instance.maakOver(source, destination, money));
    }
    
    /**
     * Test of getName method, of class Bank.
     */
    @Test
    public void testGetName() {
        try {
            System.out.println("testGetName");
            
            String expResult = "Rabobank";
            String result = instance.getName();
            assertEquals(expResult, result);
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
