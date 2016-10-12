package Client;

import Server.IEffectenbeurs;
import Server.MockEffectenbeurs;
import Utils.IFonds;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

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
    
    
    private Timer timer;
    
    public BannerController(AEXBanner banner)
    {
        this.banner = banner;
        fonds = new ArrayList<>();
        effectenbeurs = new MockEffectenbeurs();
        
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
