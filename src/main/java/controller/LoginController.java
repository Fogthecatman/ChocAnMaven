package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;

/**
 * Created by Jacob on 11/6/17.
 */
public class LoginController {

    public Label errorLabel;
    private StateController sc;

    public TextField userField;

    public LoginController() {
        sc = StateController.getInstance();
        userField = new TextField();
        init();
    }


    private void init() {

    }


    //Validates id/password user enters in LoginView
    public boolean validate(int id, String password){
        //Query database with id
        //See if password matches
        //set User.id
        //return true or false
        return true;
    }

    //Queries DB to get role corresponding with User
    //Returns string (subject to change) with role, to populate GUI with proper View
    public String getRole(){
        //Query roleDB with user's id
        //Get role as String
        //return role to *********** to populate GUI w/ proper view

        return null; //Null til we implement properly
    }

    public void run() {

    }

    //@TODO regex validation for passing values
    public void submit(ActionEvent actionEvent) throws IOException {
        /*
        boolean valid = validate(Integer.parseInt(userField.getText()), passwordField.getText());

        if(!valid) {
            //put up error message
        }
        */

        sc.setView(View.OPERATOR);
    }


    private void showError(String error) {
        errorLabel.setText(error);
    }
}
