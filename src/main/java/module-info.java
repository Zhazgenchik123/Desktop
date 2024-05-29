module com.example.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires okhttp3;

    opens com.example.desktop.controllers;
    opens com.example.desktop to javafx.fxml;
    exports com.example.desktop;
    exports com.example.desktop.controllers;

}