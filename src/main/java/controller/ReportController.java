package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import util.FileHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportController implements FxmlController {

    public JFXButton providerRptBtn;
    private FileHandler fh;



    private enum ReportType { MEMBER, PROVIDER, MANAGER }

    public ReportController() {
        fh = new FileHandler();
    }

    @Override
    public void viewLoad() {

    }

    public void actionProviderReport(ActionEvent actionEvent) {
    }

    public void actionMemberReport(ActionEvent actionEvent) {
    }

    public void actionManagerReport(ActionEvent actionEvent) {
    }

    @Override
    public void updateUser() {

    }

    public void createReport(ReportType type, ResultSet rs) throws SQLException {
        switch(type) {
            case MEMBER:
                createMemberReport(rs);
                break;
            case PROVIDER:
                createProviderReport(rs);
                break;
            case MANAGER:
                createManagerReport(rs);
                break;
            default: break;
        }
    }

    private void createMemberReport(ResultSet rs) throws SQLException {
        //@TODO write to file
        if(!rs.next()){
          System.out.println("No report to generate.");
          return;
        }

        while(rs.next()) {
          String memName = rs.getString("mem.mem_name");
          int memId = rs.getInt("mem.mem_id");
          String memberReport = rs.getString("mem.mem_name");
          memberReport += String.format("\n%d", rs.getInt("mem.mem_id"));
          memberReport += String.format("\n%s", rs.getString("mem.mem_addr"));
          memberReport += String.format("\n%s", rs.getString("mem.mem_city"));
          memberReport += String.format("\n%s", rs.getString("mem.mem_state"));
          memberReport += String.format("\n%s", rs.getInt("mem.mem_zip"));
          memberReport += "\nServices received:";
          do{
            memberReport += String.format("\n\t%s", rs.getDate("servhs.serv_dte"));
            memberReport += String.format("\n\t%s", rs.getString("prov.prov_name"));
            memberReport += String.format("\n%s", rs.getString("serv.serv_name"));
          }while(rs.next() && memName.equals(rs.getString("mem.mem_name")));
          fh.writeMemberReport(memberReport, memName, memId);
        }
    }

    private void createManagerReport(ResultSet rs) throws SQLException {
        //@TODO format ResultSet to ManagerReport, then write to file
        String managerReport = "";

        if(!rs.next()){
          System.out.println("No report to generate.");
          return;
        }
        int numOfProv = 0,
            numOfConsul = 0,
            overallFeeTot = 0;
        while(rs.next()){
          managerReport = String.format("%d", rs.getInt("prov.prov_id"));
          managerReport += String.format("\n%s", rs.getString("prov.prov_name"));
          managerReport += String.format("\n%s", rs.getInt("Consultations"));
          managerReport += String.format("\n%s", rs.getInt("TotalServFee"));
          numOfProv++;
          numOfConsul += rs.getInt("Consultations");
          overallFeeTot += rs.getInt("TotalServFee");
        }
        managerReport += String.format("\nTotal number of providers: %d", numOfProv);
        managerReport += String.format("\nTotal number of consultations: %d", numOfConsul);
        managerReport += String.format("\nTotal service fee: %d", overallFeeTot);
        fh.writeManagerReport(managerReport);
    }

    private void createProviderReport(ResultSet rs) throws SQLException {
        //TODO format ResultSet to ProviderReport, then write to file
        if(rs.isLast()){
          System.out.println("No report to generate.");
          return;
        }
        int totalFee = 0, totalConsultations = 0;
        while(rs.next()) {
          String proName = rs.getString("prov.prov_name");
          int proId = rs.getInt("prov.prov_id");
          String providerReport = rs.getString("prov.prov_name");
          providerReport += String.format("\n%d", rs.getInt("prov.prov_id"));
          providerReport += String.format("\n%s", rs.getString("prov.prov_addr"));
          providerReport += String.format("\n%s", rs.getString("prov.prov_city"));
          providerReport += String.format("\n%s", rs.getString("prov.prov_state"));
          providerReport += String.format("\n%s", rs.getInt("prov.prov_zip"));
          providerReport += "\nServices provided:";
          do{
            providerReport += String.format("\n\t%s", rs.getDate("servhs.serv_dte"));
            providerReport += String.format("\n\t%s", rs.getDate("servhs.tim_stmp"));
            providerReport += String.format("\n\t%s", rs.getString("mem.mem_name"));
            providerReport += String.format("\n\t%s", rs.getInt("serv.serv_id"));
            providerReport += String.format("\n\t%d", rs.getInt("servhs.serv_fee"));
            totalFee += rs.getInt("servhs.serv_fee");
            totalConsultations++;
          }while(rs.next() && proName.equals(rs.getString("prov.prov_name")));
          rs.previous();
          providerReport += String.format("\nTotal number of consultations: %s", totalConsultations);
          providerReport += String.format("\nTotal fee for the week: %s", totalFee);
          fh.writeProviderReport(providerReport, proName, proId);
        }
    }


}
