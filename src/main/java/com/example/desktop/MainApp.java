package com.example.desktop;
import com.example.desktop.controllers.FoodMenuController;
import com.example.desktop.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLoginView();
    }

    public void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setMainApp(this);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void showFoodMenuView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("food_menu.fxml"));
        Parent root = loader.load();
        FoodMenuController foodMenuController = loader.getController();
        // Здесь можно передать необходимые данные или вызвать методы контроллера

        primaryStage.setTitle("Food Menu");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
