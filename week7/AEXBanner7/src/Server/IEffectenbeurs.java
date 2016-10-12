/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Utils.IFonds;
import java.util.List;

/**
 *
 * @author Stefan
 */
public interface IEffectenbeurs
{
    public List<IFonds> getStocks();
    public void setStocks();
}
