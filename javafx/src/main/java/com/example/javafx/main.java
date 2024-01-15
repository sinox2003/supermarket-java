package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class main extends Application {

    public static Stage primaryStage;


    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage=stage;
        Parent root= FXMLLoader.load(getClass().getResource("/com/example/javafx/login.fxml"));
        Scene scene=new Scene(root);

        stage.setScene(scene);
//        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Supermarket");
        Image icon=new Image(getClass().getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);
        stage.show();
        stage.setOnCloseRequest(event ->{
            event.consume();
            close(stage);

        });

    }
    public void close(Stage stage){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("close");
        alert.setHeaderText("you are closing the application");
        alert.setContentText("Are you sure you want to close the application ??");
        if(alert.showAndWait().get()== ButtonType.OK){
            stage.close();
        }
    }

}
