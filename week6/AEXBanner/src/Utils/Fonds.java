/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Stefan
 */
public class Fonds implements IFonds
{
    private String name;
    private double stock;
    
    public Fonds(String name, double stock)
    {
        this.name = name;
        this.stock = stock;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getStock() {
        return stock;
    }

    @Override
    public void setStock(double stock) {
        this.stock = stock;
    }
    
}
