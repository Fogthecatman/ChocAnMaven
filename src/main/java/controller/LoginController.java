package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import model.User;
import util.Regex;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Jacob on 11/6/17.
 *
 * TO FIX: combobutton needs to be reset after logout and login
 */
public class LoginController implements FxmlController {

    public Label errorLabel;
    private StateController sc;
    private DatabaseController db;

    public JFXTextField userField;

    private User u;

    public LoginController() {
        sc = StateController.getInstance();
        userField = new JFXTextField();
        db = DatabaseController.getInstance();
        u = User.getInstance();
    }

    @Override
    public void viewLoad() {
        errorLabel.setText("");
    }


    //Validates id user enters in LoginView
    public boolean validate(int id) throws SQLException{

        ResultSet rs;
        String role;

        int idLength = String.valueOf(id).length();

        if(idLength == 9){
            rs = db.executeSql(db.getChocAnProviderValidation(id));
            role = "provider";
        }
        else if(idLength == 6){
            rs = db.executeSql(db.getChocAnOperatorValidation(id));
            role = "operator";
        }
        else{
            rs = db.executeSql(db.getChocAnManagerValidation(id));
            role = "manager";
        }

        if(rs.next()){
            createUser(id, rs.getString("name"), role);
            return true;
        }

        return false;
    }

    public void submit(ActionEvent actionEvent) throws Exception {

        System.out.println(userField.getText() + " length:" + userField.getText().length());

        if(!Regex.characterLength(userField.getText(), 9)){
            showError("Invalid ID length.");
            return;
        }

        boolean valid = validate(Integer.parseInt(userField.getText()));

        if(!valid) {
            showError("Invalid ID number.");
        }
        else{
            userField.setText(""); //Clearing text for if user logs out.
            sc.setView(View.CHOICE);
        }

    }

    private void createUser(int id, String name, String role){
        System.out.println("Creating User");
        u.setName(name);
        u.setID(id);
        u.setPermissionLevel(role);
    }



    private void showError(String error) {
        errorLabel.setText(error);
    }


    @Override
    public void updateUser() {

    }
}
