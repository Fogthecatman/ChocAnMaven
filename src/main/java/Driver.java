import controller.DatabaseController;
import controller.StateController;
import javafx.application.Application;
import javafx.stage.Stage;
import util.FileHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jacob on 11/6/2017.
 */
public class Driver extends Application{

    @Override
    public void start(Stage primaryStage) {

        StateController sc = StateController.getInstance();
        sc.run(primaryStage);

    }


    public static void main(String args[]){
        launch(args);

        //DatabaseController db = new DatabaseController();
        //db.executeSql(db.getChocAnMemberValidation(102685542));
    }

}
