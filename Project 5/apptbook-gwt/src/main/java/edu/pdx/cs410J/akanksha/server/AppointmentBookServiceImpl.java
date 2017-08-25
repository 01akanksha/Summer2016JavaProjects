package edu.pdx.cs410J.akanksha.server;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.akanksha.client.Appointment;
import edu.pdx.cs410J.akanksha.client.AppointmentBook;
import edu.pdx.cs410J.akanksha.client.AppointmentBookService;

import java.util.*;

/**
 * The server-side implementation of the division service
 */
public class AppointmentBookServiceImpl extends RemoteServiceServlet implements AppointmentBookService
{

  private final Map<String, AppointmentBook> data = new HashMap<String, AppointmentBook>();
  AppointmentBook apptbook;

  /**
   * server side implementation of add to add appointments to the appointment book
   * @param owner of the appointment
   * @param appt appointment to be added
     */
  @Override
  public void add(String owner, Appointment appt) {
    if(data.get(owner)!=null){
      apptbook=data.get(owner);
      apptbook.addAppointment(appt);
      data.put(owner, apptbook);
    }
    else{
      apptbook= new AppointmentBook(owner,appt);
      data.put(owner, apptbook);
    }
  }

  /**
   * server side implementation of print to pretty print the all the appointments
   * @return all the appointment as hash map
     */
  @Override
  public Map<String, AppointmentBook> print() {
    return data;
  }

  /**
   * server side implementation of search
   * @param owner whose appointment book is to be searched
   * @param startTime time from which appointment is to be searched
   * @param endTime time till which appointment is to be searched
     * @return list of the appointments satisfying the search criteria
     */
  @Override
  public List<Appointment> search(String owner, String startTime,String endTime) {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyy hh:mm a",formatInfo){};
    Long begin,end;
    try {
      Date search = ShortDateFormat.parse(startTime.trim());
      begin = search.getTime();
      Date search1 = ShortDateFormat.parse(endTime.trim());
      end = search1.getTime();
    }
    catch(Exception ex){
      System.out.println("Error Parsing "+ ex);
      return null;
    }
    List<Appointment> data = new ArrayList<Appointment>();
    if(this.data.isEmpty())
      return null;

    apptbook = this.data.get(owner);
    if(apptbook == null)
      return null;
    Collection<Appointment>appnts = this.data.get(owner).getAppointments();

    for(Appointment appnt: appnts){
      if(begin<= (Long)appnt.beginTime.getTime()&&begin<=(Long)appnt.endTime.getTime()) {
        if (end >= (Long) appnt.beginTime.getTime() && end >= (Long) appnt.endTime.getTime()) {
          data.add(appnt);
        }
      }
    }
    return data;
  }

  /**
   *server side implementation of createAppointment book
   * @return appointment book
   */
  @Override
  public AppointmentBook createAppointmentBook() {
    AppointmentBook phonebill = new AppointmentBook();
    phonebill.addAppointment(new Appointment());
    return phonebill;
  }

  /**
   * to print stack trace of the error
   * @param unhandled whatever exception is thrown
     */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

}
