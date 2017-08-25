package edu.pdx.cs410J.akanksha.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.List;
import java.util.Map;
/**
 * The client-side interface to the ping service
 */
public interface AppointmentBookServiceAsync {
  /**service methods*/
  public void add(String owner ,Appointment call,AsyncCallback<Void> async);

  public void print(AsyncCallback<Map<String, AppointmentBook>> async);

  public void search(String owner, String startTime,String endTime,AsyncCallback<List<Appointment>>async);

  void createAppointmentBook(AsyncCallback<AbstractAppointmentBook> asyncCallback);

}
