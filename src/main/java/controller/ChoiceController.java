package controller;

import javafx.event.ActionEvent;

import java.awt.*;

/**
 * Created by Jacob on 12/2/17.
 */
public class ChoiceController {

    private StateController sc;

    public ChoiceController() {
        sc = StateController.getInstance();
    }


    public void serviceEntry(ActionEvent actionEvent) {
        sc.setView(View.SERVICE_ENTRY);
    }

    public void printReports(ActionEvent actionEvent) {
    }

    public void editRecords(ActionEvent actionEvent) {
        sc.setView(View.OPERATOR);
    }
}
