/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankieren_bank;
import utils.BankAccount;
/**
 *
 * @author Stefan
 */
public interface ICentralManager {
    
    /**
     *
     * @param bankAccount bankaccount waarvan je wilt controleren of deze binnen de huidige bank bekend is.
     * @return true wanneer het bankaccount bekend is binnen de bank, anders false
     * @precondities bankAccount is een bestaande rekening
     * @postcondities er wordt bijvoorbeeld een transactie naar deze bankrekening gemaakt.
     */
    public boolean checkBank(BankAccount bankAccount);

    /**
     *
     * @param bankAccountSource rekeningnummer van de sender
     * @param bankAccountTarget rekeningnummer van de receiver
     * @param amount het bedrag dat wordt overgaamtk
     * @return true als de targetrekening bestaat en het bedrag hier succesvol is bijgeschreven
     * @preconditie de bankAccountTarget is een bestaande rekening
     * @postconditie de overschrijving staat bij beide rekeningen aangeven en het saldo is bijgewerkt
     */
    public boolean createTransactiobn(String bankAccountSource,String bankAccountTarget, double amount);
}
