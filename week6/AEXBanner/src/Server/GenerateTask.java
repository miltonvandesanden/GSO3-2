/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.TimerTask;

/**
 *
 * @author Stefan
 */
public class GenerateTask extends TimerTask
{
    private IEffectenbeurs effectenbeurs;
    
    public GenerateTask(IEffectenbeurs effectenbeurs)
    {
        this.effectenbeurs = effectenbeurs;
    }
    @Override
    public void run()
    {
        effectenbeurs.setStocks();
    }
    
}
