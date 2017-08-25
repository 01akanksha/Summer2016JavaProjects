package edu.pdx.cs410J.akanksha;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by A on 7/8/2016.
 */
public class TextParser implements AppointmentBookParser {

    private String fileName;
    private File file;

    /**
     * it will parse file and will create the appointment book
     * @return the appointment book created after parsing the file
     * @throws ParserException
     */
    public AbstractAppointmentBook parse() throws ParserException {

        file = new File(fileName);
        FileInputStream fis ;
        String begintime=null;
        String endtime=null;
        try {
            fis = new FileInputStream(file);
            //Construct BufferedReader from InputStreamReader+
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line ;
            AppointmentBook appointmentBook = new AppointmentBook(br.readLine());
            while ((line = br.readLine()) != null) {
                String appointmentdetails[] = line.split(",");
                String description = appointmentdetails[0];
                /*
                if(validateDate(appointmentdetails[2]) && validateTime(appointmentdetails[3])) {
                    begintime = appointmentdetails[2] + " " + appointmentdetails[3];
                }
                if(validateDate(appointmentdetails[5]) && validateTime(appointmentdetails[6]))
                {endtime = appointmentdetails[5]+ " "+(appointmentdetails[6]);} */

                begintime =appointmentdetails[1];
                endtime=appointmentdetails[2];


                if ((!(description).trim().isEmpty())) {
                    Appointment appointment = new Appointment(begintime,endtime,description);
                    appointmentBook.addAppointment(appointment);
                }
                else {
                    System.out.println("No description,Please try again,File malformmated");
                }
                line = null;
                description=null;
                begintime = null;
                endtime = null;
            }
            br.close();
            return  appointmentBook;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("File Malformmated");
            System.exit(1);
        }

        return null;
    }

    /**
     * returns true if file already exists
     * @param fileName the file to be read from
     * @return if file already exists return true
     * @throws IOException
     */

    boolean getFileName(String fileName) throws IOException {
        this.fileName=fileName;
        file = new File(fileName);
        if (file.exists()) {
            return true;
        }
        else
            return false;
    }

    /**
     * This method checks the correct format for date and time (MM/dd/yyyy HH:mm)
     * This method checks the form of dd/mm/yyyy in the form of dd/mm - 2 digits and yyyy - 4 digits
     * @param parsedate - check for entered begindate and end date
     * @throws ParseException - if dates are in invalid format
     */
    void checkValidDate(String parsedate) throws ParseException {

        if(parsedate == null || !(parsedate.matches("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2}$")))
        {
            System.out.println("Date not in valid format in text files...Please try again");
            System.exit(1);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm"); //Validate Date time format
        dateFormat.setLenient(false);
        dateFormat.parse(parsedate);
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
            System.out.println("Wrong Format of time");
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
            System.out.println("wrong format of date");
            return false;
        }
    }
}
