package Client;

import Server.IEffectenbeurs;
import Server.MockEffectenbeurs;
import Utils.IFonds;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefan
 */
public class BannerController
{
    private AEXBanner banner;
    private List<IFonds> fonds;
    private IEffectenbeurs effectenbeurs;
    
    private Registry registry;
    private final String IP = "127.0.0.1";
    private final int PORTNUMBER = 1099;
    
    private Timer timer;
    
    public BannerController(AEXBanner banner)
    {
        this.banner = banner;
        fonds = new ArrayList<>();
        try {
            //effectenbeurs = new MockEffectenbeurs();
            registry = LocateRegistry.getRegistry(IP, PORTNUMBER);
            effectenbeurs = (IEffectenbeurs) registry.lookup("effectenbeurs");
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timer = new Timer();
        
        timer.scheduleAtFixedRate(new PullTask(this, effectenbeurs) ,(Calendar.getInstance()).getTime(), 5000);
    }
    
    public String getFonds()
    {
        String result = "";
        
        for(IFonds foundation : fonds)
        {
            result += foundation.getName() + ": " + foundation.getStock() + ";  ";
        }
        
        return result;
    }
    public void setFonds(List<IFonds> fonds)
    {
        this.fonds = fonds;
    }
    
    public void stop()
    {
        
    }
}
