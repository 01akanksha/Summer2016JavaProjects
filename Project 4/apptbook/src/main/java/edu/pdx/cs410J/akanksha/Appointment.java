package edu.pdx.cs410J.akanksha;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Appointment extends AbstractAppointment implements java.lang.Comparable<AbstractAppointment>  {

  Date begTime,enTime;
  String description;
  SimpleDateFormat ft;

  public Appointment() {
    ft = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
    try {
      description="";
      begTime = null;
      enTime = null;
    }catch(Exception e){
      System.out.println("e");
      System.exit(1);
    }
  }
  public void setDateTime(String startDate,String endDate)
  {
    ft = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
    try{
      begTime=ft.parse(startDate);
      enTime=ft.parse(endDate);
    }catch(java.text.ParseException e){
      System.out.println("Wrong Format of Date");
    }
  }

  @Override public int compareTo(AbstractAppointment a) {
    long diffBegTime, diffEndTime = 0;
    int diffdes = 0;
    diffdes = this.getDescription().compareTo(a.getDescription());
    diffBegTime = this.getBeginTime().compareTo(a.getBeginTime());
    diffEndTime = this.getEndTime().compareTo(a.getEndTime());
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
   * Parameterized constructor;
   * @param begintime of appointment
   * @param endTime of appointment
   * @param description of appointment
     */
  public Appointment(String begintime,String endTime,String description)
  {
    //System.out.println(begintime + "  " + endTime);
    ft = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
    try{
      begTime=ft.parse(begintime);
      enTime=ft.parse(endTime);
    }catch(java.text.ParseException e){
      System.out.println("Wrong Format of Date");
    }
    this.description=description;
  }
  @Override
  public Date getBeginTime() {
    return begTime;
  }
@Override
  public Date getEndTime() {
    return enTime;
  }

/**overrided methods from super class*/
  @Override
  public String getBeginTimeString() {
    return ft.format(begTime);
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getEndTimeString() {
    return ft.format(enTime);
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDescription() {
    return description;
    //return "This method is not implemented yet";
  }
  public long getDuration(String dateStart,String dateStop) throws java.text.ParseException
  {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a"); //D r

    Date d1 = null;
    Date d2 = null;
    d1 = format.parse(dateStart);
    d2 = format.parse(dateStop);
    // Get msec from each, and subtract.
    long diff = d2.getTime() - d1.getTime();
    //int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
    //System.out.println(diffDays);
    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
    //long diffMinutes = diff / (60 * 1000) % 60;
    return diffInMinutes;
  }
}

