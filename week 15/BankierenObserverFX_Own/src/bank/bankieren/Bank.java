package bank.bankieren;

import fontys.util.*;
import java.util.*;

public class Bank implements IBank
{
    private static final long serialVersionUID = -8728841131739353765L;
    private final Map<Integer,IRekeningTbvBank> accounts;
    public final Collection<IKlant> clients;
    
    private int nieuwReknr;
    private final String name;

    public Bank(String name)
    {
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
