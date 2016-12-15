package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;

public class Bankiersessie extends UnicastRemoteObject implements IBankiersessie
{
    private static final long serialVersionUID = 1L;
    private long laatsteAanroep;
    private final int reknr;
    private final IBank bank;

    public Bankiersessie(int reknr, IBank bank) throws RemoteException
    {
        laatsteAanroep = System.currentTimeMillis();
        this.reknr = reknr;
        this.bank = bank;
    }

    @Override
    public boolean isGeldig()
    {
        return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
    }
    
    @Override
    public boolean maakOver(int bestemming, Money bedrag) throws NumberDoesntExistException, InvalidSessionException, RemoteException
    {
        updateLaatsteAanroep();

        if (reknr == bestemming)
        {
            throw new RuntimeException("source and destination must be different");
        }
                
        if (!bedrag.isPositive())
        {
            throw new RuntimeException("amount must be positive");
        }
        
        return bank.maakOver(reknr, bestemming, bedrag);
    }

    private void updateLaatsteAanroep() throws InvalidSessionException
    {
        if (!isGeldig())
        {
            throw new InvalidSessionException("session has been expired");
        }

        laatsteAanroep = System.currentTimeMillis();
    }

    @Override
    public IRekening getRekening() throws InvalidSessionException, RemoteException
    {
        updateLaatsteAanroep();
        return bank.getRekening(reknr);
    }

    @Override
    public void logUit() throws RemoteException
    {
        UnicastRemoteObject.unexportObject(this, true);
    }
    
    /**
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other)
    {
        Bankiersessie other1 = (Bankiersessie)other;
        
        return (other1.reknr == this.reknr && other1.bank.equals(this.bank));
    }
}
