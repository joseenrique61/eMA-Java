package com.example.ema_java.controllers;

import com.example.ema_java.services.DatabaseService;
import com.example.ema_java.eMAApplication;
import com.example.ema_java.services.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InicioSesionController extends ControllerBase {
    @FXML
    private TextField tUsuario;
    @FXML
    private PasswordField pwField;
    @FXML
    private Label errorLabel;

    public InicioSesionController(NavigationService.WindowType window) {
        super(window);
    }

    @FXML
    protected void onIniciarSesionButtonClick() {
        if (eMAApplication.databaseService.checkUserIsCorrect(tUsuario.getText(), pwField.getText())) {
            eMAApplication.usuario = tUsuario.getText();
            showInicioUsuario();
        }
        else {
            errorLabel.setVisible(true);
        }
    }

    @FXML
    protected void onRegistrarButtonClick() {
        showRegistrarUsuario();
    }

    private void showInicioUsuario() {
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.INICIO_USUARIO, window, tUsuario.getText());
    }

    private void showRegistrarUsuario(){
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.REGISTRAR_USUARIO, window);
    }
}