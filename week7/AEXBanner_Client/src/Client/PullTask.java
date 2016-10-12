/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.IEffectenbeurs;
import java.rmi.RemoteException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stefan
 */
public class PullTask extends TimerTask
{
    private BannerController bannerController;
    private IEffectenbeurs effectenbeurs;
    
    public PullTask(BannerController bannerController, IEffectenbeurs effectenbeurs)
    {
        this.bannerController = bannerController;
        this.effectenbeurs = effectenbeurs;
    }
    
    @Override
    public void run()
    {
        try {
            bannerController.setFonds(effectenbeurs.getStocks());
        } catch (RemoteException ex) {
            Logger.getLogger(PullTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
