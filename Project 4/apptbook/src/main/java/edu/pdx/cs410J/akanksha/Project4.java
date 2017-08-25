package edu.pdx.cs410J.akanksha;

import edu.pdx.cs410J.web.HttpRequestHelper;
//import sun.security.mscapi.KeyStore;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";
    static ArrayList<String> commands; //used to keep track of all the commands that will be run at the end of the program
    static AppointmentBook MySearchBook;//keep a local copy of the customer that we are looking for, only used for -search command
    static AppointmentBook MySingleAppointment;
    static boolean searchTest=false,reame=false;
    public static void main(String... args) {
        setGlobalsToNull();
        commands=new ArrayList<String>();
        int element = parseCommands(args);
        parseOwnerIfExistsToAdd(args, element);
        executeCommands(args);
        System.exit(0);
    }

    public static void setGlobalsToNull(){
        commands = null;
        MySingleAppointment = null;
        MySearchBook = null;
    }

    /** -----PARSE COMMANDS AT BEGINNING-----
     *
     * @param args parse all of the commands from args
     * @throws IllegalArgumentException if there are missing args
     */
    private static int parseCommands(String[] args){
        int element = 0;
        try {
            if (args.length == 0 ) {
                throw new IllegalArgumentException(MISSING_ARGS);
            }

            boolean flag = false;
            for (;element < args.length; element++) {
                //check if -print, -README, -search ,-host,-port

                switch (args[element]) {
                    case "-README":
                        //add readme to the command list
                        addArgumentCommand("-README");
                        reame=true;
                        break;
                    case "-print":
                        //add print to the list
                        addArgumentCommand("-print");
                        break;
                    case "-host":
                        //check for ++element
                        if (args.length > element + 1) {
                            addArgumentCommand("-host");
                            if(!dashExists(args[element+1]))
                                addArgumentCommand(args[++element]);
                            else
                                throw new IllegalArgumentException("-host must contains a valid <hostname>. No dashes allowed");
                        } else {
                            throw new IllegalArgumentException("-host argument must be followed by <hostname>");
                        }
                        break;
                    case "-port":
                        if (args.length > element + 1) {
                            addArgumentCommand("-port");
                            if(!dashExists(args[element+1]))
                                addArgumentCommand(args[++element]);
                            else
                                throw new IllegalArgumentException("-port must contains a valid <port>. No dashes allowed");
                        } else {
                            //throw error
                            throw new IllegalArgumentException("-port argument must be followed by <port>");
                        }
                        break;
                    case "-search":
                        addArgumentCommand("-search");
                        searchTest=true;
                        break;
                    default:
                        if(dashExists(args[element]))
                        {
                            throw new IllegalArgumentException("Non-Valid Argument");
                        }
                        return element;
                }
            }
        }
        catch(IllegalArgumentException ex){
            if(ex.getMessage()!= null)
                usage(ex.getMessage());
            else
                usage("");
            System.exit(1);
        }
        return element;
    }


    /**
     * Check if a dash is in the beggning of the argument
     * @param arg the argument passed
     * @return true or false if it starts with a dash
     */
    private static boolean dashExists(String arg){
        //Check if a dash exists in the arg
        return arg.startsWith("-");
    }

    /**
     *-ADD AN ARGUMENT TO THE LIST-
     * @param arg is a single string to be added to the list of commands
     */
    private static void addArgumentCommand(String arg){
        //Modify the list array of commands.
        //This list of commands ie -README, -print  will get executed after any other work is done.
        if(!commands.contains(arg)){
            //Check if the list already contains it
            commands.add(arg);
        }
    }
    /** -----PARSE CUSTOMER IF THEY EXIST-----
     *
     * @param args more parsing and stuff
     * @param element is a counter to keep track of which arg we are parsing
     * @throws IllegalArgumentException if not enough args are provided
     */
    private static void parseOwnerIfExistsToAdd(String[] args, int element){

        try {
            //System.out.println(element);
            //check that element to element+8 exists
            if (args.length >=element+7 && searchTest ==false && reame==false) {
                if(args.length>8) {
                    if(args.length>=element+8) {
                        String owner = args[element];
                        String desc = args[element + 1];
                        String bam = args[element + 4];
                        String eam = args[element + 7];
                        if (validateDate(args[element + 2]) && validateDate(args[element + 5]) && validateTime(args[element + 3]) && validateTime(args[element + 6]) && bam.matches("(am|pm|AM|PM)") && eam.matches("(am|pm|AM|PM)")) {
                            String begTime = args[element + 2] + " " + args[element + 3] + " " + args[element + 4];
                            String endTime = args[element + 5] + " " + args[element + 6] + " " + args[element + 7];
                            //parse out customer information
                            MySingleAppointment = new AppointmentBook(owner, new Appointment(begTime, endTime, desc), "-single");
                            System.out.println("Appointment Added to book...");
                        } else
                            System.out.println("Date Time not in correct format");
                    }
                    else
                        throw new IllegalArgumentException("Not enough arguments provided");

                }else if(args.length==8 && commands.isEmpty()) {
                    String owner = args[element];
                    String desc = args[element + 1];
                    String bam = args[element + 4];
                    String eam = args[element + 7];
                    if (validateDate(args[element + 2]) && validateDate(args[element + 5]) && validateTime(args[element + 3]) && validateTime(args[element + 6]) && bam.matches("(am|pm|AM|PM)") && eam.matches("(am|pm|AM|PM)")) {
                        String begTime = args[element + 2] + " " + args[element + 3] + " " + args[element + 4];
                        String endTime = args[element + 5] + " " + args[element + 6] + " " + args[element + 7];
                        //parse out customer information
                        //System.out.println(desc.trim());
                        if(desc.trim()!=null && !desc.trim().isEmpty())
                        MySingleAppointment = new AppointmentBook(owner, new Appointment(begTime, endTime, desc), "-single");
                        else
                            System.out.println("Missing Description");
                        //System.out.println("Appointment Added to book but no host and port specified so cannot add to servlet");
                    } else
                        System.out.println("Date Time not in correct format");

                }
                else
                    throw new IllegalArgumentException("Not enough arguments provided");
            }
            else if(!reame && args.length>1) {
                if (args.length > element) {
                    if(args.length>element+6)
                        parseSearch(args,element);
                    else {
                        //didn't provide enough args
                        //throw error
                        throw new IllegalArgumentException("Not enough arguments provided");
                    }
                }
            }
        }
        catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
    /**
     * Check if enough args exist to be a customer used for searching only, not creating a new one
     *@param element is a counter to keep track of which arg we are parsing
     * @param args is the list of args passed in to parse
     */
    private static void parseSearch(String[] args, int element){
        try{
            if(commands.contains("-search") && args.length==12) {
                Appointment appt = new Appointment();
                String owner = args[element];
                String begTime=null;
                String endTime=null;
                String bam=args[element+3];
                String eam=args[element+6];
                if(validateDate(args[element+1]) && validateDate(args[element+4]) && validateTime(args[element+2]) && validateTime(args[element+5])&& bam.matches("(am|pm|AM|PM)") && eam.matches("(am|pm|AM|PM)")) {
                    begTime = args[element + 1] + " " + args[element + 2] + " " + args[element + 3];
                    endTime = args[element + 4] + " " + args[element + 5] + " " + args[element + 6];
                    appt.setDateTime(begTime,endTime);
                    MySearchBook = new AppointmentBook(owner, appt, "-search");
                }
                else
                    System.out.println("Date Time not in correct format");

            }
            else{
                //Throw exception, trying to add a new phonecall or customer with not enough args.
                throw new IllegalArgumentException("Check your arguments it should have host and port and after search it should only have owner name begin and end time to search and no description");
            }
        }
        catch(IllegalArgumentException ex){
            if(ex.getMessage()!= null)
                usage(ex.getMessage());
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

    /** -----EXECUTE COMMANDS THAT EXIST-----
     * @throws Exception if something bad occurs
     */
    private static void executeCommands(String[] args){
        AppointmentBookRestClient client=null; //Client for all the HTTP commands
        HttpRequestHelper.Response response; //Response from client command
        ArrayList<HttpRequestHelper.Response> listOfResponses; //List of Response from client command

        boolean printFlag   = false;
        boolean ReadmeFlag  = false;
        boolean host        = false;
        boolean port        = false;
        boolean search      = false;
        String hostName     = null;
        int portNumber      = 0;

        try {
            if(commands !=null && !commands.isEmpty()) {
                for (String comm : commands) {
                    switch (comm) {
                        case "-README":
                            ReadmeFlag = true;
                            break;
                        case "-print":
                            printFlag = true;
                            break;
                        case "-host":
                            host = true;
                            hostName = commands.get(commands.indexOf(comm) + 1);
                            break;
                        case "-port":
                            port = true;
                            try {
                                portNumber = Integer.parseInt(commands.get(commands.indexOf(comm) + 1));

                            } catch (NumberFormatException ex) {
                                usage("Port \"" + commands.get(commands.indexOf(comm) + 1) + "\" must be an integer");
                                return;
                            }
                            break;
                        case "-search":
                            search = true;
                        default:
                            break;
                    }
                }
                boolean searchPrint = false, printSearch = false;
                if (search && !printFlag) {
                    searchPrint = true;
                } else if (search == false && printFlag == true)
                    printSearch = true;
                else if(!search && !printFlag)
                {
                }else
                    System.out.println("Print and search cannot be together");

                if (ReadmeFlag)
                    Readme();
                else {
                    if(args.length<8)
                    {
                        throw new IllegalArgumentException(MISSING_ARGS);
                    }
                    if(args.length==9 && printFlag)
                    {
                        if (MySingleAppointment != null) {
                            Print();
                            printFlag=true;
                        } else {
                            //MyphoneBill is null, throw exception
                            throw new Exception("Must provide a Appointment book with the -print command");
                        }
                    }
                    else if (printFlag && args.length<9 )
                    {

                    }
                    if(args.length >9){
                        if (host == false) {

                            throw new Exception("No host to connect to");
                        } else {
                            //execute host stuff
                            if (port == false) {

                                throw new Exception("No port to connect through");
                            } else
                            {
                                //be happy, do all the connection things here
                                client = new AppointmentBookRestClient(hostName, portNumber);
                            }
                        }

                        try {

                            if (searchPrint == true) {
                                //check that either MySearchBill is != null || MyPhoneBill!= null
                                if (MySearchBook != null) {
                                    //do a GET with mysearchbill
                                    response = client.getValues(MySearchBook);
                                } else if (MySingleAppointment != null) {
                                    //do a GET with myphonebill
                                    response = client.getValues(MySingleAppointment);
                                } else {
                                    throw new Exception("No data to search for");
                                }
                            } else if (MySingleAppointment != null && MySingleAppointment.a != null) {
                                //do a POST of MyPhoneBill and update MyPhoneBill if any extra phone calls were on the server.
                                response = client.addKeyValuePair(MySingleAppointment.getOwnerName(), (Appointment) MySingleAppointment.a);
                            } else {
                                //They supplied no commands, check the server and getAllKeysAndValues???????

                                response = client.getAllKeysAndValues();
                            }
                            checkResponseCode(HttpURLConnection.HTTP_OK, response);

                        } catch (IOException ex) {
                            error("While contacting server: " + ex.getMessage() + ",\nPlease try again with a a valid host name\n");
                            return;
                        }
                        System.out.println(response.getContent());

                        if (printSearch) {
                            if (MySingleAppointment != null) {
                                Print();
                                printFlag=true;
                            } else {
                                //MyphoneBill is null, throw exception
                                throw new Exception("Must provide a Appointment book with the -print command");
                            }
                        }
                    }
                }
            }else if(MySingleAppointment!=null){
                //System.out.println(MySingleAppointment);
                System.out.println("Appointment added but no host and port specified to add to servlet");
            }
        }
        catch(Exception ex)
        {
            if(ex.getMessage()!=null) {
                usage(ex.getMessage());
            }
            else{
                usage("Empty Exception");
            }

            System.exit(1);
        }
    }

    private static void Print()
    {
        System.out.println("owner: " + MySingleAppointment.getOwnerName() + " " + MySingleAppointment.a);

        System.out.println("#     owner      description           Begin Time        End Time        Duration \n");
        long duration = 0;
        try
        {
            duration = ((Appointment) MySingleAppointment.a).getDuration(MySingleAppointment.a.getBeginTimeString(), MySingleAppointment.a.getEndTimeString());
        }
        catch (java.text.ParseException e)
        {
            System.out.println("DateTime not in correct format");
        }
        System.out.println("1" + " " + MySingleAppointment.getOwnerName() + " " + MySingleAppointment.a.getDescription() + " " + MySingleAppointment.a.getBeginTimeString() + " " + MySingleAppointment.a.getEndTimeString() + " " + duration + "\n");
        //printFlag = true;

    }

    /**
     * Readme function contains the readme of all useful information the user may need to know.
     */
    private static void Readme() {
        System.out.println("README has been called");
        System.out.println("This program is a Appointment Book application which takes a very specific amount of arguments");
        System.out.println("You must provide a owner name, description, begin time, and end time (MM/dd/yyyy hh:mm a)");
        System.out.println();
        System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>\n" +
                "args are (in this order):\n" +
                "Owner Person whose appointment book we’re modeling\n" +
                "beginTime Date and time Appointment began\n" +
                "endTime Date and time Appointment ended\n" +
                "options are (options may appear in any order):\n" +
                "-host hostname Host computer on which the server runs\n" +
                "-port port Port on which the server is listening\n" +
                "-search Appointments should be searched for\n" +
                "-print Prints a description of the new Appointment\n" +
                "-README Prints a README for this project and exits\n" +
                "Dates and times should be in the format: mm/dd/yyyy hh:mm am");
    }


    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [key] [value]");
        err.println("  host    Host of web server");
        err.println("  port    Port of web server");
        err.println("  key     Key to query");
        err.println("  value   Value to add to server");
        err.println();
        err.println("This simple program posts key/value pairs to the server");
        err.println("If no value is specified, then all values are printed");
        err.println("If no key is specified, all key/value pairs are printed");
        err.println();
        System.exit(1);
    }
}