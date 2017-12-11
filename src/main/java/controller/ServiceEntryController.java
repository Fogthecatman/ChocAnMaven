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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ServiceEntryController implements Initializable, FxmlController {

    private StateController sc;
    private DatabaseController db;

    public Label errorLabel;

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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Setting date picker to current date
        dateSelector.setValue(LocalDate.now());
        dateSelector.setDefaultColor(Color.rgb(40, 59, 185));

        serviceRf = new RequiredFieldValidator();
        serviceRf2 = new RequiredFieldValidator();


        //http://useof.org/java-open-source/com.jfoenix.validation.RequiredFieldValidator

        serviceRf.setMessage("Input Required");
        serviceRf2.setMessage("Must be 4 Characters");

        serviceIdField.focusedProperty().addListener((o, oldValue, newValue) -> {
            //if(!newValue) {
                //serviceIdField.getValidators().add(serviceRf);
                //serviceIdField.validate();
            //}
            if (!validateServiceID(serviceIdField.getText())) {
                serviceIdField.getValidators().add(serviceRf2);
                serviceIdField.validate();
            }
        });

        //memberIdField.getValidators().add(rf2);

        memberIdField.focusedProperty().addListener((o, oldValue, newValue) -> {
            if(!newValue) {
                memberIdField.validate();
            }
        });

        //Submit button is disabled until the mandatory fields are filled
        submitBtn.disableProperty().bind((memberIdField.textProperty().isNotEmpty().and(serviceIdField.textProperty().isNotEmpty()).not()));
    }

    //Gets data from UI's text fields, writes values to database
    //@TODO: Need to implement some way to prevent submission if field have invalid data
    public void submit(ActionEvent actionEvent) {

       //int serviceID = Integer.parseInt(serviceIdField.getText());
       //String comment = commentArea.getText();
      // int memberID = Integer.parseInt(memberIdField.getText());
       //String date = dateServiceField.getText();


       //Write values to database
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

    public void validateID(ActionEvent actionEvent) {
        int memID = Integer.parseInt(memberIdField.getText());
        boolean notEmpty = false;
        ResultSet rs = db.executeSql(db.getChocAnMemberValidation(memID));

        try{
            notEmpty = rs.next();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        //@TODO: Need to figure out what to do when it's validated
    }

    public void validateService(ActionEvent actionEvent) {
    }

    //Here is where we validate service codes, we can make a call do DB here
    private boolean validateServiceID(String id) {
        boolean valid = (id.length() == 4);
        System.out.println(id + " validating " + valid);
        return valid;
    }
}
