package com.example.desktop.controllers;

import com.example.desktop.MainApp;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;

public class LoginController {

    private static final String BASE_URL = "http://localhost:8000/api/";
    private static final OkHttpClient client = new OkHttpClient();

    @FXML
    private TextField user_name;

    @FXML
    private PasswordField password;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void login() {
        String username = user_name.getText();
        String userPassword = password.getText();

        // Создаем объект для передачи данных в JSON
        LoginData loginData = new LoginData(username, userPassword);
        Gson gson = new Gson();
        String jsonData = gson.toJson(loginData);

        // Создаем запрос на сервер
        RequestBody body = RequestBody.create(jsonData, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL + "login/")
                .post(body)
                .build();

        // Отправляем запрос асинхронно
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Обработка ошибок
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // Обработка успешного входа
                    // В responseBody должен содержаться токен доступа, который вы можете использовать дальше
                    Platform.runLater(() -> {
                        mainApp.showAlert("Success", "Login successful");
                        // Здесь вызывайте метод для отображения страницы с товарами
                        showFoodMenuPage();
                    });
                } else {
                    // Обработка ошибок
                }
            }
        });
    }

    // Метод для отображения страницы с товарами
    private void showFoodMenuPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/desktop/food.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Food Menu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
