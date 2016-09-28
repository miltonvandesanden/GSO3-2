/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.Appointment;

import fontys.time.ITimeSpan;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Stefan
 */
public class Appointment
{
    private String subject;
    
    private ITimeSpan timeSpan;
    private List<Contact> invitees;
    
    /**
     *
     * @param subject can be empty
     * @param timeSpan
     */
    public Appointment(String subject, ITimeSpan timeSpan)
    {
        if(subject == null)
        {
            throw new IllegalArgumentException("subject cannot be null");
        }
        
        if(subject.isEmpty())
        {
            throw new IllegalArgumentException("subject cannot be empty");
        }
        
        if(timeSpan == null)
        {
            throw new IllegalArgumentException("timeSpan cannot be null");
        }
        
        this.subject = subject;
        this.timeSpan = timeSpan;
        
        invitees = new ArrayList<>();
    }
    
    /**
     *
     * @return subject
     */
    public String getSubject()
    {
        return subject;
    }
    
    /**
     *
     * @return timeSpan
     */
    public ITimeSpan getTimeSpan()
    {
        return timeSpan;
    }
    
    /**
     *
     * @return Iterator of invitees
     */
    public Iterator<Contact> invitees()
    {
        return invitees.iterator();
    }
    
    /**
     * adds this to contact.agenda and contact to this.invitees unless any timeSpan of contact.agenda conflicts with this.timeSpan
     * @param contact
     * @return true if contact successfully added to this and this succesfully added to contact.agenda, otherwise returns false
     */
    public boolean addContact(Contact contact)
    {
        if(contact.addAppointment(this))
        {
            invitees.add(contact);
            return true;
        }
        
        return false;
    }
    
    /**
     * Removes an existing contact from invitees, represented by the parameter contact
     * @param contact
     */
    public void removeContact(Contact contact)
    {
        invitees.remove(contact);
    }
}
