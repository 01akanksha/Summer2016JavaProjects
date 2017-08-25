package edu.pdx.cs410J.akanksha.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.List;
import java.util.Map;

/**
 * A GWT remote service that returns a dummy appointment book
 */
@RemoteServiceRelativePath("appointments")
public interface AppointmentBookService extends RemoteService {

 /**Methods in the service*/

  public void add(String owner ,Appointment appt);

  public Map<String,AppointmentBook> print();

  public List<Appointment> search(String owner, String startTime,String endTime);

  public AbstractAppointmentBook createAppointmentBook();
}
