package edu.pdx.cs410J.akanksha.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import edu.pdx.cs410J.AbstractAppointment;

import java.util.Date;

/**
 * Appoinment Class having logic for description,begin and end time validation
 */

public class Appointment extends AbstractAppointment implements Comparable<Appointment>
{

    String description;
    public Date beginTime;
    public Date endTime;
    private boolean flag;

    /**
     * Parameterised constructor for Appointments
     * @param startTime begin time of the appointment
     * @param endTime end time of the appointment
     * @param description description of the appointment
     */
    public Appointment(String startTime, String endTime,String description){
        flag = false;
        //Check for bad data
        try{
            if(startTime.contains("\"")||endTime.contains("\""))
                throw new IllegalArgumentException("Date and time cannot contain quotes ");

            String[] tempStart = startTime.split(" ");
            String[] tempEnd= endTime.split(" ");
            if(!tempStart[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!tempEnd[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")) {
                throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
            }

            if(!tempStart[1].matches("(1[0-2]|0?[1-9]):([0-5]?[0-9])")||!tempEnd[1].matches("(1[0-2]|0?[1-9]):([0-5]?[0-9])"))
                throw new IllegalArgumentException("Time format must follow mm:hh (12 hour time)");
            if(!tempStart[2].matches("(am|pm|AM|PM)")&&!tempEnd[2].matches("(am|pm|AM|PM)"))
                throw new IllegalArgumentException("Time must include am/pm");
        }
        catch(IllegalArgumentException ex) {
            Window.alert(ex.getMessage());
            flag = true;
            return;
        }
        this.description = description;
        setDate(startTime,endTime);
    }

    /**
     * Default constructor of Appointment setting all values to null
     */
    public Appointment(){
        flag = false;
        try {
            description = "";
            beginTime =null;
            endTime = null;
        }
        catch(Exception ex){
            Window.alert(ex.getMessage());
            flag = true;
            return;
        }
    }

    /**
     * Formatting the passed String formatted Date to the Date format
     * @param start Begin Time in String Format
     * @param end End time in String format
     */
    public void setDate(String start, String end){
        flag = false;
        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
        try {
            this.beginTime=ShortDateFormat.parse(start);
            this.endTime = ShortDateFormat.parse(end);
        }
        catch(Exception ex){
            Window.alert("Error Parsing the time, please enter valid time, dont forget to include am/pm " + ex.getMessage());
            flag = true;
            return;
        }
    }

    /**
     *Overidden method for the Begintime to check format
     * @return begin time if correct format and blank if wrong format
     */
    @Override
    public String getBeginTimeString()
    {
        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
        if(beginTime != null)
            return (ShortDateFormat.format(beginTime));
        else
            return "";
    }
    /**
     *Overidden method for the Endtime to check format and return
     * @return end time if correct format and blank if wrong format
     */
    @Override
    public String getEndTimeString()
    {
        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
        if(endTime != null)
            return (ShortDateFormat.format(endTime));
        else
            return "";
    }

    /**
     * Calculates duration of the appointment
     * @return duration of the appointment in minutes
     */
    public String duration(){
        long duration =beginTime.getTime()-endTime.getTime();
        long diffMinutes = duration / (60 * 1000);
        long diffHours = duration / (60 * 60 * 1000);

        return String.valueOf(-1*diffMinutes) +" "+ "minutes";

    }

    /**
     * To comapre appointment details to sort the appointment in the appontment book
     * @param appt appointment with which we have to make comparision
     * @return 0,1,-1 depending on the result of the comparision
     */
    @Override
    public int compareTo(Appointment appt) {
        long diffBegTime, diffEndTime = 0;
        int diffdes = 0;
        diffdes = this.getDescription().compareTo(appt.getDescription());
        diffBegTime = this.getBeginTime().compareTo(appt.getBeginTime());
        diffEndTime = this.getEndTime().compareTo(appt.getEndTime());
        if (diffBegTime > 0) {
            return 1;
        }
        if (diffBegTime < 0) {
            return -1;
        }
        if (diffBegTime == 0) {
            if (diffEndTime > 0) {
                return 1;
            }
            if (diffEndTime < 0) {
                return -1;
            }
            if (diffEndTime == 0) {
                if (diffdes > 0) {
                    return 1;
                }
                if (diffdes < 0) {
                    return -1;
                }
            }
        }
        return 0;
    }

    /**
     * return begin time in date format
     * @return begin time
     */

    @Override
    public Date getEndTime()
    {
        return endTime;
    }

    /**
     * returns end time in date format
     * @return end time
     */
    @Override
    public String getDescription()
    {
        return description;
    }

    /**
     * returns description
     * @return description
     */
    @Override
    public Date getBeginTime()
    {
        return beginTime;
    }

    /**
     * returns true or false set in the functions to determine whether exeption is thrown or not
     * @return flag value(true/false)
     */
    public boolean exceptionWasThrown(){
        return flag;
    }
}
