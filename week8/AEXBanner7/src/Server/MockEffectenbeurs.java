/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Utils.Fonds;
import Utils.IFonds;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class MockEffectenbeurs /*extends UnicastRemoteObject*/ implements IEffectenbeurs
{
    private List<IFonds> fonds;
    private Registry registry;
    private Random random;
    private Timer timer;
    private IRemotePublisherForDomain remotePublisher;
    
    //public MockEffectenbeurs() throws RemoteException {};
    
    public MockEffectenbeurs(Object object) /*throws RemoteException*/
    {
        fonds = new ArrayList<>();
        random = new Random();
        
        fonds.add(new Fonds("Apple", random.nextDouble()));
        fonds.add(new Fonds("Microsoft", random.nextDouble()));
        fonds.add(new Fonds("SSC", random.nextDouble()));
        
        timer = new Timer();
        
        
        try {
            remotePublisher = new RemotePublisher();
            remotePublisher.registerProperty("stocks");
            registry = LocateRegistry.createRegistry(1099);
            registry.rebind("stockServer", remotePublisher);
            //rmiServer = new RMIServer(this);
            
        } catch (RemoteException ex) {
            Logger.getLogger(MockEffectenbeurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer.scheduleAtFixedRate(new GenerateTask(this) ,(Calendar.getInstance()).getTime(), 5000);
    }
    
    @Override
    public List<IFonds> getStocks() {
        return fonds;
    }
    
    public void setStocks()
    {
        //List<IFonds> oldstocks = fonds;
        
        Random random = new Random();
        
        for(IFonds foundation : fonds)
        {
            foundation.setStock(round(random.nextDouble(), 2));
        }
        
        try {
            remotePublisher.inform("stocks", null, fonds);
        } catch (RemoteException ex) {
            Logger.getLogger(MockEffectenbeurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double round(double value, int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        
        return (double) tmp / factor;
    }
}
