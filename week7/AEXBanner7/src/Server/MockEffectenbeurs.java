/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Utils.Fonds;
import Utils.IFonds;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;

/**
 *
 * @author Stefan
 */
public class MockEffectenbeurs implements IEffectenbeurs
{
    private List<IFonds> fonds;
    
    private Random random;
    private Timer timer;
    
    public MockEffectenbeurs()
    {
        fonds = new ArrayList<>();
        random = new Random();
        
        fonds.add(new Fonds("Apple", random.nextDouble()));
        fonds.add(new Fonds("Microsoft", random.nextDouble()));
        fonds.add(new Fonds("SSC", random.nextDouble()));
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new GenerateTask(this) ,(Calendar.getInstance()).getTime(), 5000);
    }
    
    @Override
    public List<IFonds> getStocks() {
        return fonds;
    }
    
    public void setStocks()
    {
        Random random = new Random();
        
        for(IFonds foundation : fonds)
        {
            foundation.setStock(round(random.nextDouble(), 2));
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
