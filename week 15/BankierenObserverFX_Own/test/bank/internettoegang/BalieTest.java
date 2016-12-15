/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import java.rmi.RemoteException;
import java.util.HashMap;
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
public class BalieTest {
     
    Balie instance=null;
    Bank bankinstance=null;
    int rekeningNo=0;
    HashMap<String, ILoginAccount> loginaccounts;
    LoginAccount loginAccountInstance = null;
    
    public BalieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws RemoteException {
        bankinstance = new Bank("Rabobank");
        instance = new Balie(bankinstance);
        rekeningNo = 54645873;
        loginaccounts = new HashMap<String, ILoginAccount>();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openRekening method, of class Balie.
     */
    @Test
    public void testOpenRekeningNoName(){
        System.out.println("openRekening");
        String naam = "";
        String plaats = "Sliedrecht";
        String wachtwoord = "Hansel";
        String result = instance.openRekening(naam, plaats, wachtwoord);
        assertNull(result);
    }
    
    @Test
    public void testOpenRekeningNoPlace() {
        System.out.println("openRekening");
        String naam = "Gretel";
        String plaats = "";
        String wachtwoord = "Hansel";
        String result = instance.openRekening(naam, plaats, wachtwoord);
        assertNull(result);
    }

    @Test
    public void testOpenRekeningPasswordNotCorrect() {
        System.out.println("openRekening");
        String naam = "Gretel";
        String plaats = "Sliedrecht";
        String wachtwoord = "HanselUndGretel"; //password langer dan 8
        String result = instance.openRekening(naam, plaats, wachtwoord);
        assertNull(result);
    }
    
    @Test
    public void testOpenRekening() {
        System.out.println("openRekening");
        String naam = "Gretel";
        String plaats = "Sliedrecht";
        String wachtwoord = "Hansel";
        int expectedResult = 8;
        String result = instance.openRekening(naam, plaats, wachtwoord);
        assertEquals(expectedResult, result.length());
    }
    /**
     * Test of logIn method, of class Balie.
     */
    @Test
    public void testLogInNoName() throws Exception {
        System.out.println("logIn");
        String accountnaam = "";
        String wachtwoord = "password";
       
        loginAccountInstance = new LoginAccount(accountnaam, wachtwoord, rekeningNo);
        loginaccounts.put(accountnaam, loginAccountInstance);
        
        instance.setLoginAccount(loginaccounts);
        
        IBankiersessie expectedSession = new Bankiersessie(rekeningNo, bankinstance);
        
        IBankiersessie result = instance.logIn(accountnaam, wachtwoord);
        
        assertNull(result);
    }
    
    @Test
    public void testLogInNoPassword() throws Exception {
        System.out.println("logIn");
        String accountnaam = "Hansel";
        String wachtwoord = "";
       
        loginAccountInstance = new LoginAccount(accountnaam, "welEenWactwoord", rekeningNo);
        loginaccounts.put(accountnaam, loginAccountInstance);
        
        instance.setLoginAccount(loginaccounts);
        
        IBankiersessie expectedSession = new Bankiersessie(rekeningNo, bankinstance);
        
        
        IBankiersessie result = instance.logIn(accountnaam, wachtwoord);
        
        assertNull(result);
    }
    
    @Test
    public void testLogInCorrect() throws Exception {
        System.out.println("logIn");
        String accountnaam = "Hansel";
        String wachtwoord = "password";
       
        loginAccountInstance = new LoginAccount(accountnaam, wachtwoord, rekeningNo);
        loginaccounts.put(accountnaam, loginAccountInstance);
        
        instance.setLoginAccount(loginaccounts);
        
        IBankiersessie expectedSession = new Bankiersessie(rekeningNo, bankinstance);
        
        
        IBankiersessie result = instance.logIn(accountnaam, wachtwoord);
        
        assertEquals(result , expectedSession);
    }
}
