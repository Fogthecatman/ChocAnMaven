package controller;

import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by Ben on 11/14/17.
 */
public class DatabaseController {

    public ResultSet executeSql(String query)
    {
        try
        {
            // create our mysql database connection
            String hostName = "chocanbbj.database.windows.net";
            String dbName = "ChocAn";
            String user = "super@chocanbbj";
            String password = "bbjadmin=1";

            String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);

            //String url = "jdbc:sqlserver://chocanbbj.database.windows.net:1433;databaseName=chocanbbj;user=super;password=bbjadmin=1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            Connection conn = DriverManager.getConnection(url);

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet res = st.executeQuery(query);
            //st.close();
            System.out.println("Connected");
            System.out.println(res.toString());
            return res;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ResultSet res  = null;
            return res;
        }
    }

    /**
     * Gets the Managers weekly report. To get the total number of providers
     * just get the length of the result set since only providers who provided
     * services would be in the service history table. Total number of
     * consultations and overall fee would be a variable += res.get(column) which
     * would just keep a total count while looping through the result set.
     */
    public String getManagersWeeklyReport(int proNumber) {
        return String.format("select prov.prov_id, \n " +
                " prov.prov_name, \n " +
                " COUNT(servhs.mem_id) Consultations, \n " +
                " SUM(servhs.serv_fee) TotalServFee \n " +
                " from prov_tbl prov \n " +
                " join serv_his_tbl servhs \n " +
                "   on servhs.prov_id = prov.prov_id \n " +
                " where prov.prov_id = %d \n " +
                " group by prov.prov_id, " +
                "  prov.prov_name", proNumber);
    }

    /**
     * Gets the providers weekly fee. We could just use the data from the
     * getProviderWeeklyReport and just pass the total and account number
     * into the command that writes to file. Or we can just use this command.
     */
    public String getProviderWeeklyFee(int proNumber) {
        return String.format("select prov.prov_id, \n " +
                " SUM(servhs.serv_fee) \n " +
                "  from prov_tbl prov \n " +
                "  join serv_his_tbl servhs \n " +
                "    on servhs.prov_id = prov.prov_id \n " +
                "  where prov.prov_id = %d \n " +
                "  group by prov.prov_id", proNumber);
    }

    /**
     * Pretty self explanatory but used to get the info for the weekly
     * provider reports. Don't need to join on the service table because
     * all the information is on the serv_his_tbl. For total number of
     * consoltations that would just be a simple count of the number of rows
     * returned. For total fee would be a simple totalFee += res.get('serv_fee')
     * inside the while loop when looping over the result set.
     */
    public String getProviderWeeklyReport(int proNumber) {
        return String.format("select prov.*, \n " +
                "       mem.mem_name, \n " +
                "       mem.mem_id, \n " +
                "       servhs.serv_dte, \n " +
                "       servhs.tim_stmp, \n " +
                "       servhs.serv_id,  \n " +
                "       servhs.serv_fee \n " +
                "from prov_tbl prov \n " +
                "join serv_his_tbl servhs \n " +
                "  on servhs.prov_id = prov.prov_id \n " +
                "join mem_tbl mem \n " +
                "  on mem.mem_id = servhs.mem_id \n " +
                "where prov.prov_id = %d", proNumber);
    }

    /**
     * Pretty self explanatory but used to get the info for the weekly
     * member reports. Did a select * from member table because we need all the
     * columns except the flag one. To iterate over this all you would need is
     * the member info off of the first record and then iterate over the result
     * set just grabbing the serv_dte, provider name and the service name.
     */
    public String getMemberWeeklyReport(int memNumber) {
        return String.format("select mem.*, \n " +
                "       servhs.serv_dte, \n " +
                "       prov.prov_name, \n " +
                "       serv.serv_name  \n " +
                "from mem_tbl mem \n " +
                "join serv_his_tbl servhs \n " +
                "  on servhs.mem_id = mem.mem_id \n " +
                "join prov_tbl prov \n " +
                "  on prov.prov_id = servhs.prov_id \n " +
                "join serv_tbl serv \n " +
                "  on serv.serv_id = servhs.serv_id \n " +
                "where mem.mem_id = %d", memNumber);
    }


    /**
     * This method is used so that the record is written to the Service History Table
     */
    public String getUpdateForServHisTable(String servDate, int proNumber,
                                           int memNumber,  int servNumber)
    {
        Date date = new Date();
        // This is needed cause you can't do a select inside a insert into statement
        String servFeeQuery =  getServFee(servNumber);
        /* execute the query and then save the resultset of serv fee to a double */
        Double servFee = 0.0;
        return String.format("insert into serv_his_tbl " +
                        "Values " +
                        "(%s, %s, %d, %d, %d, %d)",
                date.toString(), servDate, proNumber,
                memNumber, servNumber, servFee);
    }

    public String getServFee(int servNumber)
    {
        return String.format("select serv_fee from serv_tbl where serv_id = %d",
                servNumber);
    }

    /**
     * These methods are used for validating that a service, member or provider
     * actually exist within the database.
     */
    /**
     * The service validation is written like this to check to see if a service
     * number is passed in if it is it will use that. If it is not then the query
     * assumes that the provider quering for all the service codes so it does a
     * wide open search against the service table.
     */
    public String getChocAnServiceValidation(int servNumber)
    {
        String whereClause = "1 = 1";
        if(servNumber != 0){
            whereClause = String.format("serv_id = %d", servNumber);
        }
        // Not sure what data we actually need so we should modify this to only
        // return columns we care about.
        return String.format("select * from serv_tbl where %s", whereClause);
    }

    public String getChocAnMemberValidation(int memNumber)
    {
        return String.format("select 1 from mem_tbl where mem_id = %d", memNumber);
    }

    public String getChocAnProviderValidation(int proNumber)
    {
        return String.format("select * from prov_tbl where prov_id = %d", proNumber);
    }

}
