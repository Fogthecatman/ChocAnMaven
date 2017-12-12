package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.User;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jacob on 12/2/17.
 */
public class ChoiceController implements FxmlController {

    public JFXComboBox userComboBox;
    public JFXButton reportsButton;
    public JFXButton serviceEntryButton;
    public JFXButton addUsersButton;
    public JFXButton deleteUsersButton;
    private StateController sc;
    private User u;

    public ChoiceController() {
        u = User.getInstance();
        sc = StateController.getInstance();
    }

    @Override
    public void viewLoad() {

        //@TODO: Fix this to make it shows correct buttons
        String permLvl = u.getPermissionLevel();

        if(permLvl.equals("provider")){
            reportsButton.setVisible(false);
        }
    }

    public void serviceEntry(ActionEvent actionEvent) {
        sc.setView(View.SERVICE_ENTRY);
    }

    public void printReports(ActionEvent actionEvent) {
        sc.setView(View.REPORTS);
    }

    public void editUsers(ActionEvent actionEvent) {
        sc.setView(View.OPERATOR);
    }

    @Override
    public void updateUser() {
        userComboBox.setPromptText(u.getName());
    }


    public void addUsers(ActionEvent actionEvent) {
    }

    public void deleteUsers(ActionEvent actionEvent) {
    }
}
