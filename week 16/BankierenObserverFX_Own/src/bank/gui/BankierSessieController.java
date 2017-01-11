/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.gui;

import bank.bankieren.IRekening;
import bank.bankieren.Money;
import bank.internettoegang.Bankiersessie;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;
import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class BankierSessieController extends UnicastRemoteObject implements Initializable, IRemotePropertyListener
{

    @FXML
    private Hyperlink hlLogout;

    @FXML
    private TextField tfNameCity;
    @FXML
    private TextField tfAccountNr;
    @FXML
    private TextField tfBalance;
    @FXML
    private TextField tfAmount;
    @FXML
    private TextField tfToAccountNr;
    @FXML
    private Button btTransfer;
    @FXML
    private TextArea taMessage;
    @FXML
    private ComboBox cbSelectBank;

    private BankierClient application;
    private IBalie balie;
    private IBankiersessie sessie;
    String  bankNaam;
    
    private IRemotePublisherForListener listener;

    public void setApp(BankierClient application, IBalie balie, IBankiersessie sessie)
    {
        this.balie = balie;
        this.sessie = sessie;
        this.application = application;
        IRekening rekening = null;
        try {
            rekening = sessie.getRekening();
            tfAccountNr.setText(rekening.getNr() + "");
            tfBalance.setText(rekening.getSaldo() + "");
            String eigenaar = rekening.getEigenaar().getNaam() + " te "
                    + rekening.getEigenaar().getPlaats();
            tfNameCity.setText(eigenaar);
            
            cbSelectBank.getItems().addAll(balie.getHostedBanks());
        } catch (InvalidSessionException ex) {
            disconnect();
            taMessage.setText("bankiersessie is verlopen");
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (RemoteException ex) {
            taMessage.setText("verbinding verbroken");
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        try {
            //listener = (IRemotePublisherForListener) Naming.lookup("saldoPublisher" + tfAccountNr.getText());
            Registry registry = LocateRegistry.getRegistry(application.getFileIP(), application.getFilePort() + 1);
            listener = (IRemotePublisherForListener) registry.lookup("saldoPublisher" + tfAccountNr.getText());
            listener.subscribeRemoteListener(this, "saldo");
        } catch (NotBoundException ex) {
            Logger.getLogger(Bankiersessie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void disconnect()
    {
        try {
            listener.unsubscribeRemoteListener(this, "saldo");
            Naming.unbind("saldoPublisher" + tfAccountNr.getText());
            sessie.logUit();
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public BankierSessieController() throws RemoteException{}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbSelectBank.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                bankNaam = (String) ov.getValue();
            }
        }
        );
        
    }

    @FXML
    private void logout(ActionEvent event) {
        disconnect();
        application.gotoLogin(balie, "");
    }

    @FXML
    private void transfer(ActionEvent event) {
        try {
            int from = Integer.parseInt(tfAccountNr.getText());
            int to = Integer.parseInt(tfToAccountNr.getText());
            if (from == to && bankNaam.equals(application.getFileBankName()))
            {
                taMessage.setText("can't transfer money to your own account");
            }
            long centen = (long) (Double.parseDouble(tfAmount.getText()) * 100);
            if(bankNaam!= null)
            {
                if(!bankNaam.isEmpty())
                {
                    sessie.maakOver(to, new Money(centen, Money.EURO), bankNaam);
                }
            }
        } 
        catch (RemoteException e1) {
            e1.printStackTrace();
            taMessage.setText("verbinding verbroken");
        } catch (NumberDoesntExistException | InvalidSessionException e1) {
            e1.printStackTrace();
            taMessage.setText(e1.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        tfBalance.setText(evt.getNewValue().toString());
    }
}
