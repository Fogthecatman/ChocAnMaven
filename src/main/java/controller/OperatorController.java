package controller;

import javafx.event.ActionEvent;
import model.User;

public class OperatorController  {

    /* @TODO    Buttons (except for Service btn) will need to be added dynamically from User Permissions
    */

    private StateController sc;

    public OperatorController() {
        sc = StateController.getInstance();
    }

    public void serviceEntry(ActionEvent actionEvent) {
        sc.setView(View.SERVICE_ENTRY);
    }
}
