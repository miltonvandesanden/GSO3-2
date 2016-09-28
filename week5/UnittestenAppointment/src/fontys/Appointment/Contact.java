/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.Appointment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Stefan
 */
public class Contact
{
    private String name;
    private List<Appointment> agenda;
    
    /**
     *
     * @param name cannot be empty
     */
    public Contact(String name)
    {
        if(name == null)
        {
            throw new IllegalArgumentException("name cannot be null");
        }
        
        if(name.isEmpty())
        {
            throw new IllegalArgumentException("name may not be empty!");
        }
        
        this.name = name;
        
        agenda = new ArrayList<>();
    }
    
    /**
     * 
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * adds appointment to this.agenda if appointment doesnt overlap with any appointment of this.agenda
     * @param appointment
     * @return true if appointment is successfully added, otherwise false
     */
    protected boolean addAppointment(Appointment appointment)
    {
        for(Appointment a : agenda)
        {
            if(a.getTimeSpan().intersectionWith(appointment.getTimeSpan()) != null)
            {
                return false;
            }
        }
        agenda.add(appointment);
        return true;
    }
    
    /**
     * removes appointment from this.agenda
     * @param appointment
     */
    protected void removeAppointment(Appointment appointment)
    {
        agenda.remove(appointment);
    }
    
    /**
     *
     * @return Iterator<Appointment> of agenda
     */
    public Iterator<Appointment> appointments()
    {
        return agenda.iterator();
    }
}