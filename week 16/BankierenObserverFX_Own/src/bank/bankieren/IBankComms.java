/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import fontys.util.NumberDoesntExistException;
import java.rmi.Remote;

/**
 *
 * @author Milton van de Sanden
 */
public interface IBankComms extends Remote
{
     /**
     * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar de
     * bankrekening met nummer bestemming, mits het afschrijven van het bedrag
     * van de rekening met nr bron niet lager wordt dan de kredietlimiet van deze
     * rekening 
     * 
     * @param bron
     * @param bestemming
     *            ongelijk aan bron
     * @param bedrag
     *            is groter dan 0
     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
     * @throws NumberDoesntExistException
     *             als een van de twee bankrekeningnummers onbekend is
     */
    boolean maakOver(int bron, int bestemming, Money bedrag) throws NumberDoesntExistException;
}
