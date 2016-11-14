/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Stefan
 */
public class StockExchange
{

    public static void main(String[] args)
    {
        // Welcome message
        System.out.println("SERVER USING REGISTRY");
        
        MockEffectenbeurs effectenbeurs = new MockEffectenbeurs(new Object());
    }
}
