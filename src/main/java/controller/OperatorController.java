package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.User;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperatorController implements FxmlController {

    public JFXTextField addressField;
    public JFXTextField zipField;
    public JFXTextField stateField;
    public JFXTextField cityField;
    public JFXTextField nameField;
    public JFXTextField idField;

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
        checkAcntSuspension.setVisible(true);

        userTypeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue value, String old, String newVal) {
                if(newVal.equals("Provider")) {
                    checkAcntSuspension.setVisible(false);
                }
                else {
                    checkAcntSuspension.setVisible(true);
                }
            }
        });
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
        sc.setView(View.CHOICE);
    }

    public void submitNewUser(ActionEvent actionEvent) {
    }

    public void submitEditUser(ActionEvent actionEvent) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        int zip = Integer.parseInt(zipField.getText());
        int acctErr = (checkAcntSuspension.isSelected()) ? 1:0;

        if(userTypeComboBox.getValue().toString().equals("Member")){
            db.updateMember(id, name, address, city, state, zip, acctErr);
        }
        else{
            db.updateProvider(id, name, address, city, state, zip);
        }


    }

    public void submitDeleteUser(ActionEvent actionEvent) {
    }

    public void changedComboType(ActionEvent actionEvent) {

    }
}
