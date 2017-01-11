/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralBank;

import bank.bankieren.IBank;
import bank.bankieren.Money;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milton van de Sanden
 */
public class CentralBank extends UnicastRemoteObject implements ICentralBank
{
    private String ip;
    private final int PORT = 1099;
    private Registry registry;
    
    private List<IBank> banks;
    
    public CentralBank() throws RemoteException{}
    
    public CentralBank(boolean blub) throws RemoteException
    {
        banks = new ArrayList<>();
        
        try
        {
            ip = InetAddress.getLocalHost().getHostAddress();
            
            registry = LocateRegistry.createRegistry(PORT);
            registry.rebind("centralBank", this);
            
            System.out.println("-----" + System.lineSeparator() + "CentralBank Hosted" + System.lineSeparator() + ip +  System.lineSeparator() + PORT + System.lineSeparator() + "-----");
        }
        catch (UnknownHostException ex)
        {
            Logger.getLogger(CentralBank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean subscribeBank(String afk, String ip, int port) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createTransaction(String source, String target, Money amount) throws RemoteException {
        
        //if(
        //lookup in serverregistry ip for that bank
        //connect to registry
        //get IBank from registry
        //if banks doesnt contain targetbank
        //get targetbank from registy
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
