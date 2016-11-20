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
public interface ISession
{

    /**
     * 
     * @return het Account wat gekoppeld is aan de Session
     */
    public Account getAccount();
    
    /**
     * gets het BankAccount et bankAccountNo
     * @param bankAccountNo het bankAccountNo van het BankAccount wat opgehaald wordt
     * @return het BankAccount met bankAccountNo
     * @IllegalArgumentException wanneer er geen BankAccount van de ingelogde gebruiker is met bankAccountNo
     */
    public BankAccount getBankAccount(String bankAccountNo);

    /**
     *
     * @return een List met BankAccounts van de ingelogde gebruiker
     */
    public List<BankAccount> getBankAccounts();
    
    /**
     * creates een nieuwe BankAccount en koppelt deze aan de Account van de ingelogde gebruiker
     * @return de nieuw aangemaakte BankAccount
     */
    public BankAccount createBankAccount();

    /**
     * deletes het BankAccount van de ingelogde gebruiker met bankAccountNo
     * @param bankAccountNo het bankAccountNo van het BankAccount wat verwijderd wordt
     * @IllegalArgumentException wanneer de gebruiker geen BankAccount heeft met bankAccountNo
     */
    public void deleteBankAccount(String bankAccountNo);
    
    /**
     *
     * @param bankAccountNo het bankAccountNo van het BankAccount waarvan de transactions worden opgehaald
     * @return een List met alle Transactions van de gebruiker\
     * @IllegalArgumentException wanneer de gebruiker geen BankAccount heeft met bankAccountNo
     */
    public List<Transaction> getTransactions(String bankAccountNo);

    /**
     *
     * @param bankAccountSource het bankAccountNo van de gebruiker
     * @param bankAccountTarget het bankAccountNo van het BankAccount waarnaar het geld wordt overgemaakt
     * @param amount het bedrag wat overgemaakt wordt
     * @return de nieuwe Transaction
     * @IllegalArgumentException wanneer de gebruiker geen BankAccount heeft met bankAccountSource
     * @IllegalArgumentException amount <= 0
     */
    public Transaction createTransaction(String bankAccountSource, String bankAccountTarget, double amount);
}
