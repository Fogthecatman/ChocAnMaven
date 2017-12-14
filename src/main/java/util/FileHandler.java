package util;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FileHandler {

    //@TODO: Probably want to pass in List of reports
    public void writeMemberReport(String report, String path, String memName, int memID){
        LocalDate ld = LocalDate.now();
        String fileName = memName + "-" + memID + "_" + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(ld) + ".txt";

        File file = new File(path + fileName);


        try{
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(report);
            bw.close();
        }
        catch(IOException ex){
            System.out.println("File could not be created");
        }

    }

    public void writeProviderReport(String report, String path, String provName, int provID){
        LocalDate ld = LocalDate.now();
        String fileName = provName + "-" + provID + "_" + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(ld) + ".txt";
        File file = new File(path + fileName);

        try{
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(report);
            bw.close();
        }
        catch(IOException ex){
            System.out.println("File could not be created");
        }
    }

    public void writeManagerReport(String report, String path){
        LocalDate ld = LocalDate.now();
        String fileName = "ManagerReport_" + DateTimeFormatter.ofPattern("MM-dd-yyyy").format(ld) + ".txt";
        File file = new File(path + fileName);

        try{
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(report);
            bw.close();
        }
        catch(IOException ex){
            System.out.println("File could not be created");
        }
    }

    public String createWeeklyFolder(String path){
        LocalDate ld = LocalDate.now();
        String date = DateTimeFormatter.ofPattern("MM-dd-yyyy").format(ld).toString();
        File folderPath = new File(path + date );

        if(!folderPath.exists())
            folderPath.mkdir();

        return folderPath.getPath() + "/";
    }







}
