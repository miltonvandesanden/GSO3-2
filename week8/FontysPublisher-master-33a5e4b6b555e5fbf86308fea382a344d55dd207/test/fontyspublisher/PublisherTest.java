package fontyspublisher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Nico Kuijpers
 */
public class PublisherTest {
    
    // Publisher
    private Publisher publisher;
    
    // Properties to be published
    private String[] properties;
    
    // Note that listeners are informed asynchronously
    // Define delay to ensure that listeners have been informed
    private final int delay = 10; // 10 ms
    
    public PublisherTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        properties = new String[]{"propA","propB"};
        publisher = new Publisher(properties);
    }
    
    @After
    public void tearDown() {
    }

    // Note that listeners are informed asynchronously
    // Wait some time to ensure that listeners have been informed
    private void wait(int delay) { 
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test 
    public void testPublisherDefaultNoargConstructor() {
        /**
         * Default no-arg constructor for Publisher.
         */
        // Create default publisher with no-arg constructor
        Publisher defaultPublisher = new Publisher();
        
        // List of properties should contain null only
        List<String> registeredProperties = defaultPublisher.getProperties();
        int expectedNrRegistered = 1;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property null not registered",registeredProperties.contains(null));
    }
    
    @Test
    public void testSubscribeLocalListenerPropA() {
        /**
         * Subscribe local property listener. Local listener will be subscribed 
         * to given property. In case given property is the null-String, the listener
         * will be subscribed to all properties.
         *
         * @param listener property listener to be subscribed
         * @param property null-String allowed
         */
        
        // Subscribe local property listener to propA 
        StubLocalPropertyListener listener = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(listener, "propA");
        
        // Listener should not have received any events yet
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events before inform",expectedNrEvents,actualNrEvents);
        
        // Inform subscribed listeners about propA
        // Note that listeners are informed asynchronously
        publisher.inform("propA", "propAold", "propAnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should have received exactly one event
        receivedEvents = listener.getReceivedEvents();
        expectedNrEvents = 1;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
        
        // Check old value of received event
        PropertyChangeEvent event = receivedEvents.get(0);
        String expectedString = "propAold";
        String actualString = (String) event.getOldValue();
        Assert.assertEquals("old value of event",expectedString,actualString);
        
        // Check new value of received event
        expectedString = "propAnew";
        actualString = (String) event.getNewValue();
        Assert.assertEquals("new value of event",expectedString,actualString); 
    }
    
    @Test
    public void testSubscribeLocalListenerNull() {
        /**
         * Subscribe local property listener. Local listener will be subscribed 
         * to given property. In case given property is the null-String, the listener
         * will be subscribed to all properties.
         *
         * @param listener property listener to be subscribed
         * @param property null-String allowed
         */
        
        // Subscribe local property listener to null-String 
        StubLocalPropertyListener listener = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(listener, null);
        
        // Listener should not have received any events yet
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events should be zero",expectedNrEvents,actualNrEvents);
        
        // Inform subscribed listeners about propA
        publisher.inform("propA", "propAold", "propAnew");
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should have received exactly two events
        receivedEvents = listener.getReceivedEvents();
        expectedNrEvents = 2;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events",expectedNrEvents,actualNrEvents);
        
        // Check old value of first received event
        PropertyChangeEvent event = receivedEvents.get(0);
        String expectedString = "propAold";
        String actualString = (String) event.getOldValue();
        Assert.assertEquals("old value of first event",expectedString,actualString);
        
        // Check new value of first received event
        expectedString = "propAnew";
        actualString = (String) event.getNewValue();
        Assert.assertEquals("new value of first event",expectedString,actualString);
        
        // Check old value of second received event
        event = receivedEvents.get(1);
        expectedString = "propBold";
        actualString = (String) event.getOldValue();
        Assert.assertEquals("old value of second event",expectedString,actualString);
        
        // Check new value of second received event
        expectedString = "propBnew";
        actualString = (String) event.getNewValue();
        Assert.assertEquals("new value of second event",expectedString,actualString); 
    }
    
    @Test
    public void testSubscribeLocalListenerConcurrent() {
        /**
         * Subscribe local property listener. Local listener will be subscribed 
         * to given property. In case given property is the null-String, the listener
         * will be subscribed to all properties.
         *
         * @param listener property listener to be subscribed
         * @param property null-String allowed
         */
        
         // Register 100 properties
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.registerProperty(property);
        }
        
        // Create 1000 local listeners 
        List<StubLocalPropertyListener> listeners = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            StubLocalPropertyListener listener = new StubLocalPropertyListener();
            listeners.add(listener);
        }
        
        // Subscribe local listeners to 100 properties concurrently
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            final int nr = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                     StubLocalPropertyListener listener = listeners.get(nr);
                     for (int j = 0; j < 10; j++) {
                        String property = "prop" + ((nr + j) % 100);
                        publisher.subscribeLocalListener(listener, property);
                     }
                }
                
            });
            threads[i].start();
        }
        
        // Wait till all threads are finished to ensure that all listeners are subscribed
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Inform listeners
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.inform(property, property + "old", property + "new");
        }
        
        // Wait some time to ensure that all listeners are informed
        wait(delay);
        
        // Each listener should have received exactly 10 events
        int expectedNrEvents = 10;
        for (StubLocalPropertyListener listener : listeners) {
            List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
            int actualNrEvents = receivedEvents.size();
            Assert.assertEquals("number of received events by listener",
                    expectedNrEvents,actualNrEvents);
        }
    }
    
    @Test
    public void testSubscribeRemoteListenerPropB() throws RemoteException {
        /**
         * Subscribe remote property listener. Remote listener will be subscribed 
         * to given property. In case given property is the null-String, the listener
         * will be subscribed to all properties.
         *
         * @param listener property listener to be subscribed
         * @param property null-String allowed
         */
        
        // Subscribe remote property listener to propB 
        StubRemotePropertyListener listener = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(listener, "propB");
        
        // Listener should not have received any events yet
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events before inform",expectedNrEvents,actualNrEvents);
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should have received exactly one event
        receivedEvents = listener.getReceivedEvents();
        expectedNrEvents = 1;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
        
        // Check old value of received event
        PropertyChangeEvent event = receivedEvents.get(0);
        String expectedString = "propBold";
        String actualString = (String) event.getOldValue();
        Assert.assertEquals("old value of event",expectedString,actualString);
        
        // Check new value of received event
        expectedString = "propBnew";
        actualString = (String) event.getNewValue();
        Assert.assertEquals("new value of event",expectedString,actualString); 
    }
    
    @Test
    public void testSubscribeRemoteListenerNull() throws RemoteException {
        /**
         * Subscribe remote property listener. Remote listener will be subscribed 
         * to given property. In case given property is the null-String, the listener
         * will be subscribed to all properties.
         *
         * @param listener property listener to be subscribed
         * @param property null-String allowed
         */
        
        // Subscribe remote property listener to null-String 
        StubRemotePropertyListener listener = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(listener, null);
        
        // Listener should not have received any events yet
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events should be zero",expectedNrEvents,actualNrEvents);
        
        // Inform subscribed listeners about propA
        publisher.inform("propA", "propAold", "propAnew");
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should have received exactly two events
        receivedEvents = listener.getReceivedEvents();
        expectedNrEvents = 2;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events",expectedNrEvents,actualNrEvents);
        
        // Check old value of first received event
        PropertyChangeEvent event = receivedEvents.get(0);
        String expectedString = "propAold";
        String actualString = (String) event.getOldValue();
        Assert.assertEquals("old value of first event",expectedString,actualString);
        
        // Check new value of first received event
        expectedString = "propAnew";
        actualString = (String) event.getNewValue();
        Assert.assertEquals("new value of first event",expectedString,actualString);
        
        // Check old value of second received event
        event = receivedEvents.get(1);
        expectedString = "propBold";
        actualString = (String) event.getOldValue();
        Assert.assertEquals("old value of second event",expectedString,actualString);
        
        // Check new value of second received event
        expectedString = "propBnew";
        actualString = (String) event.getNewValue();
        Assert.assertEquals("new value of second event",expectedString,actualString); 
    }
    
    @Test
    public void testSubscribeRemoteListenerConcurrent() throws RemoteException {
        /**
         * Subscribe remote property listener. Remote listener will be subscribed 
         * to given property. In case given property is the null-String, the listener
         * will be subscribed to all properties.
         *
         * @param listener property listener to be subscribed
         * @param property null-String allowed
         */
        
         // Register 100 properties
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.registerProperty(property);
        }
        
        // Create 1000 remote listeners 
        List<StubRemotePropertyListener> listeners = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            StubRemotePropertyListener listener = new StubRemotePropertyListener();
            listeners.add(listener);
        }
        
        // Subscribe remote listeners to 100 properties concurrently
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            final int nr = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                     StubRemotePropertyListener listener = listeners.get(nr);
                     for (int j = 0; j < 10; j++) {
                        String property = "prop" + ((nr + j) % 100);
                        publisher.subscribeRemoteListener(listener, property);
                     }
                }
                
            });
            threads[i].start();
        }
        
        // Wait till all threads are finished to ensure that all listeners are subscribed
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Inform listeners
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.inform(property, property + "old", property + "new");
        }
        
        // Wait some time to ensure that all listeners are informed
        wait(delay);
        
        // Each listener should have received exactly 10 events
        int expectedNrEvents = 10;
        for (StubRemotePropertyListener listener : listeners) {
            List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
            int actualNrEvents = receivedEvents.size();
            Assert.assertEquals("number of received events by listener",
                    expectedNrEvents,actualNrEvents);
        }
    }
    
    @Test
    public void testUnsubscribeLocalListenerPropA() {
        /**
         * Unsubscribe local or remote property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Subscribe local property listener to propA 
        StubLocalPropertyListener listener = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(listener, "propA");
        
        // Unsubscribe listener from propA
        publisher.unsubscribeLocalListener(listener, "propA");
        
        // Inform subscribed listeners about propA
        publisher.inform("propA", "propAold", "propAnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should not have received any events 
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
    }
    
    @Test
    public void testUnsubscribeLocalListenerNull() {
        /**
         * Unsubscribe local property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Subscribe local property listener to propA 
        StubLocalPropertyListener listener = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(listener, "propA");
        
        // Subscribe local property listener to propB 
        publisher.subscribeLocalListener(listener, "propB");
        
        // Unsubscribe listener from all properties
        publisher.unsubscribeLocalListener(listener, null);
        
        // Inform subscribed listeners about propA
        publisher.inform("propA", "propAold", "propAnew");
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should not have received any events 
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
    }
    
    @Test
    public void testUnsubscribeLocalListenerNotSubscribed() {
        /**
         * Unsubscribe local property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Do not subscribe local property listener to propA 
        StubLocalPropertyListener listener = new StubLocalPropertyListener();
        
        // Unsubscribe listener from propA (should not have any effect)
        publisher.unsubscribeLocalListener(listener, "propA");
        
        // Inform subscribed listeners about propA
        publisher.inform("propA", "propAold", "propAnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should not have received any events 
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
    }
    
    @Test
    public void testUnsubscribeLocalListenerPropertyNotRegistered() {
        /**
         * Unsubscribe local property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Property propC is not registered
        // Do not subscribe local property listener to propC 
        StubLocalPropertyListener listener = new StubLocalPropertyListener();
        
        // Unsubscribe listener from propC (should not have any effect)
        publisher.unsubscribeLocalListener(listener, "propC");
    }
    
    @Test
    public void testUnubscribeLocalListenerConcurrent() {
        /**
         * Unsubscribe local property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
         // Register 100 properties
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.registerProperty(property);
        }
        
        // Create 1000 local listeners and subscribe each to 10 properties
        List<StubLocalPropertyListener> listeners = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            StubLocalPropertyListener listener = new StubLocalPropertyListener();
            listeners.add(listener);
            for (int j = 0; j < 10; j++) {
                String property = "prop" + ((i + j) % 100);
                publisher.subscribeLocalListener(listener, property);
            }
        }
        
        // Inform listeners
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.inform(property, property + "old", property + "new");
        }
        
        // Wait some time to ensure that all listeners are informed
        wait(delay);
        
        // Each listener should have received exactly 10 events
        int expectedNrEvents = 10;
        for (StubLocalPropertyListener listener : listeners) {
            List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
            int actualNrEvents = receivedEvents.size();
            Assert.assertEquals("number of received events before listener was unsubscribed",
                    expectedNrEvents,actualNrEvents);
        }
        
        // Unsubscribe local listeners from all properties concurrently
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            final int nr = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                     StubLocalPropertyListener listener = listeners.get(nr);
                     for (int j = 0; j < 10; j++) {
                        String property = "prop" + ((nr + j) % 100);
                        publisher.unsubscribeLocalListener(listener, property);
                     }
                }
                
            });
            threads[i].start();
        }
        
        // Wait till all threads are finished to ensure that all listeners are unsubscribed
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Inform listeners
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.inform(property, property + "old", property + "new");
        }
        
        // Wait some time to ensure that all listeners are informed
        wait(delay);
        
        // Each listener should have received exactly 10 events
        expectedNrEvents = 10;
        for (StubLocalPropertyListener listener : listeners) {
            List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
            int actualNrEvents = receivedEvents.size();
            Assert.assertEquals("number of received events after listener was unsubscribed",
                    expectedNrEvents,actualNrEvents);
        }
    }
    
    @Test
    public void testUnsubscribeRemoteListenerPropB() throws RemoteException {
        /**
         * Unsubscribe remote property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Subscribe remote property listener to propB 
        StubRemotePropertyListener listener = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(listener, "propB");
        
        // Unsubscribe listener from propB
        publisher.unsubscribeRemoteListener(listener, "propB");
        
        // Inform subscribed listeners about propA
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should not have received any events 
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
    }
    
    @Test
    public void testUnsubscribeRemoteListenerNull() throws RemoteException {
        /**
         * Unsubscribe remote property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Subscribe remote property listener to propA 
        StubRemotePropertyListener listener = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(listener, "propA");
        
        // Subscribe remote property listener to propB 
        publisher.subscribeRemoteListener(listener, "propB");
        
        // Unsubscribe listener from all properties
        publisher.unsubscribeRemoteListener(listener, null);
        
        // Inform subscribed listeners about propA
        publisher.inform("propA", "propAold", "propAnew");
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should not have received any events 
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
    }
    
    @Test
    public void testUnsubscribeRemoteListenerNotSubscribed() throws RemoteException {
        /**
         * Unsubscribe remote property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Do not subscribe remote property listener to propB 
        StubRemotePropertyListener listener = new StubRemotePropertyListener();
        
        // Unsubscribe listener from propB (should not have any effect)
        publisher.unsubscribeRemoteListener(listener, "propB");
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener should not have received any events 
        List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
        int expectedNrEvents = 0;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events after inform",expectedNrEvents,actualNrEvents);
    }
    
    @Test
    public void testUnsubscribeRemoteListenerPropertyNotRegistered() throws RemoteException {
        /**
         * Unsubscribe remote property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
        // Property propC is not registered
        // Do not subscribe remote property listener to propC 
        StubRemotePropertyListener listener = new StubRemotePropertyListener();
        
        // Unsubscribe listener from propC (should not have any effect)
        publisher.unsubscribeRemoteListener(listener, "propC");
    }
    
    @Test
    public void testUnubscribeRemoteListenerConcurrent() throws RemoteException {
        /**
         * Unsubscribe remote property listener. Listener will be unsubscribed 
         * from given property. In case given property is the null-string, the listener
         * will be unsubscribed from all properties.
         * 
         * @param listener property listener to be unsubscribed
         * @param property null-String allowed
         */
        
         // Register 100 properties
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.registerProperty(property);
        }
        
        // Create 1000 remote listeners and subscribe each to 10 properties
        List<StubRemotePropertyListener> listeners = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            StubRemotePropertyListener listener = new StubRemotePropertyListener();
            listeners.add(listener);
            for (int j = 0; j < 10; j++) {
                String property = "prop" + ((i + j) % 100);
                publisher.subscribeRemoteListener(listener, property);
            }
        }
        
        // Inform listeners
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.inform(property, property + "old", property + "new");
        }
        
        // Wait some time to ensure that all listeners are informed
        wait(delay);
        
        // Each listener should have received exactly 10 events
        int expectedNrEvents = 10;
        for (StubRemotePropertyListener listener : listeners) {
            List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
            int actualNrEvents = receivedEvents.size();
            Assert.assertEquals("number of received events before listener was unsubscribed",
                    expectedNrEvents,actualNrEvents);
        }
        
        // Unsubscribe remote listeners from all properties concurrently
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            final int nr = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                     StubRemotePropertyListener listener = listeners.get(nr);
                     for (int j = 0; j < 10; j++) {
                        String property = "prop" + ((nr + j) % 100);
                        publisher.unsubscribeRemoteListener(listener, property);
                     }
                }
                
            });
            threads[i].start();
        }
        
        // Wait till all threads are finished to ensure that all listeners are unsubscribed
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Inform listeners
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.inform(property, property + "old", property + "new");
        }
        
        // Wait some time to ensure that all listeners are informed
        wait(delay);
        
        // Each listener should have received exactly 10 events
        expectedNrEvents = 10;
        for (StubRemotePropertyListener listener : listeners) {
            List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
            int actualNrEvents = receivedEvents.size();
            Assert.assertEquals("number of received events after listener was unsubscribed",
                    expectedNrEvents,actualNrEvents);
        }
    }
    
    @Test
    public void testInformPropA() throws RemoteException {
        /**
         * Inform all listeners subscribed to property. All listeners subscribed
         * to given property as well as all listeners subscribed to null-String
         * are informed of a change of given property through a (remote) method 
         * invocation of propertyChange(). In case given property is the null-String
         * all subscribed listeners are informed.
         *
         * @param property property is either null-String or is registered
         * @param oldValue original value of property at domain (null is allowed)
         * @param newValue new value of property at domain
         */
        
        // Subscribe local property listener to propA 
        StubLocalPropertyListener localListenerPropA = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(localListenerPropA, "propA");
        
        // Subscribe remote property listener to propB 
        StubRemotePropertyListener remoteListenerPropB = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(remoteListenerPropB, "propB");
        
        // Subscribe local property listener to null-String 
        StubLocalPropertyListener localListenerAllProps = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(localListenerAllProps, null);
        
        // Subscribe remote property listener to null-String 
        StubRemotePropertyListener remoteListenerAllProps = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(remoteListenerAllProps, null);
        
        // Inform subscribed listeners about propA
        publisher.inform("propA", "propAold", "propAnew");
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Local listener to propA should have received exactly one event
        List<PropertyChangeEvent> receivedEvents = localListenerPropA.getReceivedEvents();
        int expectedNrEvents = 1;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by local listener to propA",
                expectedNrEvents,actualNrEvents);
        
        // Remote listener to propB should have received exactly one event
        receivedEvents = remoteListenerPropB.getReceivedEvents();
        expectedNrEvents = 1;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by remote listener to propB",
                expectedNrEvents,actualNrEvents);
        
        // Local listener to all properties should have received exactly two events
        receivedEvents = localListenerAllProps.getReceivedEvents();
        expectedNrEvents = 2;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by local listener to all properties",
                expectedNrEvents,actualNrEvents);
        
        // Remote listener to all properties should have received exactly two events
        receivedEvents = remoteListenerAllProps.getReceivedEvents();
        expectedNrEvents = 2;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by remote listener to all properties",
                expectedNrEvents,actualNrEvents);   
    }
    
    @Test
    public void testInformNull() throws RemoteException {
        /**
         * Inform all listeners subscribed to property. All listeners subscribed
         * to given property as well as all listeners subscribed to null-String
         * are informed of a change of given property through a (remote) method 
         * invocation of propertyChange(). In case given property is the null-String
         * all subscribed listeners are informed.
         *
         * @param property property is either null-String or is registered
         * @param oldValue original value of property at domain (null is allowed)
         * @param newValue new value of property at domain
         */
        
        // Subscribe local property listener to propA 
        StubLocalPropertyListener listenerPropA = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(listenerPropA, "propA");
        
        // Subscribe remote property listener to propB 
        StubRemotePropertyListener listenerPropB = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(listenerPropB, "propB");
        
        // Subscribe local property listener to null-String 
        StubLocalPropertyListener listenerAllProps = new StubLocalPropertyListener();
        publisher.subscribeLocalListener(listenerAllProps, null);
        
        // Inform all subscribed listeners
        publisher.inform(null, "propOldVal", "propNewVal");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Listener to propA should have received exactly one event
        List<PropertyChangeEvent> receivedEvents = listenerPropA.getReceivedEvents();
        int expectedNrEvents = 1;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by listener to propA",expectedNrEvents,actualNrEvents);
        
        // Listener to propB should have received exactly one event
        receivedEvents = listenerPropB.getReceivedEvents();
        expectedNrEvents = 1;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by listener to propB",expectedNrEvents,actualNrEvents);
        
        // Listener to all properties should have received exactly one events
        receivedEvents = listenerAllProps.getReceivedEvents();
        expectedNrEvents = 1;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by listener to all properties",expectedNrEvents,actualNrEvents);   
    }
    
    @Test (expected = RuntimeException.class)
    public void testInformPropertyNotRegistered() throws RemoteException {
        /**
         * Inform all listeners subscribed to property. All listeners subscribed
         * to given property as well as all listeners subscribed to null-String
         * are informed of a change of given property through a (remote) method 
         * invocation of propertyChange(). In case given property is the null-String
         * all subscribed listeners are informed.
         *
         * @param property property is either null-String or is registered
         * @param oldValue original value of property at domain (null is allowed)
         * @param newValue new value of property at domain
         */
        
        // Property propC is not registered
        // Inform subscribed listeners about propC
        publisher.inform("propC", "propCold", "propCnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
    }
    
    @Test
    public void testInformRemoteListenerNotConnectedShortDelay() {
        /**
         * Inform all listeners subscribed to property. All listeners subscribed
         * to given property as well as all listeners subscribed to null-String
         * are informed of a change of given property through a (remote) method 
         * invocation of propertyChange(). In case given property is the null-String
         * all subscribed listeners are informed.
         *
         * @param property property is either null-String or is registered
         * @param oldValue original value of property at domain (null is allowed)
         * @param newValue new value of property at domain
         */
        
        // Subscribe three remote property listeners to propB 
        StubRemotePropertyListener remoteListenerPropBOne = null;
        StubRemotePropertyListener remoteListenerPropBTwo = null;
        StubRemotePropertyListener remoteListenerPropBThree = null;
        try {
            remoteListenerPropBOne = new StubRemotePropertyListener();
            remoteListenerPropBTwo = new StubRemotePropertyListener();
            remoteListenerPropBThree = new StubRemotePropertyListener();
            publisher.subscribeRemoteListener(remoteListenerPropBOne, "propB");
            publisher.subscribeRemoteListener(remoteListenerPropBTwo, "propB");
            publisher.subscribeRemoteListener(remoteListenerPropBThree, "propB");
        } catch (RemoteException ex) {
            Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (remoteListenerPropBOne != null && remoteListenerPropBTwo != null
                && remoteListenerPropBThree != null) {
            
            // Simulate that second remote property listener is disconnected from the internet
            // Disconnection from the internet should not affect informing the other two listeners
            // Delay is 10 ms
            remoteListenerPropBTwo.setSimulateDisconnect(10);

            // Inform subscribed listeners about propB
            publisher.inform("propB", "propBold", "propBnew");
            
            // Wait some time to ensure that all listeners have been informed
            wait(delay);

            // Remote property listener One should have received exactly one event
            try {
                List<PropertyChangeEvent> receivedEvents = remoteListenerPropBOne.getReceivedEvents();
                int expectedNrEvents = 1;
                int actualNrEvents = receivedEvents.size();
                Assert.assertEquals("number of received events by remote listener One",
                        expectedNrEvents, actualNrEvents);
            } catch (RemoteException ex) {
                Assert.fail("remote property listener One has thrown a RemoteException");
            }
            
            // Remote property listener Three should have received exactly one event
            try {
                List<PropertyChangeEvent> receivedEvents = remoteListenerPropBThree.getReceivedEvents();
                int expectedNrEvents = 1;
                int actualNrEvents = receivedEvents.size();
                Assert.assertEquals("number of received events by remote listener Three",
                        expectedNrEvents, actualNrEvents);
            } catch (RemoteException ex) {
                Assert.fail("remote property listener Three has thrown a RemoteException");
            }
        }
        else {
            Assert.fail("remote listeners not initialized");
        }
    }
    
    @Test (timeout = 2000)
    public void testInformRemoteListenerNotConnectedLongDelay() throws RemoteException {
        /**
         * Inform all listeners subscribed to property. All listeners subscribed
         * to given property as well as all listeners subscribed to null-String
         * are informed of a change of given property through a (remote) method 
         * invocation of propertyChange(). In case given property is the null-String
         * all subscribed listeners are informed.
         *
         * @param property property is either null-String or is registered
         * @param oldValue original value of property at domain (null is allowed)
         * @param newValue new value of property at domain
         */
        
        // Subscribe three remote property listeners to propB 
        StubRemotePropertyListener remoteListenerPropBOne = new StubRemotePropertyListener();
        StubRemotePropertyListener remoteListenerPropBTwo = new StubRemotePropertyListener();
        StubRemotePropertyListener remoteListenerPropBThree = new StubRemotePropertyListener();
        publisher.subscribeRemoteListener(remoteListenerPropBOne, "propB");
        publisher.subscribeRemoteListener(remoteListenerPropBTwo, "propB");
        publisher.subscribeRemoteListener(remoteListenerPropBThree, "propB");
        
        // Simulate that second remote property listener is disconnected from the internet
        // Disconnection from the internet should not affect informing the other two listeners
        // Delay is two minutes (120000 ms)
        remoteListenerPropBTwo.setSimulateDisconnect(120000);
        
        // Inform subscribed listeners about propB
        publisher.inform("propB", "propBold", "propBnew");
        
        // Wait some time to ensure that all listeners have been informed
        wait(delay);
        
        // Remote property listener One should have received exactly one event
        List<PropertyChangeEvent> receivedEvents = remoteListenerPropBOne.getReceivedEvents();
        int expectedNrEvents = 1;
        int actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by remote listener One",
                expectedNrEvents,actualNrEvents);
        
        // Remote property listener Three should have received exactly one event
        receivedEvents = remoteListenerPropBThree.getReceivedEvents();
        expectedNrEvents = 1;
        actualNrEvents = receivedEvents.size();
        Assert.assertEquals("number of received events by remote listener Three",
                expectedNrEvents,actualNrEvents);
    }
    
    @Test
    public void testInformConcurrent() {
        /**
         * Inform all listeners subscribed to property. All listeners subscribed
         * to given property as well as all listeners subscribed to null-String
         * are informed of a change of given property through a (remote) method 
         * invocation of propertyChange(). In case given property is the null-String
         * all subscribed listeners are informed.
         *
         * @param property property is either null-String or is registered
         * @param oldValue original value of property at domain (null is allowed)
         * @param newValue new value of property at domain
         */
        
        // Register 100 properties
        for (int i = 0; i < 100; i++) {
            String property = "prop" + i;
            publisher.registerProperty(property);
        }
        
        // Subscribe 1000 listeners to 100 properties
        List<StubLocalPropertyListener> listeners = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String property = "prop" + (i % 100);
            StubLocalPropertyListener listener = new StubLocalPropertyListener();
            publisher.subscribeLocalListener(listener, property);
        }
        
        // Inform listeners concurrently
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            final int nr = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                     String property = "prop" + nr;
                     publisher.inform(property, property + "old", property + "new");
                }
                
            });
            threads[i].start();
        }
        
        // Wait till all threads are finished to ensure that all listeners are informed
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Each listener should have received exactly 10 events
        int expectedNrEvents = 10;
        for (StubLocalPropertyListener listener : listeners) {
            List<PropertyChangeEvent> receivedEvents = listener.getReceivedEvents();
            int actualNrEvents = receivedEvents.size();
            Assert.assertEquals("number of received events by listener",
                    expectedNrEvents,actualNrEvents);
        }
    }
    
    @Test
    public void testGetProperties() {
        /**
         * Obtain all registered properties. An unmodifiable list all properties
         * including the null property is returned.
         * 
         * @return list of registered properties including null
         */
        
        // Properties propA and propB are already registered
        // List of properties should contain propA, propB, and null
        List<String> registeredProperties = publisher.getProperties();
        int expectedNrRegistered = 3;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property propA not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propB not registered",registeredProperties.contains("propB"));
        Assert.assertTrue("property null not registered",registeredProperties.contains(null));
    }
    
    @Test
    public void testRegisterPropertySuccesful() {
        /**
         * Register property. Register property at this publisher. From now on
         * listeners can subscribe to this property. Nothing changes in case given
         * property was already registered. 
         *
         * @param property empty string not allowed
         */
        
        // Register property propC
        publisher.registerProperty("propC");
        
        // Properties propA and propB are already registered
        // List of properties should contain propA, propB, propC, and null
        List<String> registeredProperties = publisher.getProperties();
        int expectedNrRegistered = 4;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property propA not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propB not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propC not registered",registeredProperties.contains("propC"));
        Assert.assertTrue("property null not registered",registeredProperties.contains(null)); 
    }
    
    @Test
    public void testRegisterPropertyTwice() {
        /**
         * Register property. Register property at this publisher. From now on
         * listeners can subscribe to this property. Nothing changes in case given
         * property was already registered. 
         *
         * @param property empty string not allowed
         */
        
        // Register property propC twice
        publisher.registerProperty("propC");
        publisher.registerProperty("propC");
        
        // Properties propA and propB are already registered
        // List of properties should contain propA, propB, propC, and null
        List<String> registeredProperties = publisher.getProperties();
        int expectedNrRegistered = 4;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property propA not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propB not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propC not registered",registeredProperties.contains("propC"));
        Assert.assertTrue("property null not registered",registeredProperties.contains(null)); 
    } 
    
    @Test
    public void testRegisterPropertyConcurrent() {
        /**
         * Register property. Register property at this publisher. From now on
         * listeners can subscribe to this property. Nothing changes in case given
         * property was already registered. 
         *
         * @param property empty string not allowed
         */
        
        // Register 1000 properties concurrently
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            final int nr = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                     String property = "propconcurrent" + nr;
                     publisher.registerProperty(property);
                }
                
            });
            threads[i].start();
        }
        
        // Wait till all threads are finished to ensure that all properties are registered
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Properties propA and propB are already registered
        // List of properties should contain propA, propB, null and a thousand more
        List<String> registeredProperties = publisher.getProperties();
        int expectedNrRegistered = 1003;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property propA not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propB not registered",registeredProperties.contains("propB"));
        Assert.assertTrue("property null not registered",registeredProperties.contains(null)); 
        for (int i = 0; i < 1000; i++) {
            String property = "propconcurrent" + i;
            Assert.assertTrue("property " + property + " not registered",
                    registeredProperties.contains(property));
        }
    } 
    
    @Test (expected = RuntimeException.class)
    public void testRegisterPropertyNull() {
        /**
         * Register property. Register property at this publisher. From now on
         * listeners can subscribe to this property. Nothing changes in case given
         * property was already registered. 
         *
         * @param property empty string not allowed
         */
        
        // Register null-String
        String nullString = null;
        publisher.registerProperty(nullString);
    }
    
    @Test (expected = RuntimeException.class)
    public void testRegisterPropertyEmptyString() {
        /**
         * Register property. Register property at this publisher. From now on
         * listeners can subscribe to this property. Nothing changes in case given
         * property was already registered. 
         *
         * @param property empty string not allowed
         */
        
        // Register empty string
        String empty = "";
        publisher.registerProperty(empty);
    }
    
    @Test
    public void testUnregisterPropertySuccesful() {
        /**
         * Unregister property. Unregister property at this publisher. From now on
         * listeners subscribed to this property will not be informed on changes.
         * In case given property is null-String, all properties (except null) will
         * be unregistered.
         *
         * @param property registered property at this publisher
         */
        
        // Register property propC
        publisher.registerProperty("propC");
        
        // Unregister property propC
        publisher.unregisterProperty("propC");
        
        // Properties propA and propB are already registered
        // List of properties should contain propA, propB, and null, but not propC
        List<String> registeredProperties = publisher.getProperties();
        int expectedNrRegistered = 3;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property propA not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propB not registered",registeredProperties.contains("propB"));
        Assert.assertTrue("property null not registered",registeredProperties.contains(null));
        Assert.assertFalse("property propC registered",registeredProperties.contains("propC"));
    }
    
    @Test
    public void testUnregisterPropertyAllProperties() {
        /**
         * Unregister property. Unregister property at this publisher. From now on
         * listeners subscribed to this property will not be informed on changes.
         * In case given property is null-String, all properties (except null) will
         * be unregistered.
         *
         * @param property registered property at this publisher
         */
        
        // Properties propA and propB are already registered
        // Register property propC
        publisher.registerProperty("propC");
        
        // Unregister all properties
        publisher.unregisterProperty(null);
        
        // List of properties should only contain null
        List<String> registeredProperties = publisher.getProperties();
        int expectedNrRegistered = 1;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property null not registered",registeredProperties.contains(null));
        Assert.assertFalse("property propA registered",registeredProperties.contains("propA"));
        Assert.assertFalse("property propB registered",registeredProperties.contains("propB"));
        Assert.assertFalse("property propC registered",registeredProperties.contains("propC"));
    }
    
    @Test
    public void testUnregisterPropertyConcurrent() {
        /**
         * Unregister property. Unregister property at this publisher. From now on
         * listeners subscribed to this property will not be informed on changes.
         * In case given property is null-String, all properties (except null) will
         * be unregistered.
         *
         * @param property registered property at this publisher
         */
        
        // Register 1000 properties
        for (int i = 0; i < 1000; i++) {
            String property = "prop" + i;
            publisher.registerProperty(property);
        }
        
        // Properties propA and propB are already registered
        // List of properties should contain propA, propB, null and a thousand more
        List<String> registeredProperties = publisher.getProperties();
        int expectedNrRegistered = 1003;
        int actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property propA not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propB not registered",registeredProperties.contains("propB"));
        Assert.assertTrue("property null not registered",registeredProperties.contains(null)); 
        for (int i = 0; i < 1000; i++) {
            String property = "propconcurrent" + i;
            Assert.assertFalse("property " + property + " not registered",
                    registeredProperties.contains(property));
        }
        
        // Unregister 1000 properties concurrently
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            final int nr = i;
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                     String property = "prop" + nr;
                     publisher.unregisterProperty(property);
                }
                
            });
            threads[i].start();
        }
        
        // Wait till all threads are finished to ensure that all properties are unregistered
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(PublisherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Properties propA and propB are already registered
        // List of properties should contain propA, propB, and null 
        registeredProperties = publisher.getProperties();
        expectedNrRegistered = 3;
        actualNrRegistered = registeredProperties.size();
        Assert.assertEquals("number of registered properties",expectedNrRegistered,actualNrRegistered);
        Assert.assertTrue("property propA not registered",registeredProperties.contains("propA"));
        Assert.assertTrue("property propB not registered",registeredProperties.contains("propB"));
        Assert.assertTrue("property null not registered",registeredProperties.contains(null)); 
        for (int i = 0; i < 1000; i++) {
            String property = "prop" + i;
            Assert.assertFalse("property " + property + " is still registered",
                    registeredProperties.contains(property));
        }
    } 
    
    @Test (expected = RuntimeException.class)
    public void testUnregisterPropertyTwice() {
        /**
         * Unregister property. Unregister property at this publisher. From now on
         * listeners subscribed to this property will not be informed on changes.
         * In case given property is null-String, all properties (except null) will
         * be unregistered.
         *
         * @param property registered property at this publisher
         */
        
        // Register property propC
        publisher.registerProperty("propC");
        
        // Unregister property propC for the first time
        publisher.unregisterProperty("propC");
        
        // Unregister property propC for the second time
        publisher.unregisterProperty("propC");
    }
    
    @Test (expected = RuntimeException.class)
    public void testUnregisterPropertyNotRegistered() {
        /**
         * Unregister property. Unregister property at this publisher. From now on
         * listeners subscribed to this property will not be informed on changes.
         * In case given property is null-String, all properties (except null) will
         * be unregistered.
         *
         * @param property registered property at this publisher
         */
        
        // Unregister property propC (not registered)
        publisher.unregisterProperty("propC");
    }
    
    @Test (expected = RuntimeException.class)
    public void testUnregisterPropertyEmptyString() {
        /**
         * Unregister property. Unregister property at this publisher. From now on
         * listeners subscribed to this property will not be informed on changes.
         * In case given property is null-String, all properties (except null) will
         * be unregistered.
         *
         * @param property registered property at this publisher
         */
        
        // Unregister empty string
        String empty = "";
        publisher.unregisterProperty(empty);
    }
}
