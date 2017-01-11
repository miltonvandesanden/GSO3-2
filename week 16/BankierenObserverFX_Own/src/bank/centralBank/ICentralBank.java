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

    /**
     * subscribes the Bank with name afk to the CentralBank and CentralBank creates a RMI connection with Bank
     * @param afk the name of the bank that will be subscribed, may not be null or empty
     * @param ip the IP-address on which the Bank is hosted using RMI, may not be null or empty
     * @param port the port on which the Bank is hosted using RMI, port may not be already in use
     * @return true when the bank is successfully subscribed, false when the bankis unsuccessfully subscribed
     * @throws RemoteException
     */
    public boolean subscribeBank(String afk, String ip, int port) throws RemoteException;

    /**
     * transfers amount money from Rekening with number source to Rekening on Bank bank and with number targer
     * increases saldo of rekening target by amount
     * increases saldo of rekening source when saldo of rekening target is unsuccesfully increased
     * @param source  the number of the Rekening where the transfer originates from, may not be null or empty, Rekenng with this number must exist
     * @param target the number of the Rekening where the transfer is going to, may not be null or empty, Rekening with this number must exist
     * @param amount the amount of money that will be transferred, may not be negative or zero
     * @param bank the name of the Bank the target Rekening belongs to, the Bank must be subscribed to CentralBank
     * @return true when transfer is successfull otherwise it returns false
     * @throws RemoteException
     */
    public boolean createTransaction(int source, int target, Money amount, String bank) throws RemoteException;

    /**
     * @return list with the names of all banks that are subscribed to the CentralBank
     * @throws RemoteException
     */
    public List<String> getHostedBanks() throws RemoteException;
}
