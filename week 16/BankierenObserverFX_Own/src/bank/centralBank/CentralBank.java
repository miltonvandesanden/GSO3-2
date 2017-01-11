/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralBank;

import bank.bankieren.IBank;
import bank.bankieren.Money;
import fontys.util.NumberDoesntExistException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
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
    public boolean subscribeBank(String name, String ip, int port) throws RemoteException
    {
        try {
            banks.add((IBank)LocateRegistry.getRegistry(ip, port).lookup(name));
            return true;
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(CentralBank.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean createTransaction(int source, int target, Money amount, String bank) throws RemoteException
    {   
        if(bank != null)
        {
            if(!bank.isEmpty())
            {
                for(IBank bank2 : banks)
                {
                    if(bank2.getName().equals(bank))
                    {
                        try {
                            return bank2.maakOverTarget(target, amount);
                        } catch (NumberDoesntExistException ex) {
                            Logger.getLogger(CentralBank.class.getName()).log(Level.SEVERE, null, ex);
                            return false;
                        }
                    }
                }
            }
            
            return false;
        }
        
        return false;
    }

    @Override
    public List<String> getHostedBanks() throws RemoteException
    {
        List<String> names = new ArrayList<>();
        
        for(IBank bank : banks)
        {
            names.add(bank.getName());
        }
        
        return names;
    }
}
