package edu.pdx.cs410J.akanksha.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an appointment book back from the server
 */
public class AppointmentBookGwt implements EntryPoint {
 private final Alerter alerter;
  String a="",b="",c="",d ="";
  @VisibleForTesting
  Button button;
  TextBox textBox;

  /**
   * constructor
   */
  public AppointmentBookGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  AppointmentBookGwt(Alerter alerter) {
    this.alerter = alerter;

    addWidgets();
  }

  /**
   * to create ping button and its handler
   */
  private void addWidgets() {
    button = new Button("Ping Server");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        createAppointments();
      }
    });

    this.textBox = new TextBox();
  }

  /**
   * to call the create appointments from the service
   */
  private void createAppointments() {
    AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
    //int numberOfAppointments = getNumberOfAppointments();
    async.createAppointmentBook(new AsyncCallback<AbstractAppointmentBook>() {

      @Override
      public void onFailure(Throwable ex) {
        alert(ex);
      }

      @Override
      public void onSuccess(AbstractAppointmentBook abstractAppointmentBook) {

      }
    });
  }

  /**
   * to get and return number of appointments
   * @return no of appointments
     */
  private int getNumberOfAppointments() {
    String number = this.textBox.getText();

    return Integer.parseInt(number);
  }

  /**
   * to display appointments in alert box
   * @param airline appointment book to be displayed
     */
  private void displayInAlertDialog(AppointmentBook airline) {
    StringBuilder sb = new StringBuilder(airline.toString());
    sb.append("\n");

    Collection<Appointment> flights = airline.getAppointments();
    for (Appointment flight : flights) {
      sb.append(flight);
      sb.append("\n");
    }
    alerter.alert(sb.toString());
  }

  /**
   * alert box for exceptions
   * @param ex exception thrown
     */
  private void alert(Throwable ex) {
    alerter.alert(ex.toString());
  }

  /**
   * method overriden to load the components on the page load like all buttons and their on click handlers and what functionality done on the click,textboxes etc
   */
  @Override
  public void onModuleLoad() {

    Button buttonPingServer = new Button("Ping Server");
    Button buttonCreateAppointmentBook = new Button("Create Appointment and Add to AppointmentBook");
    Button buttonAddAppointment = new Button("Add Appointment");
    Button buttonPrettyPrintAllAppointments = new Button("Pretty Print Appointments");
    Button buttonSearch = new Button("Search");
    Button buttonHelp = new Button("Help");

    buttonCreateAppointmentBook.setWidth("350px");
    buttonPrettyPrintAllAppointments.setWidth("200px");
    buttonSearch.setWidth("100px");
    buttonHelp.setWidth("100px");

    final TextArea textBoxResults = new TextArea();
    textBoxResults.setReadOnly(true);
    textBoxResults.setSize("800px","300px");

    VerticalPanel panelHeader = new VerticalPanel();
    panelHeader.add(new Label());
    panelHeader.add(textBoxResults);

    VerticalPanel panelAddNewOwner = new VerticalPanel();
    panelAddNewOwner.add(new Label("Owner Name:"));
    final TextBox textBoxOwnerName = new TextBox();
    panelAddNewOwner.add(textBoxOwnerName);

    panelAddNewOwner.add(new Label("Description:"));
    final TextBox textBoxDescription = new TextBox();
    panelAddNewOwner.add(textBoxDescription);

    panelAddNewOwner.add(new Label("Begin Time:"));
    final TextBox textBoxStartTime = new TextBox();
    panelAddNewOwner.add(textBoxStartTime);

    panelAddNewOwner.add(new Label("End Time:"));
    final TextBox textBoxEndTime = new TextBox();
    panelAddNewOwner.add(textBoxEndTime);

    RootPanel rootPanel = RootPanel.get();
    //rootPanel.a

    rootPanel.add(panelAddNewOwner, 400, 50);

  //  rootPanel.add(buttonAddAppointment,600,50);
    rootPanel.add(buttonCreateAppointmentBook,600,50);
    rootPanel.add(buttonPrettyPrintAllAppointments,600,90);
    rootPanel.add(buttonSearch,600,130);
    rootPanel.add(buttonHelp,600,170);
    rootPanel.add(panelHeader,250 ,260 );

    buttonPingServer.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
        async.createAppointmentBook(new AsyncCallback<AbstractAppointmentBook>() {


          @Override
          public void onFailure(Throwable throwable) {
            Window.alert(throwable.toString());
          }

          @Override
          public void onSuccess(AbstractAppointmentBook abstractAppointmentBook) {
            StringBuilder sb = new StringBuilder(abstractAppointmentBook.toString());
            Collection<AbstractPhoneCall> calls = abstractAppointmentBook.getAppointments();
            for (AbstractPhoneCall call : calls) {
              sb.append(call);
              sb.append("\n");
            }
            Window.alert(sb.toString());
          }
        });
      }
    });

