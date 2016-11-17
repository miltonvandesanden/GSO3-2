/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankieren_facade;

/**
 *
 * @author Stefan
 */
public interface IBankManager {

    /**
     *
     * @param inlogAccountCode string met de inlogAccountCode van de gebruiken
     * @param password String met het password van de gebruiker
     * @param firstName string met de naam van de gebruiker
     * @param lastName string met de achternaam van de gebruiker
     * @param place string met de woonplaatst van de gebruiker
     * @precondition gebruiker is aanwezig op locatie bij de bank en meld zich aan
     * @postcondition er is een account aangemaakt bij de bank
     */
    public void createAccount(String inlogAccountCode, String password, String firstName, String lastName, String place);

    /**
     *
     * @param inlogAccountCode string inlogAccountCode die de gebruiker heeft ontvangen bij het aanmelden bij de bank
     * @param password String password die de gebruiker heeft ontvangen bij het aanmelden bij de bank
     * @return retourneerd het id van de sessie die aangemaakt wordt voor de gebruiker, als er al een sessie bestaat voor deze gebruiker wordt het sessieid van deze sessie geretourneerd
     * @precondition gebruiker is nog niet ingelogd
     * @postcondition gebruiker is ingelogd een heeft een sessieid waarmee communicatie mogelijk is tussen client en facade
     */
    public int LogIn(String inlogAccountCode, String password);
    
    /**
     *
     * @param sessionId sessionid waarmee de gebruiker was ingelogd
     * @preconditiegebruiker is ingelogd
     * @postconditie sessie wordt gekilld en gebruiker is uitgelogd
     */
    public void LogOut(int sessionId);

    /**
     *
     * @param bankAccountSource rekeningnummer van de sender   
     * @param bankAccountTarget rekeningnummer van de receiver
     * @param amount bedrag dat wordt overgemaakt
     * @preconditie gebruiker is ingelogd en maakt geld van zijn rekening over naar een bestaande andere rekening
     * @postconditie geld wordt van de ene rekening over gemaakt naar de andere rekening
     */
    public void createTransaction(String bankAccountSource, String bankAccountTarget, double amount);
}
