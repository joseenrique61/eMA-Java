module com.example.ema_java {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.ema_java to javafx.fxml;
    exports com.example.ema_java;
    exports com.example.ema_java.controllers;
    exports com.example.ema_java.models;
    exports com.example.ema_java.utils;
    opens com.example.ema_java.controllers to javafx.fxml;
    exports com.example.ema_java.services;
}