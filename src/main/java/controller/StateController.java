package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jacob on 11/17/17.
 *
 * This class is a Singleton for switching views
 *
 */

enum View { LOGIN, SERVICE_ENTRY, OPERATOR, CHOICE }

public class StateController {

    private static StateController sc;

    private Parent login, service, operator, choice;
    private Scene currentScene, loginScene, serviceScene, operatorScene, choiceScene;
    private Stage primaryStage;


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

            login = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
            service = FXMLLoader.load(getClass().getResource("../view/service_entry.fxml"));
            choice = FXMLLoader.load(getClass().getResource("../view/choice.fxml"));
            operator = FXMLLoader.load(getClass().getResource("../view/operator.fxml"));

            loginScene = new Scene(login, 600, 700);
            serviceScene = new Scene(service, 600, 700);
            choiceScene = new Scene(choice, 600, 700);
            operatorScene = new Scene(operator, 600, 700);


            currentScene = loginScene;

            //Set stylesheet for current
            //currentScene.getStylesheets().add(getClass().getResource("../view/css/app.css").toExternalForm());

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Main event
        primaryStage.setTitle("ChocoAn Login");
        primaryStage.setScene(currentScene);
        primaryStage.show();
    }


    public void setView(View v) {

        if(v == View.LOGIN)
            currentScene = loginScene;
        else if(v == View.SERVICE_ENTRY)
            currentScene = serviceScene;
        else if(v == View.CHOICE)
            currentScene = choiceScene;
        else if(v == View.OPERATOR)
            currentScene = operatorScene;

        //Set stylesheet for current
        //currentScene.getStylesheets().add(getClass().getResource("../view/css/app.css").toExternalForm());

        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

}