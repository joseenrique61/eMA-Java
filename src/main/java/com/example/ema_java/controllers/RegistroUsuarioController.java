package com.example.ema_java.controllers;

import com.example.ema_java.services.DatabaseService;
import com.example.ema_java.eMAApplication;
import com.example.ema_java.models.MedicoModel;
import com.example.ema_java.services.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;

public class RegistroUsuarioController extends ControllerBase {
    @FXML
    private TextField tUsuario;
    @FXML
    private PasswordField pwContrasena;
    @FXML
    private PasswordField pwRepetirContrasena;
    @FXML
    private TextField tNombre;
    @FXML
    private TextField tCedula;
    @FXML
    private TextField tEspecialidad;
    @FXML
    private Label errorLabel;

    public RegistroUsuarioController(NavigationService.WindowType window) {
        super(window);
    }

    @FXML
    private void onCrearUsuarioButtonClick() {
        if (eMAApplication.databaseService.checkUserAvailability(tUsuario.getText())) {
            errorLabel.setText("El usuario ingresado no está disponible.");
            errorLabel.setVisible(true);
            return;
        }
        else if (checkEmpty() && checkNotSamePassword()) {
            errorLabel.setText("Todos los campos deben estar llenos.\nLas contraseñas deben ser iguales.");
            errorLabel.setVisible(true);
            return;
        }
        else if (checkEmpty()) {
            errorLabel.setText("Todos los campos deben estar llenos.");
            errorLabel.setVisible(true);
            return;
        }
        else if (checkNotSamePassword()) {
            errorLabel.setText("Las contraseñas deben ser iguales.");
            errorLabel.setVisible(true);
            return;
        }

        errorLabel.setVisible(false);
        eMAApplication.databaseService.createUser(tUsuario.getText(), pwContrasena.getText(), createModel());
        showInicioDeSesion();
    }

    private MedicoModel createModel() {
        MedicoModel medicoModel = new MedicoModel();
        medicoModel.Nombre = tNombre.getText();
        medicoModel.Cedula = tCedula.getText();
        medicoModel.Especialidad = tEspecialidad.getText();
        return medicoModel;
    }

    private boolean checkEmpty() {
        return tUsuario.getText().isEmpty() && tNombre.getText().isEmpty()
                && tCedula.getText().isEmpty() && tEspecialidad.getText().isEmpty()
                && pwContrasena.getText().isEmpty() == pwRepetirContrasena.getText().isEmpty();
    }

    private boolean checkNotSamePassword() {
        return !Objects.equals(pwContrasena.getText(), pwRepetirContrasena.getText());
    }

    private void showInicioDeSesion() {
        eMAApplication.windows.get("inicioDeSesion").show();
        eMAApplication.windows.get("registroUsuario").close();
        eMAApplication.windows.remove("registroUsuario");
    }
}