//Help Readme
    buttonHelp.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        Window.alert("This is the README for the AppointmentBook web application!\n" +
                "Please enter the appropriate information for a owner. Including the name," +
                "Begin and end time for the Appointment.\n" +
                "You may store owner Appointment here, search them by starting time, or " +
                "display all AppointmentBooks");
      }
    });

    //CreateAppointmentBook
    buttonCreateAppointmentBook.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        try {
          if(textBoxOwnerName.getValue() == ""){
            Window.alert("Please provide owner name");
            return;
          }

          if(textBoxDescription.getValue() == ""){
            Window.alert("Please provide Description");
            return;
          }
          if(textBoxStartTime.getValue() == ""){
            Window.alert("Please provide begin time");
            return;
          }
          if(textBoxEndTime.getValue() == ""){
            Window.alert("Please provide end Time");
            return;
          }
          final Appointment tempAppt = new Appointment(textBoxStartTime.getValue().trim(), textBoxEndTime.getValue().trim(),textBoxDescription.getValue().trim());
          if(tempAppt.exceptionWasThrown()){
            return;
          }
          AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
          async.add(textBoxOwnerName.getValue(),tempAppt , new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
              Window.alert(throwable.getMessage());
            }
            @Override
            public void onSuccess(Void aVoid) {
              textBoxResults.setValue(textBoxOwnerName.getValue()+" has added a new appointment and created appointment book: "+tempAppt.toString() );

            }
          });
        }
        catch(Exception ex){
          //Not enough args or something
          Window.alert("Please provide correct arguments"+ex.getMessage());
          return;
        }

      }
    });

//Add a Appointment
    buttonAddAppointment.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        try {
          if(textBoxOwnerName.getValue() == ""){
            Window.alert("Please provide owner name");
            return;
          }

          if(textBoxDescription.getValue() == ""){
            Window.alert("Please provide description");
            return;
          }
          if(textBoxStartTime.getValue() == ""){
            Window.alert("Please provide begin time");
            return;
          }
          if(textBoxEndTime.getValue() == ""){
            Window.alert("Please provide end Time");
            return;
          }
          final Appointment tempAppt = new Appointment(textBoxStartTime.getValue(), textBoxEndTime.getValue(),textBoxDescription.getValue().trim());
          if(tempAppt.exceptionWasThrown()){
            return;
          }
          AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
          async.add(textBoxOwnerName.getValue().trim(),tempAppt , new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
              Window.alert(throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
              textBoxResults.setValue(textBoxOwnerName.getValue()+" has added a new appointment: "+tempAppt.toString() );
            }
          });
        }
        catch(Exception ex){
          //Not enough args or something
          Window.alert("Please provide correct arguments"+ex.getMessage());
          return;
        }

      }
    });

    //pretty Print
    buttonPrettyPrintAllAppointments.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
        async.print(new AsyncCallback<Map<String, AppointmentBook>>() {

          @Override
          public void onFailure(Throwable throwable) {
            Window.alert(throwable.getMessage());
          }

          @Override
          public void onSuccess(Map<String, AppointmentBook> stringPhoneBillMap) {
            String prettyCalls = "";
            //pretty print it
            if (stringPhoneBillMap == null || stringPhoneBillMap.isEmpty()) {
              Window.alert("No appointment Book to show");
              return;
            }
            for (String owner : stringPhoneBillMap.keySet()) {
              Collection calls = stringPhoneBillMap.get(owner).getAppointments();
              prettyCalls+=prettyPrint((List<Appointment>) calls,owner)+"\n";
            }
            textBoxResults.setValue("# Owner    Description          begin Time          End Time              Duration \n"+prettyCalls);
          }
        });
      }
    });
//Search for user
    buttonSearch.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        if(textBoxOwnerName.getValue() == ""){
          Window.alert("Please provide owner name");
          return;
        }
        if(textBoxStartTime.getValue() == ""){
          Window.alert("Please provide begin time");
          return;
        }
        if(textBoxEndTime.getValue() == ""){
          Window.alert("Please provide end time");
          return;
        }
        if(textBoxDescription.getValue() !="")
        {
          Window.alert("Description should be empty while searching");
          return;
        }


        if(textBoxStartTime.getValue().contains("\"")||textBoxEndTime.getValue().contains("\""))
          Window.alert("Date and time cannot contain quotes");

        String[] tempStart = textBoxStartTime.getValue().split(" ");
        String[] tempEnd= textBoxEndTime.getValue().split(" ");
        if(!tempStart[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!tempEnd[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")) {
          Window.alert("Date format must follow mm/dd/yyyy");
        }

        if(!tempStart[1].matches("(1[0-2]|0?[1-9]):([0-5]?[0-9])")||!tempEnd[1].matches("(1[0-2]|0?[1-9]):([0-5]?[0-9])"))
          Window.alert("Time format must follow mm:hh (12 hour time)");
        if(!tempStart[2].matches("(am|pm|AM|PM)")&&!tempEnd[2].matches("(am|pm|AM|PM)"))
          Window.alert("Time must include am/pm");

        AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
        async.search(textBoxOwnerName.getValue(), textBoxStartTime.getValue(),textBoxEndTime.getValue(), new AsyncCallback<List<Appointment>>() {
          @Override
          public void onFailure(Throwable throwable) {
            Window.alert("error "+throwable.getMessage());
          }

          @Override
          public void onSuccess(List<Appointment> appointments) {
              if(appointments==null||appointments.isEmpty()){
              Window.alert("No appointments matching under "+textBoxOwnerName.getValue());
              textBoxResults.setValue("# owner     description   Begin Time          End Time              Duration ");
              return;
            }
            //pretty print the search calls
            textBoxResults.setValue("# owner     description   Begin Time          End Time              Duration \n"+prettyPrint(appointments,textBoxOwnerName.getValue()));
          }
        });

      }
    });

  }

  /**
   * to pretty print all the appointments
   * @param apptList list of the appointments to be printed
   * @param owner owner of the appointment
     * @return return the statement in string format tobe printed
     */
  public String prettyPrint(List<Appointment> apptList,String owner){
    int x=0;
    String prettyCalls="";
    for(Appointment appt: apptList){

      prettyCalls += ++x + " "+owner+" " + appt.getDescription() + " " +appt.getBeginTimeString() + "  " + appt.getEndTimeString() + "   " + appt.duration() + "\n";
    }
    return prettyCalls;
  }


  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

}
