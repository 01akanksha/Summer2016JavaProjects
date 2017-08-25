package edu.pdx.cs410J.akanksha;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by A on 7/20/2016.
 */
public class PrettyPrinter implements AppointmentBookDumper {
    private String fileName;
    private File file;
    private boolean isFileExist;
    private FileWriter fw = null;
    ArrayList<AbstractAppointment> list ;

    /**
     * It will dump the appointments to the file
     * @param appointmentBook the contents of this appointment book will be dumped
     * @throws IOException
     */
    public void dump(AbstractAppointmentBook appointmentBook) throws IOException
    {

        System.out.println(isFileExist);
        if(isFileExist)
        {
            boolean a=false;
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String ownerName= br.readLine();
            if((appointmentBook.getOwnerName()!=null && appointmentBook.getOwnerName().equalsIgnoreCase(ownerName))){
                fw = new FileWriter(fileName, false);
                fw.write(appointmentBook.getOwnerName());
            }
            else{
                System.out.println("Owners  are different .. Please try again");
                System.exit(1);
            }

            //fw.write("\n");
        }
        else{
            fw = new FileWriter(fileName, true);
            fw.write(appointmentBook.getOwnerName());
            //fw.write("\n");
        }
        list = (ArrayList<AbstractAppointment>) appointmentBook.getAppointments();
        for (int i=0;i<list.size();i++)
        {
            long Duration=0;
            try {
                Duration = ((Appointment)list.get(i)).getDuration(list.get(i).getBeginTimeString(), list.get(i).getEndTimeString());
            }catch(java.text.ParseException e) {
            System.out.println("Date Time not in correct format");
            }
            fw.write("\n"+list.get(i).getDescription()+","+list.get(i).getBeginTimeString() + ","+ list.get(i).getEndTimeString()+","+String.valueOf(Duration));
        }
        fw.flush();
        fw.close();
    }

    /**
     * checks if file already exists if not create new file else return true
     * @param fileName the name of the file to be created
     * @return if file exists or not
     * @throws IOException
     */

    public boolean getFileName(String fileName) throws IOException {

        this.fileName=fileName;
        //System.out.println("I am in dumper 1");
        file = new File(fileName);
        //System.out.println("I am in dumper 2" + this.fileName);
        //System.out.println(file.exists());
        //System.out.println(file);
        if (file.exists()) {
            //System.out.println("file exists" +isFileExist);
            isFileExist = true;
            return isFileExist;
        }
        else {
            //System.out.println("I am in get name dumper else" + file.createNewFile());
            boolean isFileCreated = file.createNewFile();
            //System.out.println("file is created" +isFileCreated);

            //System.out.println("file exists" +isFileExist);
            return false;
        }
    }

}
