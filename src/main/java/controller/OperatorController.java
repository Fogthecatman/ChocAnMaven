package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.User;
import util.Regex;

import java.awt.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OperatorController implements Initializable, FxmlController {

    public JFXTextField addressField;
    public JFXTextField zipField;
    public JFXTextField stateField;
    public JFXTextField cityField;
    public JFXTextField nameField;
    public JFXTextField idField;

    public JFXComboBox userTypeComboBox;

    public JFXTextArea dataArea;

    public JFXCheckBox checkAcntSuspension;
    public Label errorLabel;

    private StateController sc;
    private DatabaseController db;
    private String view;

    public OperatorController() {
        sc = StateController.getInstance();
        db = DatabaseController.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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


    public void searchIDEdit(ActionEvent actionEvent) throws SQLException{
        int userID;
        ResultSet rs;
        String userType;

        if(Regex.isInteger(idField.getText()))
            userID = Integer.parseInt(idField.getText());
        else{
            showMessage("ID is not an integer.");
            return;
        }


        if(userTypeComboBox.getValue().equals("Member")){
            rs = db.executeSql(db.getMemberInfo(userID));
            userType = "mem";
        }
        else{
            rs = db.executeSql(db.getProviderInfo(userID));
            userType = "prov";
        }

        if(rs.next()){
            String name = rs.getString(userType + "_name");
            String address = rs.getString(userType + "_addr");
            String city = rs.getString(userType + "_city");
            String state = rs.getString(userType + "_state");
            int zip = rs.getInt(userType + "_zip");
            boolean acctFlag = false;
            if(userType.equals("mem"))
                acctFlag = (rs.getInt("acc_err_flg") != 0);

            nameField.setText(name);
            addressField.setText(address);
            cityField.setText(city);
            stateField.setText(state);
            zipField.setText(zip + "");
            if(userType.equals("mem"))
                checkAcntSuspension.setSelected(acctFlag);

        }

        idField.setDisable(true);

    }

    public void searchIDDelete(ActionEvent actionEvent) throws SQLException {
        int userID;
        ResultSet rs;
        String userType;

        if(Regex.isInteger(idField.getText()))
            userID = Integer.parseInt(idField.getText());
        else{
            showMessage("ID is not an integer.");
            return;
        }


        if(userTypeComboBox.getValue().equals("Member")){
            rs = db.executeSql(db.getMemberInfo(userID));
            userType = "mem";
        }
        else{
            rs = db.executeSql(db.getProviderInfo(userID));
            userType = "prov";
        }

        if(rs.next()){
            String name = rs.getString(userType + "_name");
            String address = rs.getString(userType + "_addr");
            String city = rs.getString(userType + "_city");
            String state = rs.getString(userType + "_state");
            int zip = rs.getInt(userType + "_zip");
            String acctFlgMsg = "";
            if(userType.equals("mem")){
                acctFlgMsg = (rs.getInt("acc_err_flg") == 0) ? "Account is valid.":"Account is flagged.";

            }


            String deleteUserText = String.format("Name: %s\n" +
                                                    "Address: %s\n" +
                                                    "City: %s\n" +
                                                    "State: %s\n" +
                                                    "Zip: %d\n%s", name, address, city, state, zip, acctFlgMsg);

            dataArea.setText(deleteUserText);

        }

        idField.setDisable(true);
    }
    

    @Override
    public void updateUser() {

    }

    public void cancelNew(){
        clearNewFields();
        sc.setView(View.CHOICE);
    }

    //Canceling form
    public void cancelEdit(ActionEvent actionEvent) {
        clearEditFields();
        sc.setView(View.CHOICE);
    }

    public void cancelDelete(ActionEvent actionEvent) {
        clearDeleteFields();
        sc.setView(View.CHOICE);
    }

    public void clearNewFields(){
        nameField.clear();
        addressField.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        showMessage("");
    }

    public void clearEditFields(){
        idField.clear();
        nameField.clear();
        addressField.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        checkAcntSuspension.setSelected(false);
        showMessage("");
        idField.setDisable(false);
    }

    public void clearDeleteFields(){
        idField.clear();
        dataArea.clear();
        showMessage("");
        idField.setDisable(false);
    }



    public void submitNewUser(ActionEvent actionEvent) {

        System.out.println(userTypeComboBox.getValue().toString());

        boolean validated = validateFields();

        //if fields aren't valid we need to
        if(!validated) return;


        if(userTypeComboBox.getValue().equals("Member")) {
            db.createNewMember(nameField.getText(), addressField.getText(),
                    cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText()));

            showMessage("Created new Member!");
        }
        else {
            db.createNewProvider(nameField.getText(), addressField.getText(),
                    cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText()));

            showMessage("Created new Provider!");
        }



    }

    public void submitEditUser(ActionEvent actionEvent) {
        if(!validateFields())
            return;

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

        showMessage("Changes successful!");
    }

    public void submitDeleteUser(ActionEvent actionEvent) {

    }

    public void changedComboType(ActionEvent actionEvent) {

    }

    private void showMessage(String error) {
        errorLabel.setText(error);
    }

    public boolean validateFields() {

        String errorMsg = "";

        if(!Regex.characterLength(nameField.getText(), 40)){
            errorMsg += "Name Field too many characters (40),\n";
        }

        if(!Regex.characterLength(addressField.getText(), 40)){
            errorMsg += "Address Field too many characters (40),\n";
        }

        if(!Regex.characterLength(cityField.getText(), 40)){
            errorMsg += "City Field too many characters (40),\n";
        }

        if(!Regex.exactCharLength(zipField.getText(), 5) || !Regex.isInteger(zipField.getText())){
            errorMsg += "Zip Field invalid (#####),\n";
        }

        if(!Regex.exactCharLength(stateField.getText(), 2)){
            errorMsg += "State Field too many characters (2),\n";
        }

        showMessage(errorMsg);

        //If errors in message then valid is false;
        return errorMsg.length() == 0;
    }

}
