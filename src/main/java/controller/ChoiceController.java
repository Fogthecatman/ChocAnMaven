package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
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
    public JFXButton editUsersButton;
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

        hideButtons();

        if(permLvl.equals("provider")){
            serviceEntryButton.setVisible(true);
        }
        if(permLvl.equals("operator")){
            addUsersButton.setVisible(true);
            editUsersButton.setVisible(true);
            deleteUsersButton.setVisible(true);
        }
        if(permLvl.equals("manager")){
            reportsButton.setVisible(true);
        }
    }

    private void hideButtons() {
        reportsButton.setVisible(false);
        serviceEntryButton.setVisible(false);
        addUsersButton.setVisible(false);
        editUsersButton.setVisible(false);
        deleteUsersButton.setVisible(false);
    }

    public void serviceEntry(ActionEvent actionEvent) {
        sc.setView(View.SERVICE_ENTRY);
    }

    public void printReports(ActionEvent actionEvent) {
        sc.setView(View.REPORTS);
    }

    public void editUsers(ActionEvent actionEvent) {
        sc.setView(View.EDIT_USER);
    }

    @Override
    public void updateUser() {
        System.out.println(u.getName());
        userComboBox.setPromptText(u.getName());
    }


    public void addUsers(ActionEvent actionEvent) {
        sc.setView(View.NEW_USER);
    }

    public void deleteUsers(ActionEvent actionEvent) {
        sc.setView(View.DELETE_USER);
    }

    public void logOut(ActionEvent actionEvent) {
        //@TODO: reset user data to null
        sc.setView(View.LOGIN);
    }
}
