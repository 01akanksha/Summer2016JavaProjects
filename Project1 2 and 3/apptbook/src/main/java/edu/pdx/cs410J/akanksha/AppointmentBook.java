package edu.pdx.cs410J.akanksha;


import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by A on 7/2/2016.
 */
public class AppointmentBook extends AbstractAppointmentBook {
    ArrayList<Appointment> list= new ArrayList<Appointment>(); ;
    AbstractAppointment a;
    String owner;

    /**
     * arameterized Constructor
     * @param owner of the appointment
     */
    public AppointmentBook(String owner)
    {
        this.owner=owner;
    }

    /**
     * Parameterized Constructor
     * @param owner of the appointment
     * @param a appointment that sis to be booked for this owner
     */
    public AppointmentBook(String owner, Appointment a)
    {

        this.a=a;
        this.owner=owner;
        addAppointment(a);
    }
/**Overrided methods from Super class*/
    @Override
    public String getOwnerName()
    {
        return owner;
    }
    @Override
    public Collection<Appointment> getAppointments()
    {
        return list;
    }

    @Override
    public void addAppointment(AbstractAppointment var1)
    {
        list.add((Appointment) var1);
        Collections.sort(list);
    }
}
