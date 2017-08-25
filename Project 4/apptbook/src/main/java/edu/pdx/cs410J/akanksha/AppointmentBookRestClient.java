package edu.pdx.cs410J.akanksha;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

/**
 * A helper class for accessing the rest client
 */
public class AppointmentBookRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "apptbook";
    private static final String SERVLET = "appointments";

    private final String url;


    /**
     * Creates a client to the appointment book REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AppointmentBookRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all keys and values from the server
     */
    public Response getAllKeysAndValues() throws IOException
    {
        return get(this.url );
    }

    /**
     * Returns all values for the given key
     */
    public Response getValues( AppointmentBook book ) throws IOException
    {
        try {
            //Search was properly handled.
            if (book.a != null)
                return get(this.url, "owner", book.getOwnerName(), "beginTime", book.a.getBeginTimeString(), "endTime", book.a.getEndTimeString());
            else {
                if (book.list != null)
                    throw new Exception("Please specify search parameters");
                else{
                    return get(this.url, "owner", book.getOwnerName(), "beginTime", book.a.getBeginTimeString(), "endTime", book.a.getEndTimeString());
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            System.exit(1);
            return null;
        }
    }

    public Response addKeyValuePair( String owner, Appointment appt ) throws IOException
    {
        return post( this.url, "owner", owner,"description",appt.getDescription(),"beginTime", appt.getBeginTimeString(), "endTime", appt.getEndTimeString() );
        //return postToMyURL("key", key, "value", value);
    }

    /*@VisibleForTesting
    Response postToMyURL(String... keysAndValues) throws IOException {
        return post(this.url, keysAndValues);
    }

    public Response removeAllMappings() throws IOException {
        return delete(this.url);
    }*/
}
