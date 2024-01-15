package com.example.javafx;

import Backend.User.User;
import Backend.User.UserDaoImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    public double x,y;
    @FXML
    private AnchorPane topBar;
    @FXML
    private Parent root;
    @FXML
    private Label label;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private UserDaoImpl userDao = new UserDaoImpl();


        // ... (other declarations)

        public void login(ActionEvent event) throws IOException {
            String username = usernameField.getText();
            String password = passwordField.getText();

            List<User> userList = userDao.getUsersByInfo(username, password);

            if (!userList.isEmpty()) {
                // Successful login
                changeScene(event, "/com/example/javafx/admin.fxml");
            } else {
                // Incorrect username or password
                label.setText("Username or password incorrect");
            }
        }

        public void changeScene(ActionEvent event, String file) throws IOException {
            root = FXMLLoader.load(getClass().getResource(file));
            scene = new Scene(root);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            scene.setFill(Color.TRANSPARENT);


            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

        }
    public void close(ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("close");
        alert.setHeaderText("you are closing the application");
        alert.setContentText("Are you sure you want to close the application ??");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(alert.showAndWait().get()== ButtonType.OK){
            stage.close();

        }
    }


    public void nextLogin(ActionEvent Event){
        passwordField.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.ENTER) {
                try {
                    login(Event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void nextPassword(ActionEvent Event) {
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBar.setOnMousePressed(Event->{
            x=Event.getSceneX();
            y=Event.getSceneY();

        });
        topBar.setOnMouseDragged(event ->{
            main.primaryStage.setX(event.getScreenX()-x);
            main.primaryStage.setY(event.getScreenY()-y );

        });

    }
    public void minimize_window(ActionEvent event){
        ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }
}



