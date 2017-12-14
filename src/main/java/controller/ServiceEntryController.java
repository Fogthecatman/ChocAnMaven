package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ServiceEntryController implements Initializable, FxmlController {

    private StateController sc;
    private DatabaseController db;
    private User u;

    public Label errorLabel;

    private boolean memberIsValid;
    private boolean serviceIsValid;

    @FXML
    public JFXDatePicker dateSelector;

    public JFXButton validateIdBtn;
    public JFXButton validateServiceBtn;
    public JFXButton submitBtn;

    public JFXTextArea commentArea;

    public JFXTextField serviceIdField;
    public JFXTextField memberIdField;

    private  RequiredFieldValidator serviceRf, serviceRf2;

    public ServiceEntryController() {
        sc = StateController.getInstance();
        db = DatabaseController.getInstance();
        u = User.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Setting date picker to current date
        dateSelector.setValue(LocalDate.now());
        dateSelector.setDefaultColor(Color.rgb(40, 59, 185));

    }

    @Override
    public void viewLoad(){

    }


    //Gets data from UI's text fields, writes values to database
    //@TODO: Need to implement some way to prevent submission if field have invalid data
    public void submit(ActionEvent actionEvent) throws Exception {

       int serviceID = Integer.parseInt(serviceIdField.getText());
       String comment = commentArea.getText();
       int memberID = Integer.parseInt(memberIdField.getText());
       String servDate = dateSelector.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

       //Write values to database
        String servEntryQuery = db.createNewServiceEntry(servDate, comment, u.getUserID(), memberID, serviceID);
        db.executeUpdateQuery(servEntryQuery);

        //Display "Submission successful on GUI when completed
        showError("Written to Database!");
    }

    public void cancel(ActionEvent actionEvent) {
        //@TODO This call throws an error due to the changes in scenes. Need help fixing.
        sc.setView(View.CHOICE);
    }

    private void showError(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void updateUser() {

    }


    public void validateID(ActionEvent actionEvent) throws SQLException {
        //cannot have null field
        if(memberIdField.getText().equals(""))
            return;

        int memID = Integer.parseInt(memberIdField.getText());
        boolean notEmpty = false;
        ResultSet rs = db.executeSql(db.getChocAnMemberValidation(memID));

        notEmpty = rs.next();

        if(notEmpty){
            memberIdField.setUnFocusColor(Color.GREEN);
            validateIdBtn.setStyle("-fx-background-color: #00aa00;");
            memberIsValid = true;
            //submitBtnValidation();
        }
        else{
            memberIdField.setUnFocusColor(Color.RED);
            validateIdBtn.setStyle("-fx-background-color: #ff0000;");
            memberIsValid = false;
        }
    }

    public void validateService(ActionEvent actionEvent) throws SQLException {
        //cannot have null field
        if(serviceIdField.getText().equals(""))
            return;

        int servCode = Integer.parseInt(serviceIdField.getText());
        ResultSet rs = db.executeSql(db.getChocAnServiceValidation(servCode));

        if(rs.next()){
            serviceIdField.setUnFocusColor(Color.GREEN);
            validateServiceBtn.setStyle("-fx-background-color: #00aa00;");
            serviceIsValid = true;
            //submitBtnValidation();
        }
        else{
            serviceIdField.setUnFocusColor(Color.RED);
            validateServiceBtn.setStyle("-fx-background-color: #ff0000;");
            serviceIsValid = false;
        }
    }

    //Here is where we validate service codes, we can make a call do DB here
    private boolean validateServiceID(String id) {
        boolean valid = (id.length() == 4);
        System.out.println(id + " validating " + valid);
        return valid;
    }

    /*private void submitBtnValidation(){
        if(memberIsValid && serviceIsValid)
            submitBtn.setDisable(false);
    }*/

}
