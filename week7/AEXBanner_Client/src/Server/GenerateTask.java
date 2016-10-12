/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.RemoteException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            effectenbeurs.setStocks();
        } catch (RemoteException ex) {
            Logger.getLogger(GenerateTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
