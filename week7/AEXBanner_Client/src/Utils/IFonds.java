/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stefan
 */
public interface IFonds extends Remote
{
    public String getName();
    public double getStock();
    public void setStock(double stock);
}
