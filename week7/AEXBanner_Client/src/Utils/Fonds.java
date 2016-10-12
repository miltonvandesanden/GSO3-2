/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Stefan
 */
public class Fonds implements IFonds, Serializable
{
    private String name;
    private double stock;
    
    public Fonds() throws RemoteException{};
    
    public Fonds(String name, double stock) throws RemoteException
    {
        this.name = name;
        this.stock = stock;
    }
    
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public double getStock()
    {
        return stock;
    }

    @Override
    public void setStock(double stock)
    {
        this.stock = stock;
    }
}
