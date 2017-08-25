package edu.pdx.cs410J.akanksha;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project2 {
    static boolean checkTextFile;
    static String fileName = null;
    static TextDumper textDumper = new TextDumper();
    static TextParser textParser = new TextParser();
    public static void main(String[] args) {
        // Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
//    System.err.println("Missing command line arguments");
/**check if There is a README or print Argumnt*/
        boolean checkreadorprint=false;
        boolean checkprint=false;
        //boolean checkTextFile=false;

        for (String arg : args)
        {
            if(arg.contains("-README")) {
                checkreadorprint=true;
            }
            else if(arg.contains("-print")) {
                checkprint=true;
            }
            else if(arg.contains("-textFile") && !args[arg.indexOf("-textFile") +1].contains("-")) {
                checkTextFile=true;
                fileName=args[arg.indexOf("-textFile") +1];
            }
        }
        /**if README is there print the text*/
        if(checkreadorprint==true) {
            System.out.println("Name-FNU Akanksha\n***********************************\n" +
                    "Readme about CS410J Project 1: Designing an Appointment Book Application\n***********************************\n" +
                    "AppointmentBook- To manage the objects and to implement the getter functions of the super class AbstractAppointmentbook I have created a parameterized constructor and then in the implementation of the getter we have returned the values initialized inside the constructor while creation of the object.So this way the appointment book will be associated with the owner and the appointment.\n***********************************\n" +
                    "Appointment- To manage the objects and to implement the getter functions of the super class AbstractAppointment I have created a parameterized constructor and then in the implementation of the getter we have returned the values initialized inside the constructor while creation of the object.So this way the appointment will be associated with the description , begin time and end time of the appointment.\n***********************************\n" +
                    "Project1- This the class having main,So here we will take all the command line arguments and will associate it to the appointment and appointment book objects.Also we need to check the validatons on time,error handling on no of arguments passes and need to implement code for readme and print arguments\n***********************************\n" +
                    "END\n***********************************");
        }
/**Check if No of arguments are appropiate*/
        else if((checkTextFile && ((args.length<9 && checkprint) || (args.length<8)))|| (args.length>9 && checkreadorprint==false) ||((args.length<=6 || args.length>7) && checkprint &&  checkTextFile ==false && args[0].contains("-print")) || (checkreadorprint==false && args.length<6) || (args.length==7 && !args[0].contains("-print") && checkreadorprint==false))   {
            System.err.println("No of arguments are inappropriate");
            System.exit(1);
        }

        /**if only text file options are available */
        else if(checkTextFile && ! checkprint) {
            if((args[3].trim()!=null && !args[3].trim().isEmpty()) && args[2]!=null && args[4]!=null && args[5]!=null && args[6]!=null && args[7]!=null){
                bookAappointment(args[5],args[4],args[7],args[6],args[3],args[2]);
                //System.out.println("Description :"+args[2]);
            }
            else
                System.out.println("Please enter the non null description");
        }
/**if both text file and print options are available */
        else if(checkTextFile && checkprint) {
            //System.out.println("I a here");
            if((args[4].trim()!=null && !args[4].trim().isEmpty()) && args[3]!=null && args[5]!=null && args[6]!=null && args[7]!=null && args[8]!=null){
                bookAappointment(args[6],args[5],args[8],args[7],args[4],args[3]);
                System.out.println("Description :"+args[4]);
            }
            else
                System.out.println("Please enter the non null description");
        }
/**if only print is there and number of arguents are appropriate add the appointment and print the description of the appointment*/
        else if(checkprint==true && !checkTextFile)
        {
            //System.out.println("I am here");
            if(args.length ==7) {
                //if(args[1].contains("-print"))
                if(args[0]!=null&& (args[2].trim()!=null && !args[2].trim().isEmpty()) && args[1]!=null && args[3]!=null && args[4]!=null && args[5]!=null && args[6]!=null){
                    bookAappointment(args[4],args[3],args[6],args[5],args[2],args[1]);
                    System.out.println("Description :"+args[2]);
                }
                else
                    System.out.println("Please enter the non null description");
            } else{
                if(args.length ==6) {
                    if(args[0]!=null&& (args[1].trim()!=null && !args[1].trim().isEmpty()) && args[2]!=null && args[3]!=null && args[4]!=null && args[5]!=null)
                        bookAappointment(args[3],args[2],args[5],args[4],args[1],args[0]);
                    else
                        System.out.println("Please check the Arguments and enter the non null description");
                }
            }

        }
/** if there is neither README and print and number of arguments are appropriate add appointment*/
        else {
            if(args.length ==6) {
                if(args[0]!=null&& (args[1].trim()!=null && !args[1].trim().isEmpty()) && args[2]!=null && args[3]!=null && args[4]!=null && args[5]!=null)
                    bookAappointment(args[3],args[2],args[5],args[4],args[1],args[0]);
                else
                    System.out.println("Please check the Arguments and enter the non null description");
            }
        }
    }
    /**
     * Validate time in 24 hours format with regular expression
     * @param a in string format for validation
     * @return true valid time format, false invalid time format
     */
    public static boolean validateTime(String a) {
        try {
            String TIME24HOURS_PATTERN =
                    "([01]?[0-9]|2[0-3]):[0-5][0-9]";
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile(TIME24HOURS_PATTERN);
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

    /**
     * Add the appoint to the appointment book
     * @param beTime begin appointment time
     * @param beDate begin appointment Date
     * @param ndTime end time of appointment
     * @param ndDate end date of appointment
     * @param desc description of appointment
     * @param ownerName owner of the appointment
     */
    public static void bookAappointment(String beTime,String beDate,String ndTime,String ndDate,String desc,String ownerName) {
        try {
            AbstractAppointment a;
            AbstractAppointmentBook ab;
            AbstractAppointmentBook ab1;
            String EndDateTime = null;
            String BeginDateTime = null;
            boolean check6 = false;

            check6 = (validateTime(beTime) && validateTime(ndTime) && validateDate(ndDate) && validateDate(beDate));
            //System.out.println(check6);
            if (check6 == true) {

                BeginDateTime = beDate + " " + beTime;
                EndDateTime = ndDate + " " + ndTime;
                if(checkTextFile){
                    a = new Appointment(BeginDateTime, EndDateTime, desc);
                    ab = new AppointmentBook(ownerName);
                    ab.addAppointment(a);
                    try{
                        if(textParser.getFileName(fileName)) {
                            ab1=ReadDataFromFile(textParser, fileName);
                            if(ab1.getOwnerName().equalsIgnoreCase(ownerName)) {
                                ab1.addAppointment(a);
                                textDumper.getFileName(fileName);
                                textDumper.dump(ab1);
                                String resultOfAppointment = a.toString();
                                System.out.println(resultOfAppointment);

                                String resultOFBook = ab1.toString();
                                System.out.println(resultOFBook);
                            }
                            else {
                                textDumper.getFileName(fileName);
                                textDumper.dump(ab);
                                String resultOfAppointment = a.toString();
                                System.out.println(resultOfAppointment);

                                String resultOFBook = ab.toString();
                                System.out.println(resultOFBook);
                            }
                        }
                        else {
                            textDumper.getFileName(fileName);
                            textDumper.dump(ab);
                            String resultOfAppointment = a.toString();
                            System.out.println(resultOfAppointment);

                            String resultOFBook = ab.toString();
                            System.out.println(resultOFBook);
                        }
                    }
                    catch (IOException e) {
                        System.out.println("wrong format of data");
                    }
                    catch (ParserException e)
                    {
                        System.out.println("Error while parsing files");
                    }catch (StringIndexOutOfBoundsException e){
                        System.out.println("Malformmated data unable to parse");
                    }

                }
                else {
                    a = new Appointment(BeginDateTime, EndDateTime, desc);
                     ab = new AppointmentBook(ownerName, (Appointment) a);
                    String resultOfAppointment = a.toString();
                    System.out.println(resultOfAppointment);

                    String resultOFBook = ab.toString();
                    System.out.println(resultOFBook);
                }


            } else {
                System.out.println("DateTime not in correct format");
                System.exit(0);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * This method reads data from file , calls readfile() and parse() of TextParser Class
     * This method calls parse() method which retrieves data from file into Appointmentbook object and prints details.
     * @param textParser - textParser to call parse method
     * @param fileName - fileName for check if file exists for reading data
     * @throws IOException
     * @throws ParserException
     */
    private static AbstractAppointmentBook ReadDataFromFile(TextParser textParser, String fileName) throws IOException, ParserException {
        textParser.getFileName(fileName);
        AbstractAppointmentBook appointmentBook;
        appointmentBook = textParser.parse(); //Read from file and creates appointment book
        System.out.println("READING FROM FILE---\n");
        System.out.println("Appointment book has appointments="+appointmentBook.getAppointments().size());
        //System.out.println(appointmentBook.getAppointments());
        return  appointmentBook;
    }
}


