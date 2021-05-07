package sample;

import database.DataSource;
import database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("GLA University Exam Portal");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
        DatabaseConnection.connectToDatabase();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
