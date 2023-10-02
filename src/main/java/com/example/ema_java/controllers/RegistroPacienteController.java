package com.example.ema_java.controllers;

import com.example.ema_java.eMAApplication;
import com.example.ema_java.models.PacienteModel;
import com.example.ema_java.services.DatabaseService;
import com.example.ema_java.services.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistroPacienteController extends ControllerBase {
    @FXML
    private TextField tNombre;
    @FXML
    private TextField tCedula;
    @FXML
    private TextField tEdad;
    @FXML
    private Label errorLabel;

    public RegistroPacienteController(NavigationService.WindowType window) {
        super(window);
    }

    @FXML
    private void onCrearPacienteButtonClick() {
        if (eMAApplication.databaseService.checkPacienteAvailability(tCedula.getText())) {
            errorLabel.setText("El paciente ingresado ya está registrado.");
            errorLabel.setVisible(true);
            return;
        }
        else if (checkEmpty()) {
            errorLabel.setText("Todos los campos deben estar llenos.");
            errorLabel.setVisible(true);
            return;
        }

        errorLabel.setVisible(false);
        eMAApplication.databaseService.createPaciente(createModel());
        showInicioUsuario();
    }

    private PacienteModel createModel() {
        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.Nombre = tNombre.getText();
        pacienteModel.Cedula = tCedula.getText();
        pacienteModel.Edad = tEdad.getText();
        return pacienteModel;
    }

    private boolean checkEmpty() {
        return tNombre.getText().isEmpty() || tCedula.getText().isEmpty() || tEdad.getText().isEmpty();
    }

    private void showInicioUsuario() {
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.INICIO_USUARIO, window);
    }
}