package com.example.desktop.controllers;

import com.example.desktop.controllers.FoodItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FoodMenuController implements Initializable {
    private static final String BASE_URL = "http://localhost:8000/api/v1/";
    private static final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchFoodMenu();
    }

    private void fetchFoodMenu() {
        Request request = new Request.Builder()
                .url(BASE_URL + "food/")
                .build();

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
                    Type listType = new TypeToken<List<FoodItem>>(){}.getType();
                    List<FoodItem> foodItems = gson.fromJson(responseBody, listType);

                    Platform.runLater(() -> {
                        for (FoodItem foodItem : foodItems) {
                            createFoodCard(foodItem);
                        }
                    });
                } else {
                    // Обработка ошибок
                }
            }
        });
    }

    private void createFoodCard(FoodItem foodItem) {
        ImageView imageView;
        if (foodItem.getImageUrl() != null && !foodItem.getImageUrl().isEmpty()) {
            imageView = new ImageView(new Image(foodItem.getImageUrl()));
        } else {
            // Установка пустого изображения, если ссылка на изображение не указана
            imageView = new ImageView(new Image("https://via.placeholder.com/150"));
        }
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        Label nameLabel = new Label(foodItem.getName());
        Label descriptionLabel = new Label(foodItem.getDescription());
        Label priceLabel = new Label(String.valueOf(foodItem.getPrice()));

        gridPane.addRow(gridPane.getRowCount(), imageView, nameLabel, descriptionLabel, priceLabel);
    }
}
