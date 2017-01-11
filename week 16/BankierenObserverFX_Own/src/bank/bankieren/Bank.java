package bank.bankieren;

import bank.centralBank.ICentralBank;
import fontys.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank extends UnicastRemoteObject implements IBank
{
    private static final long serialVersionUID = -8728841131739353765L;
    private Map<Integer,IRekeningTbvBank> accounts;
    public Collection<IKlant> clients;
    
    private int nieuwReknr;
    private String name;
    
    private String ip;
    private Registry registry;
    
    private final String CENTRALIP = "localhost";
    private final int CENTRALPORT = 1099;
    private ICentralBank centralBank;

    public Bank() throws RemoteException{}
    
    public Bank(String name, int portnr) throws RemoteException
    {
        try
        {
            ip = InetAddress.getLocalHost().getHostAddress();
            
            registry = LocateRegistry.createRegistry(portnr + 1);
            registry.rebind(name, this);
            
            System.out.println("-----" + System.lineSeparator() + "Bank " + name + " Hosted" + System.lineSeparator() + ip +  System.lineSeparator() + portnr + 1 + System.lineSeparator() + "-----");
            
            Registry serverRegistry = LocateRegistry.getRegistry(CENTRALIP, CENTRALPORT);
            centralBank = (ICentralBank) serverRegistry.lookup("centralBank");
            
            centralBank.subscribeBank(name, ip, portnr + 1);
            
            System.out.println("-----" + System.lineSeparator() + "connected to CentralBank at" + System.lineSeparator() + "IP: " + CENTRALIP + System.lineSeparator() + "Port: " + CENTRALPORT + System.lineSeparator() + "-----");
        }
        catch (UnknownHostException | RemoteException | NotBoundException ex)
        {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        accounts = new HashMap<>();
        clients = new ArrayList<>();
        nieuwReknr = 100000000;	
        this.name = name;	
    }

    @Override
    public synchronized int openRekening(String name, String city) throws RemoteException
    {
        if (name.isEmpty() || city.isEmpty())
        {
            return -1;
        }

        IKlant klant = getKlant(name, city);
        IRekeningTbvBank account = new Rekening(nieuwReknr, klant, Money.EURO);
        
        accounts.put(nieuwReknr,account);
        nieuwReknr++;
        
        return nieuwReknr-1;
    }

    private IKlant getKlant(String name, String city) throws RemoteException
    {
        for (IKlant k : clients)
        {
            if (k.getNaam().equals(name) && k.getPlaats().equals(city))
            {
                return k;
            }
        }

        IKlant klant = new Klant(name, city);
        clients.add(klant);
        return klant;
    }

    @Override
    public IRekening getRekening(int nr) throws RemoteException
    {
        return accounts.get(nr);
    }
    
    @Override
    public boolean maakOver(int source, int destination, Money money, String bankName) throws NumberDoesntExistException, RemoteException
    {
        if (source == destination && bankName.equals(name))
        {
            throw new RuntimeException("cannot transfer money to your own account");
        }

        if (!money.isPositive())
        {
            throw new RuntimeException("money must be positive");
        }

        IRekeningTbvBank source_account = (IRekeningTbvBank) getRekening(source);

        if (source_account == null)
        {
            throw new NumberDoesntExistException("account " + source + " unknown at " + name);
        }

        Money negative = Money.difference(new Money(0, money.getCurrency()), money);

        boolean success = source_account.muteer(negative);

        if (success)
        {
            if(bankName != null)
            {
                if(bankName.equals(name))
                {
                    success = maakOverTarget(destination, money);
                }
                else
                {
                    success = centralBank.createTransaction(source, destination, money, bankName);
                }
            }
            else
            {
                success = false;
            }
            
            if(!success)
            {
                source_account.muteer(money);
            }
        }

        return success;
    }
    
    @Override
    public boolean maakOverTarget(int destination, Money money) throws NumberDoesntExistException
    {
        boolean success = false;
        
        if(!accounts.containsKey(destination))
        {
            return false;
        }
        
        try
        {    
            IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(destination);
            
            if (dest_account == null)
            {
                return false;
            }
            
            success = dest_account.muteer(money);
            
        } catch (RemoteException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            return success;
        }
    }

    @Override
    public String getName() throws RemoteException
    {
        return name;
    }

    @Override
    public List<String> getHostedBanks() throws RemoteException
    {
        return centralBank.getHostedBanks();
    }
}
