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
 */

enum View { LOGIN, SERVICE_ENTRY, OPERATOR, CHOICE, REPORTS }

public class StateController {

    private static StateController sc;

    private Parent login, service, operator, choice, report;
    private Scene currentScene, loginScene, serviceScene, operatorScene, choiceScene, reportScene;
    private Stage primaryStage;
    FxmlController lc, sec, cc, oc, rc;
    

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
            lc = loginLoader.getController();

            FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("../view/service_entry.fxml"));
            service = serviceLoader.load();
            sec = serviceLoader.getController();

            FXMLLoader choiceLoader = new FXMLLoader(getClass().getResource("../view/choice.fxml"));
            choice = choiceLoader.load();
            cc = choiceLoader.getController();

            FXMLLoader opLoader = new FXMLLoader(getClass().getResource("../view/operator.fxml"));
            operator = opLoader.load();
            oc = opLoader.getController();

            FXMLLoader reportLoader = new FXMLLoader(getClass().getResource("../view/reports.fxml"));
            report = reportLoader.load();
            rc = reportLoader.getController();

            loginScene = new Scene(login, 600, 700);
            serviceScene = new Scene(service, 600, 700);
            choiceScene = new Scene(choice, 600, 700);
            operatorScene = new Scene(operator, 600, 700);
            reportScene = new Scene(report, 600, 700);

            currentScene = loginScene;

            //Set stylesheet for current
            //currentScene.getStylesheets().add(getClass().getResource("../view/css/app.css").toExternalForm());

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
        }
        else if(v == View.SERVICE_ENTRY)
            currentScene = serviceScene;
        else if(v == View.CHOICE) {
            currentScene = choiceScene;
            System.out.println(cc);
            cc.updateUser();
        }
        else if(v == View.OPERATOR)
            currentScene = operatorScene;
        else if(v == View.REPORTS)
            currentScene = reportScene;

        //Set stylesheet for current
        //currentScene.getStylesheets().add(getClass().getResource("../view/css/app.css").toExternalForm());

        primaryStage.setScene(currentScene);
        primaryStage.show();
    }

}