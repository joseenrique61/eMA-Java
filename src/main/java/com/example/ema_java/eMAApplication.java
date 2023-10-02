package com.example.ema_java;

import com.example.ema_java.services.DatabaseService;
import com.example.ema_java.services.NavigationService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Dictionary;
import java.util.Hashtable;

public class eMAApplication extends Application {
    public static Dictionary<NavigationService.WindowType, Stage> windows = new Hashtable<>();

    public static NavigationService navigationService = new NavigationService();

    public static DatabaseService databaseService = new DatabaseService();

    public static String usuario;

    @Override
    public void start(Stage stage) {
        navigationService.startApplication();
    }

    public static void main(String[] args) {
        launch();
    }
}