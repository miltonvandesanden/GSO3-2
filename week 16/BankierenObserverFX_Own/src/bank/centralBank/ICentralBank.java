/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralBank;

import bank.bankieren.Money;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Milton van de Sanden
 */
public interface ICentralBank extends Remote
{
    public boolean subscribeBank(String afk, String ip, int port) throws RemoteException;
    public boolean createTransaction(int source, int target, Money amount, String bank) throws RemoteException;
    public List<String> getHostedBanks() throws RemoteException;
}
