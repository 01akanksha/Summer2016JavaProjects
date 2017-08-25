package edu.pdx.cs410J.akanksha;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.acl.Owner;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class AppointmentBookServlet extends HttpServlet
{
 //   private final Map<String, String> data = new HashMap<>();
 private final Map<String, AppointmentBook> data = new HashMap<String, AppointmentBook>();
    private AppointmentBook apptBook = null;

    /**
     * Handles an HTTP GET request from a client by writing the value of the key
     * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
     * parameter is not specified, all of the key/value pairs are written to the
     * HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );
        String owner = getParameter( "owner", request );
        String startTime = getParameter( "beginTime", request );
        String endTime = getParameter( "endTime", request );
        boolean set=false,otherset=false;
        PrintWriter pw = response.getWriter();
        if(startTime!=null && endTime!=null) {
            SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            try {
                Date a = ft.parse(startTime);
                Date b = ft.parse(endTime);
                set = true;
            } catch (java.text.ParseException e) {
                pw.println("U have Passed wrong date in query string");
                pw.println("Page Cannot be display");
            }
            if(set) {
                String[] datesBegin = startTime.split(" ");
                String[] datesEnd=endTime.split(" ");

                if(validateDate(datesBegin[0])&& validateTime(datesBegin[1]) && datesBegin[2].matches("(am|pm|AM|PM)")&& datesEnd[2].matches("(am|pm|AM|PM)") && validateDate(datesEnd[0]) && validateTime(datesEnd[1])){
                    otherset=true;
                }
                else
                {
                    pw.println("U have Passed wrong date in query string");
                    pw.println("Page Cannot be display as wrong date enterend in query string");
                }
            }
        }
        //String key = getParameter( "key", request );
        if (owner != null && startTime!=null && endTime!=null) {
            if(set && otherset) {
                Appointment a = new Appointment();
                a.setDateTime(startTime, endTime);
                writeSearchValue(new AppointmentBook(owner, a, "-search"), response);
                // writeValue(key, response);
            }else
            {
                pw.println("Error:500-Page cannot be displayed as wrong date entered in query string");
            }
        } else if(owner!=null && startTime==null && endTime==null) {
            writeValue(owner,response);
        }
        else{
            writeAllMappings(response);
        }
    }
    /**
     * Validate time in 24 hours format with regular expression
     * @param a in string format for validation
     * @return true valid time format, false invalid time format
     */
    public static boolean validateTime(String a) {
        try {
            String TIME12HOURS_PATTERN ="^(1[0-2]|0?[1-9]):([0-5]?[0-9])$";
            //        "^(?:0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile(TIME12HOURS_PATTERN);
            matcher = pattern.matcher(a);
            //System.out.println(matcher.matches());
            return matcher.matches();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    /**
     * Validate Date in mm/dd/yyyy format with regular expression
     * @param s in string format for validation
     * @return true valid time format, false invalid time format
     */
    public static boolean validateDate(String s) {
        try {
            String regex =
                    "^((((0[13578])|([13578])|(1[02]))[\\/](([1-9])|([0-2][0-9])|(3[01])))|(((0[469])|([469])|(11))[\\/](([1-9])|([0-2][0-9])|(30)))|((2|02)[\\/](([1-9])|([0-2][0-9]))))[\\/]\\d{4}$|^\\d{4}$";
            //String regex ="^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            //System.out.println("Date"+ matcher.matches());
            return matcher.matches();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    private void writeSearchValue( AppointmentBook b, HttpServletResponse response ) throws IOException
    {
        //String value = this.data.get(key);
        long duration=0;
        PrintWriter pw = response.getWriter();
        //pw.println(Messages.getMappingCount( value != null ? 1 : 0 ));
        //pw.println(Messages.formatKeyValuePair( key, value ));
        //pw.println("WriteValue function to be displayed on server page");
        String owner = b.getOwnerName();
        Long begin = b.a.getBeginTime().getTime();
        Long end =b.a.getEndTime().getTime();
        if(data.get(owner)!= null) {
            pw.println("Searching for: " +data.get(owner).toString());
            Collection<Appointment> appt = data.get(owner).getAppointments();
            boolean flag = true;
            int counter = 0;
            pw.println("#owner: description  Start Time   And   End Time Duration \n");

            for(Appointment app: appt){
                if(begin<= (Long)app.begTime.getTime()&&begin<=(Long)app.enTime.getTime()){
                    //System.out.println("inside begin");
                    if(end>=(Long)app.begTime.getTime() && end>=(Long)app.enTime.getTime()) {
                      //  System.out.println("inside end");
                        flag = false;
                        try {
                            duration = app.getDuration(app.getBeginTimeString(), app.getEndTimeString());
                        } catch (java.text.ParseException e) {
                            System.out.println("Date not in correct format to calculate duration");
                        }
                        pw.println(++counter + " " + owner + "  " + app.getDescription() + "  " + app.getBeginTimeString() + "  " + app.getEndTimeString() + "   " + duration + "\n");
                    }

                }
            }
            if(flag){
                //No phone calls match your request
                pw.println("No Appointents match for: "+owner);
            }
        }
        else
            pw.println("Owner does not exists");

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }



    /**
     * Handles an HTTP POST request by storing the key/value pair specified by the
     * "key" and "value" request parameters.  It writes the key/value pair to the
     * HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );


        String owner = getParameter( "owner", request );
        if (owner == null) {
            missingRequiredParameter( response, "owner" );
            return;
        }
        String description = getParameter( "description", request );
        if (description == null) {
            missingRequiredParameter( response, "description" );
            return;
        }
        String startTime = getParameter( "beginTime", request );
        if (startTime == null) {
            missingRequiredParameter( response, "beginTime" );
            return;
        }
        String endTime = getParameter( "endTime", request );
        if (endTime == null) {
            missingRequiredParameter( response, "endTime" );
            return;
        }

        PrintWriter pw = response.getWriter();
        if(data!=null &&data.get(owner) != null){
            apptBook = data.get(owner);
            apptBook.addAppointment(new Appointment(startTime, endTime ,description));
            data.put(owner, apptBook);
            System.out.println("attempting to add new appointment");


        }
        else{
            data.put(owner, new AppointmentBook(owner, new Appointment(startTime, endTime,description)));
            System.out.println("new owner added");
            pw.println("attempting to add a new owner");
        }

        int counter=0;
        Collection<Appointment> appointment = data.get(owner).getAppointments();
        pw.println("#owner Description    Start Time        End Time        Duration \n");
        long duration=0;
        for(Appointment appt: appointment){
            try{
                duration=appt.getDuration(appt.getBeginTimeString(),appt.getEndTimeString());
            }catch (java.text.ParseException e)
            {
                System.out.println("Date not in correct format to calculate duration");
            }
            pw.println(++counter +" "+ owner+ "  "+appt.getDescription()+"  "+appt.getBeginTimeString()+"  "+appt.getEndTimeString()+  "   "+duration+"\n");
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all key/value pairs.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
   /* @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.data.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allMappingsDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }
*/
    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(parameterName));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    /**
     * Writes the value of the given key to the HTTP response.
     *
     * The text of the message is formatted with {@link Messages#getMappingCount(int)}
     * and {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeValue( String owner, HttpServletResponse response ) throws IOException
    {
        long duration=0;
        PrintWriter pw = response.getWriter();
        //String value = this.data.get(key);
        if(data.get(owner)!= null) {
            int counter=0;
            Collection<Appointment> app=data.get(owner).getAppointments();
            pw.println("#owner: Description  Start Time   And   End Time Duration \n");

            for(Appointment appt: app){
                try{
                    duration=appt.getDuration(appt.getBeginTimeString(),appt.getEndTimeString());
                }catch (java.text.ParseException e)
                {
                    System.out.println("Date not in correct format to calculate duration");
                }
                pw.println(++counter +" "+ owner+ "  "+appt.getDescription()+"  "+appt.getBeginTimeString()+"  "+appt.getEndTimeString()+  "   "+duration+"\n");
                }
            }
        else
            pw.println("owner does not exists");

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
        }

    /**
     * Writes all of the key/value pairs to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount(data.size()));

        for (Map.Entry<String, AppointmentBook> entry : this.data.entrySet()) {
            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue().toString()));
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

   /* @VisibleForTesting
    void setValueForKey(String key, String value) {
        this.data.put(key, value);
    }

    @VisibleForTesting
    String getValueForKey(String key) {
        return this.data.get(key);
    }*/
}
