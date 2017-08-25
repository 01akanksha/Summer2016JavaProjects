package edu.pdx.cs410J.akanksha.client;

import com.google.gwt.user.client.Window;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Appointment book class extending AbstractAppointmentBook class
 */
public class AppointmentBook extends AbstractAppointmentBook<Appointment>
{

    String owner;
    ArrayList<Appointment> appt;
    Appointment searchApptOnly;
    Appointment singleAppt;
    //private Collection<Appointment> appts = new ArrayList<>();

    /**
     * Parameterised constructor
     * @param owner of the appointment
     * @param appnt to be added to the book
     */
    public AppointmentBook(String owner, Appointment appnt)
    {
        this.owner = owner;
        appt = new ArrayList<Appointment>();
        addAppointment(appnt);
        searchApptOnly=null;
        singleAppt = null;
    }

    /**
     * paramterised constructor
     * @param owner of the appointment
     */
    public AppointmentBook(String owner)
    {
        this.owner = owner;
        appt = new ArrayList<Appointment>();
        searchApptOnly=null;
        singleAppt = null;
    }

    /**
     * default constructor to initialise all to null
     */
    public AppointmentBook()
    {
        //Create an empty phonebill
        owner = "";
        appt = new ArrayList<Appointment>();
        searchApptOnly=null;
        singleAppt = null;
    }

    /**
     * Parameterised Constructor
     * @param owner owner of the appointment
     * @param tempAppointmetn appointment currently added
     * @param s to determine if there is search or a new appointment addition
     */
    public AppointmentBook(String owner, Appointment tempAppointmetn, String s) {
        if(s.equals("-search")) {
            this.owner = owner;
            appt = null;
            searchApptOnly = tempAppointmetn;
        }
        if(s.equals("-single")) {
            this.owner = owner;
            appt = null;
            singleAppt = tempAppointmetn;
        }
    }

    /**
     * returns owner of the appointment
     * @return owner name
     */
    @Override
    public String getOwnerName()
    {
        return owner;
    }

    /**
     * To get all the appoinments in the particular appointment book.
     * @return collection of appointment in the book
     */
    @Override
    public Collection<Appointment> getAppointments()
    {
        return appt;
    }

    /**
     * adds appointment to the appointment book
     * @param apptmnt appointment to be added in the appointment book
     */
    @Override
    public void addAppointment( Appointment apptmnt )
    {
        appt.add((Appointment) apptmnt);
        Collections.sort(appt);
    }

}
