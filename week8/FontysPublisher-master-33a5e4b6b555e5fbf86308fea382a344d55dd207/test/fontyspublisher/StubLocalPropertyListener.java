/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontyspublisher;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub for local property listener. An instance of this class maintains a 
 * list of property change events received.
 *
 * @author Nico Kuijpers
 */
public class StubLocalPropertyListener implements ILocalPropertyListener {

    // List of property change events received by this listener
    List<PropertyChangeEvent> receivedEvents;

    /**
     * Constructor.
     */
    public StubLocalPropertyListener() {
        receivedEvents = new ArrayList<>();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        receivedEvents.add(evt);
    }

    /**
     * Get list of property change events received by this listener.
     * 
     * @return list of property change events
     */
    public List<PropertyChangeEvent> getReceivedEvents() {
        return receivedEvents;
    }
}
