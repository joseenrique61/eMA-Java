package com.example.ema_java.controllers;

import com.example.ema_java.models.MedicoModel;
import com.example.ema_java.eMAApplication;
import com.example.ema_java.services.DatabaseService;
import com.example.ema_java.services.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class InicioUsuarioController extends ControllerBase {
    private final String usuario;

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

    public InicioUsuarioController(NavigationService.WindowType window, String usuario) {
        super(window);
        this.usuario = usuario;
    }

    @Override
    public void setValues() {
        MedicoModel medicoModel = eMAApplication.databaseService.getMedicoModel(usuario);
        lbNombre.setText("Bienvenido/a, Dr/a. " + medicoModel.Nombre + ".\n" + medicoModel.Especialidad);

        cbPacientes.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> onCbPacientesSelectionChanged());
        setCbItems();
    }

    public void setCbItems() {
        cbPacientes.setItems(eMAApplication.databaseService.getPacientesList());
    }

    @FXML
    private void onNuevoPacienteClicked() {
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.REGISTRAR_PACIENTE, window);
    }

    @FXML
    private void onNuevaHistoriaClicked() {
        System.out.println("onNuevaHistoriaClicked");
    }

    private void onCbPacientesSelectionChanged() {
        bNuevaHistoria.setVisible(true);
        lbHistoria.setVisible(true);
        cbHistorias.setVisible(true);
    }
}