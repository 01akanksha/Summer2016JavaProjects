package edu.pdx.cs410J.akanksha;

       import edu.pdx.cs410J.AbstractAppointment;
        import edu.pdx.cs410J.AbstractAppointmentBook;
        import edu.pdx.cs410J.ParserException;

       import java.awt.*;
       import java.io.IOException;
        import java.util.ArrayList;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

/**
 * Created by A on 7/20/2016.
 */
public class Project3 {
    static boolean checkTextFile=false;
    static boolean checkPrintFile=false;
    static String fileName = null;
    static String fileNamePrint=null;
    static TextDumper textDumper = new TextDumper();
    static TextParser textParser = new TextParser();
    static PrettyPrinter prettyPrinter=new PrettyPrinter();
    static boolean Nothing =false;
    public static void main(String[] args) {
        boolean checkreadorprint=false;
        boolean checkprint=false;
        for (String arg : args)
        {
            if(arg.equals("-README")) {
                checkreadorprint=true;
            }
            if(arg.equals("-print")) {
                checkprint=true;
            }
            if(arg.equals("-textFile") && !args[arg.indexOf("-textFile") +1].contains("-")) {
                checkTextFile=true;

            }
            if(arg.equals("-pretty"))
            {
                checkPrintFile =true;
            }
        }

        if(checkTextFile){
            for(int i=0;i<args.length;i++)
                if(args[i].equals("-textFile")) {
                    fileName = args[i + 1];
                }

        }
        if(checkPrintFile) {
            for(int i=0;i<args.length;i++)
                if(args[i].equals("-pretty")) {
                    if(!args[i +1].equals("-"))
                    fileNamePrint = args[i + 1];
            //        System.out.println(fileNamePrint);
                }

        }

        if(checkreadorprint==true) {
            System.out.println("Name-FNU Akanksha\n***********************************\n" +
                    "Readme about CS410J Project 1: Designing an Appointment Book Application\n***********************************\n" +
                    "AppointmentBook- To manage the objects and to implement the getter functions of the super class AbstractAppointmentbook I have created a parameterized constructor and then in the implementation of the getter we have returned the values initialized inside the constructor while creation of the object.So this way the appointment book will be associated with the owner and the appointment.\n***********************************\n" +
                    "Appointment- To manage the objects and to implement the getter functions of the super class AbstractAppointment I have created a parameterized constructor and then in the implementation of the getter we have returned the values initialized inside the constructor while creation of the object.So this way the appointment will be associated with the description , begin time and end time of the appointment.\n***********************************\n" +
                    "Project- 3 This the class having main,So here we will take all the command line arguments and will associate it to the appointment and appointment book objects.Also we need to check the validatons on time,error handling on no of arguments passes and need to implement code for readme and print arguments\n***********************************\n" +
                    "END\n***********************************");
        }
        else if(checkprint) {
            if (checkTextFile) {
                if(checkPrintFile){
                    if(args.length==13){
                    if((args[6].trim()!=null && !args[6].trim().isEmpty()) && args[2]!=null && args[4]!=null && args[5]!=null && args[6]!=null && args[7]!=null && args[8]!=null && args[9]!=null && args[10]!=null && args[11]!=null && args[12]!=null){
                        bookAappointment(args[8],args[7],args[9],args[11],args[10],args[12],args[6],args[5]);
                       System.out.println("Description :"+args[6]);
                    }
                    else
                        System.out.println("Please enter the non null description");
                    }
                    else {
                        System.out.println("No of arguments are not correct");
                    }
                }
                else
                {
                    if(args.length==11){
                    if((args[4].trim()!=null && !args[4].trim().isEmpty()) && args[3]!=null && args[5]!=null && args[6]!=null && args[7]!=null && args[8]!=null && args[9]!=null && args[10] !=null){
                        bookAappointment(args[6],args[5],args[7],args[9],args[8],args[10],args[4],args[3]);
                        System.out.println("Description :"+args[4]);
                    }
                    else
                        System.out.println("Please enter the non null description");
                    }
                    else{
                        System.out.println("No of arguments are not correct");
                    }
                }

            }else if(checkPrintFile) {
                if(args.length==11) {
                    if ((args[4].trim() != null && !args[4].trim().isEmpty()) && args[3] != null && args[5] != null && args[6] != null && args[7] != null && args[8] != null && args[9] != null && args[10] != null)
                    {
                        bookAappointment(args[6], args[5], args[7], args[9], args[8], args[10], args[4], args[3]);
                        System.out.println("Description :" + args[4]);
                    } else
                        System.out.println("Please enter the non null description");
                }else
                {
                    System.out.println("No of arguments are not correct");
                }
            }
            else {
                System.out.println("I am here to print");
                if (args.length == 9) {
                    Nothing=true;
                    //if(args[1].contains("-print"))
                    if (args[0] != null && (args[2].trim() != null && !args[2].trim().isEmpty()) && args[1] != null && args[3] != null && args[4] != null && args[5] != null && args[6] != null) {
                        bookAappointment(args[4], args[3], args[5], args[7], args[6], args[8], args[2], args[1]);
                        System.out.println("Description :" + args[2]);
                    } else
                        System.out.println("Please enter the non null description");
                }else
                {
                    System.out.println("No of arguments are not correct");
                }
            }
        }
        else if(checkTextFile){
            if(checkPrintFile) {
                if(args.length==12) {
          //          System.out.println(fileName +"textprint"+fileNamePrint);
                    if (!fileName.equalsIgnoreCase(fileNamePrint)) {
                        if ((args[5].trim() != null && !args[5].trim().isEmpty()) && args[3] != null && args[5] != null && args[6] != null && args[7] != null && args[8] != null && args[9] != null && args[10] != null && args[11] != null) {
                            bookAappointment(args[7], args[6], args[8], args[10], args[9], args[11], args[5], args[4]);
                            //System.out.println("Description :" + args[5]);
                        } else
                            System.out.println("Please enter the non null description");
                    }
                    else
                        System.out.println("Both files should be of different names");
                }
                else
                    System.out.println("No of arguments are not correct");
            }
            else{
                if(args.length==10){
                    if((args[3].trim()!=null && !args[3].trim().isEmpty()) && args[2]!=null && args[4]!=null && args[5]!=null && args[6]!=null && args[7]!=null && args[8]!=null && args[9]!=null){
                        bookAappointment(args[5],args[4],args[6],args[8],args[7],args[9],args[3],args[2]);
                        //System.out.println("Description :"+args[2]);
                    }
                    else
                        System.out.println("Please enter the non null description");
                }else
                    System.out.println("No of arguments are not correct");
                            }
        }
        else if(checkPrintFile){
            if(args.length==10) {
                if ((args[3].trim() != null && !args[3].trim().isEmpty()) && args[2] != null && args[4] != null && args[5] != null && args[6] != null && args[7] != null && args[8] != null && args[9] != null) {
                    bookAappointment(args[5], args[4], args[6], args[8], args[7], args[9], args[3], args[2]);
                    //System.out.println("Description :"+args[2]);
                } else
                    System.out.println("Please enter the non null description");
            }else{
                System.out.println("No of arguments are not correct");
            }
        }
        else  if(args.length==8)
        {
            Nothing=true;
            if(args.length ==8) {
                if(args[0]!=null&& (args[1].trim()!=null && !args[1].trim().isEmpty()) && args[2]!=null && args[3]!=null && args[4]!=null && args[5]!=null && args[6]!=null && args[7]!=null)
                    bookAappointment(args[3],args[2],args[4],args[6],args[5],args[7],args[1],args[0]);
                else
                    System.out.println("Please check the Arguments and enter the non null description");
            }
        }
        else {
            System.out.println("No of arguments are not correct");
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

    /**
     * Function to add dump and pretty print appointments
     * @param beTime begin time of appointment
     * @param beDate begin date of appointment
     * @param begAMPM begin time Am/Pm
     * @param ndTime end tiem of appointment
     * @param ndDate end date of appointment
     * @param endAMPM end time am/pm
     * @param desc description of appointment
     * @param ownerName owner of appointment
     */
    public static void bookAappointment(String beTime,String beDate,String begAMPM,String ndTime,String ndDate,String endAMPM,String desc,String ownerName) {
        AbstractAppointment a=null;
        AbstractAppointmentBook ab=null;
        AbstractAppointmentBook ab1=null;
        String EndDateTime = null;
        String BeginDateTime = null;
        boolean check6 = false;
        check6 = (validateTime(beTime) && validateTime(ndTime) && validateDate(ndDate) && validateDate(beDate) && begAMPM.matches("(am|pm|AM|PM)") && endAMPM.matches("(am|pm|AM|PM)"));
        if(check6)
        {
            BeginDateTime = beDate + " " + beTime + " " + begAMPM;
            EndDateTime = ndDate + " " + ndTime +" "+ endAMPM;
            a = new Appointment(BeginDateTime, EndDateTime, desc);
            ab = new AppointmentBook(ownerName);
            ab.addAppointment(a);
            if(checkTextFile){
                try{
                    if(textParser.getFileName(fileName)) {
                        ab1=ReadDataFromFile(textParser, fileName);
                        if(ab1.getOwnerName().equalsIgnoreCase(ownerName)) {
                            ab1.addAppointment(a);
                            textDumper.getFileName(fileName);
                            textDumper.dump(ab1);
                            //String resultOfAppointment = a.toString();
                            System.out.println("\n"+"appointment added to the appointment book and written to the text file with name " + fileName);
                            //System.out.println(resultOfAppointment);
                            //String resultOFBook = ab1.toString();
                            //System.out.println(resultOFBook);
                        }else{
                            System.out.println("Different owners");
                        }
                    }
                    else {
                        textDumper.getFileName(fileName);
                        textDumper.dump(ab);
                        //String resultOfAppointment = a.toString();
                        System.out.println("\n"+"appointment added to the appointment book and written to the text file with name " + fileName);
                        //System.out.println(resultOfAppointment);
                        //String resultOFBook = ab.toString();
                        //System.out.println(resultOFBook);
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
            if(checkPrintFile){
                try{ if(fileNamePrint != null) {
                    //System.out.println("Hi");
                    //System.out.println(fileNamePrint);
                    if (textParser.getFileName(fileNamePrint)) {
                        if(checkTextFile){
                            ab1 = ReadDataFromFile(textParser, fileName);
                            if (ab1.getOwnerName().equalsIgnoreCase(ownerName))
                            //ab1 = ReadDataFromFile(textParser, fileName);
//                            ab1.addAppointment(a);
                            {
                            prettyPrinter.getFileName(fileNamePrint);
                            prettyPrinter.dump(ab1);
                            //String resultOfAppointment = a.toString()
                            System.out.print("   ");
                            System.out.println("\n"+"appointment added to the appointment book and pretty print to file with name " + fileNamePrint);
                            //System.out.println(resultOfAppointment);
                            //String resultOFBook = ab1.toString();
                            //System.out.println(resultOFBook);
                            }
                      //      System.out.println("Bye");
                        }
                        else{
                        ab1 = ReadDataFromFile(textParser, fileNamePrint);
                            if (ab1.getOwnerName().equalsIgnoreCase(ownerName)){
                                ab1.addAppointment(a);
                                prettyPrinter.getFileName(fileNamePrint);
                                prettyPrinter.dump(ab1);
                                //String resultOfAppointment = a.toString();
                                System.out.println("\n"+"appointment added to the appointment book and written to the pretty file named " + fileNamePrint);
                                //System.out.println(resultOfAppointment);
                                //String resultOFBook = ab1.toString();
                                //System.out.println(resultOFBook);
                            }
                            else
                                System.out.println("Different Owners");
                    }

                    } else {
                        if(checkTextFile){
                            ab1 = ReadDataFromFile(textParser, fileName);
                            if (ab1.getOwnerName().equalsIgnoreCase(ownerName))
                                //ab1 = ReadDataFromFile(textParser, fileName);
                                //ab1.addAppointment(a);
                            {prettyPrinter.getFileName(fileNamePrint);
                            prettyPrinter.dump(ab1);
                            //String resultOfAppointment = a.toString();
                            //System.out.println("Final Result of the appointment book and the added appoitment for the -pretty argument");
                            //System.out.println(resultOfAppointment);
                            //String resultOFBook = ab1.toString();
                            //System.out.println(resultOFBook);
                                }
                            else
                                System.out.println("Different owners in the text file and pretty");
                        }
                        else{
                        prettyPrinter.getFileName(fileNamePrint);
                        prettyPrinter.dump(ab);
                        //String resultOfAppointment = a.toString();
                        System.out.println("\n"+"Appointment book pretty printed to the file " + fileNamePrint);
                        //System.out.println(resultOfAppointment);
                        //String resultOFBook = ab.toString();
                        //System.out.println(resultOFBook);
                    }
                    }
                }
                else
                {
                    ArrayList<AbstractAppointment> list=null;
                    if(checkTextFile) {
                        ab1=ReadDataFromFile(textParser,fileName);
                        if (ab1.getOwnerName().equalsIgnoreCase(ownerName)){
                            list = (ArrayList<AbstractAppointment>) ab1.getAppointments();
                     //       System.out.println(a.toString());
                       //     System.out.println(ab1.toString());
                    }
                        else
                            list = (ArrayList<AbstractAppointment>) ab.getAppointments();
                        //System.out.println(a.toString());
                        //System.out.println(ab.toString());
                    }
                    else {
                        list = (ArrayList<AbstractAppointment>) ab.getAppointments();
                        //System.out.println(a.toString());
                        //System.out.println(ab.toString());
                    }
                    System.out.println("\n"+"Pretty Printing the Appointments....." +"\n");
                        for (int i = 0; i < list.size(); i++) {
                            long Duration = 0;
                            try {
                                Duration = ((Appointment) list.get(i)).getDuration(list.get(i).getBeginTimeString(), list.get(i).getEndTimeString());
                            } catch (java.text.ParseException e) {
                                System.out.println("Date Time not in correct format");
                            }
                            System.out.println("\n" + "Description-" + list.get(i).getDescription() + "\n" + "Begin Time-" + list.get(i).getBeginTimeString() + "\n" + "End Time-" + list.get(i).getEndTimeString() + "\n" + "Duration-" + Duration+"\n");
                        }
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
            if(Nothing) {
                a = new Appointment(BeginDateTime, EndDateTime, desc);
                ab = new AppointmentBook(ownerName, (Appointment) a);
                String resultOfAppointment = a.toString();
                //System.out.println("Final Result of the appointment book and the added appoitment without any argument options");
                System.out.println(resultOfAppointment);
                String resultOFBook = ab.toString();
                System.out.println(resultOFBook);
            }
        }else{
               System.out.println("DateTime not in correct format");
                System.exit(0);
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
        //System.out.println("READING FROM FILE---\n");
        //System.out.println("Appointment book has appointments="+appointmentBook.getAppointments().size());
        //System.out.println(appointmentBook.getAppointments());
        return  appointmentBook;
    }
}
