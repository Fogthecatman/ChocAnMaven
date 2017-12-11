package controller;

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
    private StateController sc;
    private User u;

    public ChoiceController() {
        u = User.getInstance();
        sc = StateController.getInstance();
    }

    public void serviceEntry(ActionEvent actionEvent) {
        sc.setView(View.SERVICE_ENTRY);
    }

    public void printReports(ActionEvent actionEvent) {
    }

    public void editUsers(ActionEvent actionEvent) {
        sc.setView(View.OPERATOR);
    }

    @Override
    public void updateUser() {
        userComboBox.setPromptText(u.getName());
    }
}
