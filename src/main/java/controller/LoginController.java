package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.Regex;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by Jacob on 11/6/17.
 */
public class LoginController {

    public Label errorLabel;
    private StateController sc;
    private DatabaseController db;

    public TextField userField;

    public LoginController() {
        sc = StateController.getInstance();
        userField = new TextField();
        db = new DatabaseController();
        init();
    }


    private void init() {

    }


    //Validates id user enters in LoginView
    public boolean validate(int id){

        boolean notEmpty = false;
        ResultSet rs = db.executeSql(db.getChocAnProviderValidation(id));

        try{
            notEmpty = rs.next();
            /*if(notEmpty){
                rs.first();
                createUser(id);

            }*/
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println("blah");
        }

        return notEmpty;
    }

    //Queries DB to get role corresponding with User
    //Returns string (subject to change) with role, to populate GUI with proper View
    //@TODO: Populate role_table with values
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

        if(!Regex.characterLength(userField.getText(), 4)){
            showError("Invalid ID length.");
            return;
        }

        boolean valid = validate(Integer.parseInt(userField.getText()));

        if(!valid) {
            showError("Invalid Provider number.");
        }
        else{
            sc.setView(View.CHOICE);
        }

    }

    private void createUser(int id){
        System.out.println("in createUser");
    }



    private void showError(String error) {
        errorLabel.setText(error);
    }
}
