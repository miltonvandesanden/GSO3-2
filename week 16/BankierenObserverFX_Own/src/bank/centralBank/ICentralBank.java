/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralBank;

import bank.bankieren.Money;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Milton van de Sanden
 */
public interface ICentralBank extends Remote
{
    public boolean subscribeBank(String afk, String ip, int port) throws RemoteException;
    public boolean createTransaction(String source, String target, Money amount) throws RemoteException;
}
