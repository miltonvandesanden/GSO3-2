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
    private final int PORT = 1100;
    private Registry registry;
    
    private final String SERVERIP = "localhost";
    private final int SERVERPORT = 1099;
    private ICentralBank centralBank;

    public Bank() throws RemoteException{}
    
    public Bank(String name) throws RemoteException
    {
        try
        {
            ip = InetAddress.getLocalHost().getHostAddress();
            
            registry = LocateRegistry.createRegistry(PORT);
            registry.rebind(name, this);
            
            System.out.println("-----" + System.lineSeparator() + "Bank " + name + " Hosted" + System.lineSeparator() + ip +  System.lineSeparator() + PORT + System.lineSeparator() + "-----");
            
            Registry serverRegistry = LocateRegistry.getRegistry(SERVERIP, SERVERPORT);
            centralBank = (ICentralBank) serverRegistry.lookup("centralbank");
            //put entry in registry with key bank name, value bank ip
            serverRegistry.rebind(name, this);
            
            System.out.println("-----" + System.lineSeparator() + "connected to CentralBank at" + System.lineSeparator() + "IP: " + SERVERIP + System.lineSeparator() + "Port: " + SERVERPORT + System.lineSeparator() + "-----");
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
    public synchronized int openRekening(String name, String city)
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

    private IKlant getKlant(String name, String city)
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
    public IRekening getRekening(int nr)
    {
        return accounts.get(nr);
    }
    
    @Override
    public boolean maakOver(int source, int destination, Money money) throws NumberDoesntExistException
    {
        if (source == destination)
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

        if (!success)
        {
            return false;
        }

        IRekeningTbvBank dest_account = (IRekeningTbvBank) getRekening(destination);

        if (dest_account == null)
        {
            throw new NumberDoesntExistException("account " + destination + " unknown at " + name);
        }

        success = dest_account.muteer(money);

        if (!success)// rollback
        {
            source_account.muteer(money);
        }

        return success;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
