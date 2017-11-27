package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.lang.Integer;
import model.User;

public class ServiceEntryController {

    public Label errorLabel;
    private StateController sc;

    public TextField serviceIdField;
    public TextArea commentArea;
    public TextField memberIdField;
    public TextField dateServiceField;

    public ServiceEntryController() {
        sc = StateController.getInstance();
    }

    //Gets data from UI's text fields, writes values to database
    //@TODO: Need to implement some way to prevent submission if field have invalid data
    public void submit(ActionEvent actionEvent) {
       int serviceID = Integer.parseInt(serviceIdField.getText());
       String comment = commentArea.getText();
       int memberID = Integer.parseInt(memberIdField.getText());
       String date = dateServiceField.getText();

       //Write values to database
        //Display "Submission successful on GUI when completed
    }

    public void cancel(ActionEvent actionEvent) {
        //@TODO This call throws an error due to the changes in scenes. Need help fixing.
        sc.setView(View.OPERATOR);
    }

    private void showError(String error) {
        errorLabel.setText(error);
    }

    public void validateMemberID(ActionEvent actionEvent) {

    }

    public void validateServiceCode(ActionEvent actionEvent) {
        //Validates Service Code
        int serviceID = Integer.parseInt(serviceIdField.getText());

        //query DB with service ID
        //If it exists, fine, otherwise mark as invalid
    }
}
