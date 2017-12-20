package controller;

import javafx.event.ActionEvent;
import util.FileHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* This class is used to handle all building of all the reports into a giant string.
* It then passes the String into the file handler class where it will then be printed
* to a file.
*/

public class ReportController {

    private FileHandler fh;
    private DatabaseController db;
    private StateController sc;

    public ReportController() {
        fh = new FileHandler();
        db = DatabaseController.getInstance();
        sc = StateController.getInstance();
    }

    /**
    * Action events to handle report writing when the operator asks for a report to be written
    * when he presses a button. 
    */
    public void actionProviderReport(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = db.executeSql(db.getProviderWeeklyReport());

        createProviderReport(rs);
    }

    public void actionMemberReport(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = db.executeSql(db.getMemberWeeklyReport());

        createMemberReport(rs);
    }

    public void actionManagerReport(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = db.executeSql(db.getManagersWeeklyReport());

        createManagerReport(rs);
    }

    /**
    * Used to build out the member report and then writes it to file.
    */
    private void createMemberReport(ResultSet rs) throws SQLException {
        if(rs.isLast()){
          System.out.println("No report to generate.");
          return;
        }

        String folderpath = fh.createWeeklyFolder("reports/members/");

        while(rs.next()) {
          String memName = rs.getString("mem_name");
          int memId = rs.getInt("mem_id");
          String memberReport = "Name: " + rs.getString("mem_name");
          memberReport += String.format("\nID: %d", rs.getInt("mem_id"));
          memberReport += String.format("\nAddress: %s, ", rs.getString("mem_addr"));
          memberReport += String.format("%s, ", rs.getString("mem_city"));
          memberReport += String.format("%s ", rs.getString("mem_state"));
          memberReport += String.format("%s", rs.getInt("mem_zip"));
          memberReport += "\nServices received:";
          do{
            memberReport += String.format("\n\tService Date: %s", rs.getString("serv_dte"));
            memberReport += String.format("\n\tProvider Name: %s", rs.getString("prov_name"));
            memberReport += String.format("\n\tService Name: %s\n", rs.getString("serv_name"));
          }while(rs.next() && memName.equals(rs.getString("mem_name")));
          //Need this because the result set will be one record ahead due to the rs.next() from the do while
          rs.previous();
          fh.writeMemberReport(memberReport, folderpath, memName, memId);
        }

        System.out.println("done");
    }

    /**
    * Used to build out the manager report and then writes it to file.
    */
    private void createManagerReport(ResultSet rs) throws SQLException {
        String managerReport = "";

        //Creating weekly folder
        String folderpath = fh.createWeeklyFolder("reports/managers/");

        if(rs.isLast()){
          System.out.println("No report to generate.");
          return;
        }
        int numOfProv = 0,
            numOfConsul = 0,
            overallFeeTot = 0;
        while(rs.next()){
          managerReport += String.format("Provider ID: %d", rs.getInt("prov_id"));
          managerReport += String.format("\nProvider Name: %s", rs.getString("prov_name"));
          managerReport += String.format("\nTotal Consultations: %s", rs.getInt("Consultations"));
          managerReport += String.format("\nTotal Service Fee: $%s\n\n", rs.getInt("TotalServFee"));
          numOfProv++;
          numOfConsul += rs.getInt("Consultations");
          overallFeeTot += rs.getInt("TotalServFee");
        }
        managerReport += String.format("\nTotal number of providers: %d", numOfProv);
        managerReport += String.format("\nTotal number of consultations: %d", numOfConsul);
        managerReport += String.format("\nTotal service fee: $%d", overallFeeTot);
        fh.writeManagerReport(managerReport, folderpath);

    }
    
    /**
    * Used to build out the provider report and then writes it to file.
    */
    private void createProviderReport(ResultSet rs) throws SQLException {

        String folderpath = fh.createWeeklyFolder("reports/providers/");

        if(rs.isLast()){
          System.out.println("No report to generate.");
          return;
        }

        int totalFee = 0, totalConsultations = 0;
        while(rs.next()) {
          String proName = rs.getString("prov_name");
          int proId = rs.getInt("prov_id");
          String providerReport = "Name: " + rs.getString("prov_name");
          providerReport += String.format("\nID: %d", rs.getInt("prov_id"));
          providerReport += String.format("\nAddress: %s,", rs.getString("prov_addr"));
          providerReport += String.format(" %s, ", rs.getString("prov_city"));
          providerReport += String.format("%s ", rs.getString("prov_state"));
          providerReport += String.format("%s", rs.getInt("prov_zip"));
          providerReport += "\nServices provided:";
          do{
            providerReport += String.format("\n\tService Date: %s", rs.getString("serv_dte"));
            providerReport += String.format("\n\tSubmission Date: %s", rs.getDate("tim_stmp"));
            providerReport += String.format("\n\tMember Name: %s", rs.getString("mem_name"));
            providerReport += String.format("\n\tService Code: %s", rs.getInt("serv_id"));
            providerReport += String.format("\n\tService Fee: $%d\n", rs.getInt("serv_fee"));
            totalFee += rs.getInt("serv_fee");
            totalConsultations++;
          }while(rs.next() && proName.equals(rs.getString("prov_name")));
          //Need this because the result set will be one record ahead due to the rs.next() from the do while
          rs.previous();
          providerReport += String.format("\nTotal number of consultations: %s", totalConsultations);
          providerReport += String.format("\nTotal fee for the week: $%s", totalFee);
          fh.writeProviderReport(providerReport, folderpath, proName, proId);
        }

        createEFT(folderpath);
        System.out.println("done");
    }

    /**
    * Used to build out the eft report and then writes it to file.
    */
    private void createEFT(String weeklyPath) throws SQLException{
        String folderpath = fh.createEFTWeeklyFolder(weeklyPath);

        ResultSet rs = db.executeSql(db.getProviderWeeklyFee());

        if(rs.isLast()){
            System.out.println("No report to generate.");
            return;
        }
        while(rs.next()) {
            String providerEFT = "Name: " + rs.getString("prov_name");
            providerEFT += String.format("\nID: %d", rs.getInt("prov_id"));
            providerEFT += String.format("\nTotal Fee: %s", rs.getString("total_fee"));
            fh.writeEFT(providerEFT, folderpath, rs.getString("prov_name"));
        }

        System.out.println("done");
    }


    public void cancel(ActionEvent actionEvent) { sc.setView(View.CHOICE); }
}
