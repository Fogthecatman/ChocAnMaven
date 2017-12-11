package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperatorController implements FxmlController {

    public JFXTextField addressField;
    public JFXTextField zipField;
    public JFXTextField stateField;
    public JFXTextField cityField;
    public JFXTextField nameField;
    public TextField idField;

    /* @TODO    Buttons (except for Service btn) will need to be added dynamically from User Permissions
    */

    private StateController sc;
    private DatabaseController db;

    public OperatorController() {
        sc = StateController.getInstance();
        db = DatabaseController.getInstance();
    }

    public void validateID(ActionEvent actionEvent) {
        int memberID = Integer.parseInt(idField.getText());
        ResultSet rs = db.executeSql(db.getChocAnMemberValidation(memberID));

        boolean notEmpty = false;
        try {
            notEmpty = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(notEmpty) {
            try {
                System.out.println(rs.getString("mem_name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void createNewUser() {
        String[] props = new String[7];
        ResultSet rs = db.executeSql(db.getLargestMemberID());
        int maxID = 0;
        try{
            if(rs.next()) {
                maxID = rs.getInt(1);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        if(maxID == 0){
            //@TODO: Validation for id
            // Not 0
            //Also not more than 4 digits
        }
        else
            maxID ++;


        props[0] = "" + maxID; //@Todo create way to make the mem_id next id from list
        props[1] = "'" + nameField.getText()+"'";
        props[2] = "'" + addressField.getText()+"'";
        props[3] = "'" + cityField.getText()+"'";
        props[4] = "'" + stateField.getText()+"'";
        props[5] = zipField.getText();
        props[6] = "0";
        db.createNewMember(props);
    }

    @Override
    public void updateUser() {

    }
}
