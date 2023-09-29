package com.example.ema_java.controllers;

import com.example.ema_java.eMAApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class InicioSesionController {
    @FXML
    private TextField tField;
    @FXML
    private PasswordField pwField;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        if (Objects.equals(tField.getText(), "hola") && Objects.equals(pwField.getText(), "quihubo")) {
            Stage inicio = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(eMAApplication.class.getResource("inicio-usuario-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            inicio.setTitle("Inicio");
            inicio.setScene(scene);
            inicio.show();

            eMAApplication.windows.get("inicioDeSesion").hide();
        }
    }
}