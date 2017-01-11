package bank.internettoegang;

import java.rmi.Remote;
import java.rmi.RemoteException;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;

public interface IBankiersessie extends Remote
{	
    public long GELDIGHEIDSDUUR = /*10000;*/ 600000;

    /**
     * @return 
     * @throws java.rmi.RemoteException
     * @returns true als de laatste aanroep van getRekening of maakOver voor deze
     *          sessie minder dan GELDIGHEIDSDUUR geleden is
     *          en er geen communicatiestoornis in de tussentijd is opgetreden, 
     *          anders false
     */
    public boolean isGeldig() throws RemoteException; 

    /**
     * er wordt bedrag overgemaakt van de bankrekening met het nummer bron naar
     * de bankrekening met nummer bestemming
     * 
     * @param bestemming
     *            is ongelijk aan rekeningnummer van deze bankiersessie
     * @param bedrag
     *            is groter dan 0
     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
     * @throws NumberDoesntExistException
     *             als bestemming onbekend is
     * @throws InvalidSessionException
     *             als sessie niet meer geldig is 
    * @throws java.rmi.RemoteException 
     */
    public boolean maakOver(int bestemming, Money bedrag, String bankNaam) throws NumberDoesntExistException, InvalidSessionException, RemoteException;

    /**
     * @return de rekeninggegevens die horen bij deze sessie
     * @throws InvalidSessionException
     *             als de sessieId niet geldig of verlopen is
     * @throws RemoteException
     */
    IRekening getRekening() throws InvalidSessionException, RemoteException;
    
     /**
     * sessie wordt beeindigd
     * @param rmiName
     * @throws java.rmi.RemoteException
     */
    void logUit() throws RemoteException;

}
