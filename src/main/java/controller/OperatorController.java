package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
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
    public JFXComboBox userTypeComboBox;
    public JFXTextArea dataArea;
    public JFXCheckBox checkAcntSuspension;

    /* @TODO    Buttons (except for Service btn) will need to be added dynamically from User Permissions
    */

    private StateController sc;
    private DatabaseController db;

    public OperatorController() {
        sc = StateController.getInstance();
        db = DatabaseController.getInstance();
    }

    @Override
    public void viewLoad() {

    }

    public void validateID(ActionEvent actionEvent) {
        //why not just pass in what this equals to into the actual function
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
        db.createNewMember(nameField.getText(), addressField.getText(), 
                            cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText()));
    }

    @Override
    public void updateUser() {

    }
    
    //Canceling form
    public void cancel(ActionEvent actionEvent) {
    }

    public void submitNewUser(ActionEvent actionEvent) {
    }

    public void submitEditUser(ActionEvent actionEvent) {
    }

    public void submitDeleteUser(ActionEvent actionEvent) {
    }
}
