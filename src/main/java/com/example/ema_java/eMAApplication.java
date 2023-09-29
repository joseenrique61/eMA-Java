package com.example.ema_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Dictionary;

public class eMAApplication extends Application {
    public static Dictionary<String, Stage> windows;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(eMAApplication.class.getResource("inicio-sesion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Iniciar sesión");
        stage.setScene(scene);
        stage.show();

        windows.put("inicioDeSesion", stage);
    }

    public static void main(String[] args) {
        launch();
    }
}