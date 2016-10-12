/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class RMIServer
{
    private Registry registry;
    private final int PORTNUMBER = 1099;
    
    public RMIServer(IEffectenbeurs effectenbeurs)
    {   
        try {
            registry = LocateRegistry.createRegistry(PORTNUMBER);
            registry.rebind("effectenbeurs", effectenbeurs);
        } catch (RemoteException ex)
        {
            Logger.getLogger(RMIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
