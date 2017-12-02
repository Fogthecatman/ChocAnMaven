package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import model.User;

public class OperatorController  {

    /* @TODO    Buttons (except for Service btn) will need to be added dynamically from User Permissions
    */

    private StateController sc;

    public TextField idField;

    public OperatorController() {
        sc = StateController.getInstance();
    }

    public void validateID(ActionEvent actionEvent) {
        //Validate ID for whichever table we are accessing
    }
}
