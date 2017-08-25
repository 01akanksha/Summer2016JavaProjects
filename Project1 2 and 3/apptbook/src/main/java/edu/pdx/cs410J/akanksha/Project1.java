package edu.pdx.cs410J.akanksha;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {
   // Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
//    System.err.println("Missing command line arguments");
/**check if There is a README or print Argumnt*/
    boolean checkreadorprint=false;
    boolean checkprint=false;
    for (String arg : args)
    {
        if(arg.contains("-README"))
        {
          checkreadorprint=true;
        }
      else if(arg.contains("-print") && args.length==7 && !args[6].contains("-print")) {
          checkprint=true;
        }
    }
/**Check if No of arguments are appropiate*/
    if((args.length<=6 && checkprint==true) || (checkreadorprint==false && args.length<6) || (args.length==7 && (args[6].contains("-print") || !args[0].contains("-print")))) {
      System.err.println("No of arguments are inappropriate");
      System.exit(1);
    }
/**if README is there print the text*/
    else if(checkreadorprint==true) {
      System.out.println("Name-FNU Akanksha\n***********************************\n" +
              "Readme about CS410J Project 1: Designing an Appointment Book Application\n***********************************\n" +
              "AppointmentBook- To manage the objects and to implement the getter functions of the super class AbstractAppointmentbook I have created a parameterized constructor and then in the implementation of the getter we have returned the values initialized inside the constructor while creation of the object.So this way the appointment book will be associated with the owner and the appointment.\n***********************************\n" +
              "Appointment- To manage the objects and to implement the getter functions of the super class AbstractAppointment I have created a parameterized constructor and then in the implementation of the getter we have returned the values initialized inside the constructor while creation of the object.So this way the appointment will be associated with the description , begin time and end time of the appointment.\n***********************************\n" +
              "Project1- This the class having main,So here we will take all the command line arguments and will associate it to the appointment and appointment book objects.Also we need to check the validatons on time,error handling on no of arguments passes and need to implement code for readme and print arguments\n***********************************\n" +
              "END\n***********************************");
    }
    /**if print is there and number of arguents are appropriate add the appointment and print the description of the appointment*/
    else if(checkprint==true)
      {
        if(args.length ==7) {
          //if(args[1].contains("-print"))
          if(args[0]!=null&& (args[2].trim()!=null && !args[2].trim().isEmpty()) && args[1]!=null && args[3]!=null && args[4]!=null && args[5]!=null && args[6]!=null){
          bookAappointment(args[4],args[3],args[6],args[5],args[2],args[1]);
          System.out.println("Description :"+args[2]);
          }
          else
            System.out.println("Please enter the non null description");
        }
      }
/** if there is neither README and print and number of arguments are appropriate add appointment*/
    else {
      if(args.length ==6) {
        if(args[0]!=null&& (args[1].trim()!=null && !args[1].trim().isEmpty()) && args[2]!=null && args[3]!=null && args[4]!=null && args[5]!=null)
        //this is the call to the function ----> public static void bookAappointment(String beTime,String beDate,String ndTime,String ndDate,String desc,String ownerName)
        // now your args[3]=begin Time , args[2] begin date, agrs[5] end time, args[4] end date, args[1] description, args[0] is owner
          bookAappointment(args[3],args[2],args[5],args[4],args[1],args[0]);
        else
          System.out.println("Please check the Arguments and enter the non null description");
      }
      }
  }
  //use these two functions-validateTime(String a) and validateDate(String a)  for validating time and date
  //u can search for other alternatives as well for validating it like there is one class SimpleDateTimeFormat u can use that also
  //in both these functions i have used the regular expression which is compiled into the interpretted language by compile function (eg..Pattern.compile(TIME24HOURS_PATTERN)) and then we have compared this compiled with the one we have passed as the parameter to the function(here string s) using the matcher function
  //these are all in import java.util.regex package so u need to import it
  // both these functions have this explanation
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

  //this is used for the main functionality that is of adding appointmetn to the appointment book
// above two functions are used here to validate the date and time
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
      String EndDateTime = null;
      String BeginDateTime = null;
      boolean check6 = false;
// time validation
      check6 = (validateTime(beTime) && validateTime(ndTime) && validateDate(ndDate) && validateDate(beDate));
//if the time and date are valid then add the appointment
      if (check6 == true) {
// combining date and time as the constructor of the appointment will take it together in the format(mm/dd/yyyy hh:mm)
        BeginDateTime = beDate + " " + beTime;
        EndDateTime = ndDate + " " + ndTime;
        // creating object of the appointment with the arguments passed by command line(we are getting here by the function call made above and values are passed as function parameter)
        AbstractAppointment a = new Appointment(BeginDateTime, EndDateTime, desc);
        //here we r craeting appointment book object to add appointment to the book
        //what i have done is i have called the appointment book's addappointment function in the constructor it self if you havenot done it like this you have to explicitly call it here
        // i.e. ab.addappointment(a);
        //this is what i have done with the appointmetn book constructor
        /*
         public AppointmentBook(String owner, Appointment a)
    {

        this.a=a;
        this.owner=owner;
        addAppointment(a);
    }
         */
        AbstractAppointmentBook ab = new AppointmentBook(ownerName, (Appointment) a);

        //after adding u r just printing the appointmet and appointment book by calling its to string method
        //u can do it directly as well System.out.println(a.toString() or ab.toString())
        String resultOfAppointment = a.toString();

        System.out.println(resultOfAppointment);

        String resultOFBook = ab.toString();
        System.out.println(resultOFBook);
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
}


