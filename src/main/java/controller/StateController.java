package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import javax.xml.ws.Service;

/**
 * Created by Jacob on 11/17/17.
 *
 * This class is a Singleton for switching views
 *
 * @TODO: Naming conventions for variables.
 */

enum View { LOGIN, SERVICE_ENTRY, NEW_USER, EDIT_USER, DELETE_USER, CHOICE, REPORTS }

public class StateController {

    private static StateController sc;

    private Parent login, service, editUser, newUser, deleteUser, choice, report;
    private Scene currentScene, loginScene, serviceScene, editUserScene, newUserScene, deleteUserScene, choiceScene, reportScene;
    private Stage primaryStage;
    private FxmlController sec, cc, operatorController, loginController;
    

    protected StateController() {
        //This is only here to stop compilation errors
    }


    public static StateController getInstance() {

        if(sc == null) {
            sc = new StateController();
        }

        return sc;
    }

    public void run(Stage primaryStage) {

        this.primaryStage = primaryStage;

        try {
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
            login = loginLoader.load();
            loginController = loginLoader.getController();

            FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("../view/service_entry.fxml"));
            service = serviceLoader.load();
            sec = serviceLoader.getController();

            FXMLLoader choiceLoader = new FXMLLoader(getClass().getResource("../view/choice.fxml"));
            choice = choiceLoader.load();
            cc = choiceLoader.getController();

            FXMLLoader editUserLoader = new FXMLLoader(getClass().getResource("../view/edit_user.fxml"));
            editUser = editUserLoader.load();
            operatorController = editUserLoader.getController();

            FXMLLoader newUserLoader = new FXMLLoader(getClass().getResource("../view/new_user.fxml"));
            newUser = newUserLoader.load();

            FXMLLoader deleteUserLoader = new FXMLLoader(getClass().getResource("../view/delete_user.fxml"));
            deleteUser = deleteUserLoader.load();

            FXMLLoader reportLoader = new FXMLLoader(getClass().getResource("../view/reports.fxml"));
            report = reportLoader.load();

            loginScene = new Scene(login, 600, 700);
            serviceScene = new Scene(service, 600, 700);
            choiceScene = new Scene(choice, 600, 700);
            editUserScene = new Scene(editUser, 600, 700);
            newUserScene = new Scene(newUser, 600, 700);
            deleteUserScene = new Scene(deleteUser, 600, 700);
            reportScene = new Scene(report, 600, 700);

            currentScene = loginScene;

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Main event
        primaryStage.setTitle("ChocoAn");
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }


    public void setView(View v) {

        if(v == View.LOGIN) {
            currentScene = loginScene;
            loginController.viewLoad();
        }
        else if(v == View.SERVICE_ENTRY)
            currentScene = serviceScene;
        else if(v == View.CHOICE) {
            currentScene = choiceScene;
            System.out.println(cc);
            cc.updateUser();
            cc.viewLoad();
        }
        else if(v == View.NEW_USER) {
            currentScene = newUserScene;
            operatorController.viewLoad();
        }
        else if(v == View.EDIT_USER) {
            currentScene = editUserScene;
            operatorController.viewLoad();
        }
        else if(v == View.DELETE_USER) {
            currentScene = deleteUserScene;
            operatorController.viewLoad();
        }
        else if(v == View.REPORTS)
            currentScene = reportScene;

        //Set stylesheet for current
        //currentScene.getStylesheets().add(getClass().getResource("../view/css/app.css").toExternalForm());

        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

}