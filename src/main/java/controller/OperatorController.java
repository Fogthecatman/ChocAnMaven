package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import model.User;

public class OperatorController  {

    public JFXTextField addressField;
    public JFXTextField zipField;
    public JFXTextField stateField;
    public JFXTextField cityField;
    public JFXTextField nameField;
    public TextField idField;

    /* @TODO    Buttons (except for Service btn) will need to be added dynamically from User Permissions
    */

    private StateController sc;

    public OperatorController() {
        sc = StateController.getInstance();
    }

    public void validateID(ActionEvent actionEvent) {
        //Validate ID for whichever table we are accessing
    }
}
