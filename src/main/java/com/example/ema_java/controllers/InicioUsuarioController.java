package com.example.ema_java.controllers;

import com.example.ema_java.models.MedicoModel;
import com.example.ema_java.eMAApplication;
import com.example.ema_java.services.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class InicioUsuarioController extends ControllerBase {
    @FXML
    private Label lbNombre;
    @FXML
    private ComboBox<String> cbPacientes;
    @FXML
    private Button bNuevaHistoria;
    @FXML
    private Label lbHistoria;
    @FXML
    private ComboBox<String> cbHistorias;

    public InicioUsuarioController(NavigationService.WindowType window) {
        super(window);
    }

    @Override
    public void setValues() {
        MedicoModel medicoModel = eMAApplication.databaseService.getMedicoModel(eMAApplication.usuario);
        lbNombre.setText("Bienvenido/a, Dr/a. " + medicoModel.Nombre + ".\n" + medicoModel.Especialidad);

        cbPacientes.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> onCbPacientesSelectionChanged());
        cbHistorias.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> onCbHistoriasSelectionChanged());
        setCbPacientesItems();
    }

    public void setCbPacientesItems() {
        cbPacientes.setItems(eMAApplication.databaseService.getPacientesList());
    }

    public void setCbHistoriasItems() {
        cbHistorias.setItems(eMAApplication.databaseService.getFechasList(eMAApplication.usuario, getPacienteCedula()));
    }

    @FXML
    private void onNuevoPacienteClicked() {
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.REGISTRAR_PACIENTE, window);
    }

    @FXML
    private void onNuevaHistoriaClicked() {
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.REGISTRAR_HISTORIA, window, getPacienteCedula());
    }

    private String getPacienteCedula() {
        return cbPacientes.getValue().split(" - ")[1];
    }

    private String getFechaHistoria() {
        return cbHistorias.getValue();
    }

    private void onCbPacientesSelectionChanged() {
        bNuevaHistoria.setVisible(true);
        lbHistoria.setVisible(true);
        cbHistorias.setVisible(true);

        bNuevaHistoria.setDisable(eMAApplication.databaseService.checkFechaExists(LocalDate.now(), eMAApplication.usuario, getPacienteCedula()));

        setCbHistoriasItems();
    }

    private void onCbHistoriasSelectionChanged() {
        showLeerHistoria();
    }

    private void showLeerHistoria() {
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.LEER_HISTORIA, window, getPacienteCedula(), getFechaHistoria());
    }
}