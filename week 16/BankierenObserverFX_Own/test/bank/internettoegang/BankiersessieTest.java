/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import bank.bankieren.IRekening;
import bank.bankieren.Klant;
import bank.bankieren.Money;
import bank.bankieren.Rekening;
import fontys.util.InvalidSessionException;
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
import org.junit.Ignore;

/**
 *
 * @author Stefan
 */
public class BankiersessieTest
{
    private Bankiersessie instance;
    private int sourceRekeningNr;
    private int targetRekeningNr;
    private Money money;
    
    private int time = /*11000;*/ 600000;
    
    public BankiersessieTest(){}
    
    @BeforeClass
    public static void setUpClass(){}
    
    @AfterClass
    public static void tearDownClass(){}
    
    @Before
    public void setUp()
    {
        try
        {
            Bank bank = new Bank("Rabobank");
            sourceRekeningNr = bank.openRekening("Milton", "Tilburg");
            targetRekeningNr = bank.openRekening("Comrade Stefano", "Stalingrad");
            money = new Money(100, Money.EURO);
            
            try
            {
                instance = new Bankiersessie(sourceRekeningNr, bank);
            }
            catch(RemoteException ex)
            {
                Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch(RemoteException ex)
        {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown(){}

    /**
     * Test of isGeldig method, of class Bankiersessie.
     */
    @Test
    public void testIsGeldig()
    {
        System.out.println("isGeldig");
        
        boolean result = instance.isGeldig();
        assertTrue(result);
    }
    
     /**
     * Test of isGeldig method, of class Bankiersessie.
     */
    @Test
    //@Ignore
    public void testIsGeldig2()
    {
        System.out.println("isGeldig");
        
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(BankiersessieTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean result = instance.isGeldig();
        assertFalse(result);
    }


    /**
     * Test of maakOver method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    (expected = RuntimeException.class)
    public void testMaakOver() throws Exception
    {
        System.out.println("maakOver");
        instance.maakOver(sourceRekeningNr, money);
    }
    
    /**
     * Test of maakOver method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    (expected = RuntimeException.class)
    public void testMaakOver2() throws Exception
    {
        System.out.println("maakOver");
        money = new Money(-100, Money.EURO);
        instance.maakOver(targetRekeningNr, money);
    }
    
    /**
     * Test of maakOver method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    (expected = RuntimeException.class)
    public void testMaakOver3() throws Exception
    {
        System.out.println("maakOver");
        money = new Money(0, Money.EURO);
        instance.maakOver(targetRekeningNr, money);
    }
    
    /**
     * Test of maakOver method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    (expected = NumberDoesntExistException.class)
    public void testMaakOver4() throws Exception
    {
        System.out.println("maakOver");
        instance.maakOver(25, money);
    }

    /**
     * Test of maakOver method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    (expected = InvalidSessionException.class)
    //@Ignore
    public void testMaakOver5() throws Exception
    {
        System.out.println("maakOver");
        
        Thread.sleep(time);
        instance.maakOver(targetRekeningNr, money);
    }
    
     /**
     * Test of maakOver method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    (expected = RuntimeException.class)
    public void testMaakOver6() throws Exception
    {
        System.out.println("maakOver");
        
        money = new Money(-20000, Money.EURO);
        
        boolean result = instance.maakOver(targetRekeningNr, money);
        assertFalse(result);
    }

     /**
     * Test of maakOver method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    public void testMaakOver7() throws Exception
    {
        System.out.println("maakOver");
        
        boolean result = instance.maakOver(targetRekeningNr, money);
        assertTrue(result);
    }

    /**
     * Test of getRekening method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    (expected = InvalidSessionException.class)
    //@Ignore
    public void testGetRekening() throws Exception
    {
        System.out.println("getRekening");
        
        Thread.sleep(time);
        
        instance.getRekening();
    }
    
    /**
     * Test of getRekening method, of class Bankiersessie.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetRekening2() throws Exception
    {
        System.out.println("getRekening");
        
        IRekening expResult = new Rekening(sourceRekeningNr, new Klant("Milton", "Tilburg"), Money.EURO);
        IRekening result = instance.getRekening();
        assertEquals(expResult, result);
    }
}