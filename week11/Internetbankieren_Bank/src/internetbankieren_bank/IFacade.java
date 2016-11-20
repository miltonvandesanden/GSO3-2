/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankieren_bank;

import java.util.List;
import utils.Account;
import utils.BankAccount;
import utils.Transaction;

/**
 *
 * @author Stefan
 */
public interface IFacade
{
    /**
     * de gebruiker wordt ingelogd en er wordt een Sessie voor de gebruiker aangemaakt,
     * als er al een sessie voor de gebruiker bestaat wordt deze bestaande sessie gebruikt
     * @IllegalArgumentException wanneer inlogAccountCode en/of password empty zijn
     * @param inlogAccountCode string met the inlogAccountCode van de gebruiker
     * @param password string met het wachtwoord van de gebruiker
     * @return de sessionId van de bij de ingelogde gebruiker behorende sessie
     * @precondition gebruiker is niet ingelogd
     * @postcondition gebruiker is ingelogd
     */
    public int logIn(String inlogAccountCode, String password);

    /**
     * de gebruiker wordt uitgelogd en de sessie van de gebruiker wordt gedelete
     * @IllegalArgumentException wanneer er geen Session is met sessionId
     * @param sessionId de sessionId van de bij de ingelogde gebruiker behorende sessie
     * @precondition de gebruiker is ingelogd
     * @postcondition de gebruiker is uitgelogd
     */
    public void logOut(int sessionId);
    
    /**
     * haalt het Account van de ingelogde gebruiker op
     * @IllegalArgumentException wanneer er geen Session is met sessionId
     * @param sessionId het sessieId van de sessie van de ingelogde gebruiker
     * @return het account van de ingelogde gebruiker of null wanneer er geen Session met sessionId is gevonden
     * @precondition gebruiker is ingelogd
     */
    public Account getAccount(int sessionId);

    /**
     * haalt alle BankAccounts van de ingelogde gebruiker op
     * @IllegalArgumentException wanneer er geen Session is met sessionId
     * @param sessionId het sessieId van de sessie van de ingelogde gebruiker
     * @return een List met de BankAccounts van de ingelogde gebruiker of een lege List wanneer er geen Session met sessionId is gevonden
     * @precondition gebruiker is ingelogd
     */
    public List<BankAccount> getBankAccounts(int sessionId);
    
    /**
     * creates een new BankAccount en koppelt deze aan het Account van de ingelogde gebruiker
     * @param sessionId de sessionId van de Session van de ingelogde gebruiker
     * @return het nieuw aangemaakte BankAccount
     * @IllegalArgumentException wanneer er geen Session is met sessionId
     * @precondition gebruiker is ingelogd
     */
    public BankAccount createBankAccount(int sessionId);

    /**
     * verwijderd het meegegeven BankAccount
     * @param sessionId de sessionId van de ingelogde gebruiker
     * @param bankAccount het BankAccount dat verwijderd wordt
     * @return true if bankAccount verwijderd is, false als BankAccount niet verwijderd is
     * @precondition gebruiker is ingelogd
     * @postcondition Bankaccount is verwijderd
     * @IllegalArgumentException wanneer er geen Session is met sessionId
     */
    public boolean deleteBankAccount(int sessionId, BankAccount bankAccount);
    
    /**
     * creates een Transaction, voegt deze Transaction toe aan het BankAccount van source en target, verlaagt het saldo van source met amount, verhoogt het saldo van target met amount
     * @param sessionId het sessionId van de Session van de ingelogde gebruiker
     * @param bankAccountSource het bankAccountNo van de bankAccount van de ingelogde gebruiker
     * @param bankAccountTarget het bankAccountNo van het bankAccount waar het geld naar overgemaakt wordt
     * @param amount het bedrag dat overgemaakt wordt
     * @IllegalArgumentException wanneer er geen Session is met sessionId
     * @IllegalArgumentException wanneer bankAccountSource niet toe behoort aan de ingelogde gebruiker
     * @IllegalArgumentException wanneer er geen bankAccount bestaat met bankAccountTarget
     * @IllegalArgumentException wanneer amount <= 0
     * @return de nieuw aangemaakte Transaction
     */
    public Transaction createTransaction(int sessionId, String bankAccountSource, String bankAccountTarget, double amount);

    /**
     * pushed Transaction naar de client
     * @param transaction de transaction die gepushed wordt
     */
    public void pushTransaction(Transaction transaction);
}