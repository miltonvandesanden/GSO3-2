/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontyspublisher;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stub for remote property listener. An instance of this class maintains a 
 * list of property change events received.
 *
 * @author Nico Kuijpers
 */
public class StubRemotePropertyListener 
        extends UnicastRemoteObject 
        implements IRemotePropertyListener {

    // List of property change events received by this listener
    List<PropertyChangeEvent> receivedEvents;
    
    // Flag to indicate whether disconnection from the internet should met simulated
    boolean simulateDisconnect = false;
    
    // Delay in ms before RemoteExcpection is thrown when disconnection from the
    // internet is simulated
    int delay = 0;

    /**
     * Constructor.
     * @throws java.rmi.RemoteException
     */
    public StubRemotePropertyListener() throws RemoteException {
        receivedEvents = new ArrayList<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        if (simulateDisconnect) {
            try {
                // Simulate disconnection from the internet by sleeping
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(StubRemotePropertyListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            throw new RemoteException("disconnection from the internet simulated");
        }
        receivedEvents.add(evt);
    }

    /**
     * Get list of property change events received by this listener.
     * 
     * @return list of property change events
     * @throws java.rmi.RemoteException
     */
    public List<PropertyChangeEvent> getReceivedEvents() throws RemoteException {
        return receivedEvents;
    }
    
    /**
     * Set flag and delay to simulate disconnection from the internet.
     */
    public void setSimulateDisconnect(int delay) {
        this.simulateDisconnect = true;
        this.delay = delay;
    }
}
