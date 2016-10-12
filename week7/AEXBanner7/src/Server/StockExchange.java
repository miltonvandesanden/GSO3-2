/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class StockExchange
{

    public static void main(String[] args)
    {
        // Welcome message
        System.out.println("SERVER USING REGISTRY");
        
        try {
            MockEffectenbeurs effectenbeurs = new MockEffectenbeurs(new Object());
        } catch (RemoteException ex) {
            Logger.getLogger(StockExchange.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
