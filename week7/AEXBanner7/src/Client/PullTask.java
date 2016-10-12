/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.IEffectenbeurs;
import Utils.IFonds;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

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
        bannerController.setFonds(effectenbeurs.getStocks());
    }
    
}
