/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralBank;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milton van de Sanden
 */
public class CentralBankLauncher
{
    private static CentralBank centralBank;
    
    public static void main(String[] args)
    {
        try
        {
            centralBank = new CentralBank(true);
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(CentralBankLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
