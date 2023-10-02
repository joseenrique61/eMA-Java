package com.example.ema_java.controllers;

import com.example.ema_java.eMAApplication;
import com.example.ema_java.models.HistoriaModel;
import com.example.ema_java.models.PacienteModel;
import com.example.ema_java.services.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RegistroHistoriaController extends ControllerBase {
    private final String cedula;

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbEdad;
    @FXML
    private Label lbCedula;
    @FXML
    private TextArea tAntecedentes;
    @FXML
    private TextArea tAlergias;
    @FXML
    private TextArea tEnfAct;
    @FXML
    private TextArea tMotCons;
    @FXML
    private TextArea tExFis;
    @FXML
    private TextArea tRecipe;
    @FXML
    private TextArea tIndicaciones;
    @FXML
    private TextArea tDiagnostico;

    public RegistroHistoriaController(NavigationService.WindowType window, String cedula) {
        super(window);
        this.cedula = cedula;
    }

    @FXML
    private void onGuardarButtonClick() {
        eMAApplication.databaseService.createHistoria(createModel(), eMAApplication.usuario, cedula);
    }

    private HistoriaModel createModel() {
        HistoriaModel historiaModel = new HistoriaModel();
        historiaModel.Antecedentes = tAntecedentes.getText();
        historiaModel.Alergias = tAlergias.getText();
        historiaModel.MotivoDeConsulta = tMotCons.getText();
        historiaModel.EnfermedadActual = tEnfAct.getText();
        historiaModel.ExamenFisico = tExFis.getText();
        historiaModel.Recipe = tRecipe.getText();
        historiaModel.Indicaciones = tIndicaciones.getText();
        historiaModel.Diagnostico = tDiagnostico.getText();
        return historiaModel;
    }

    private void showInicioUsuario() {
        eMAApplication.navigationService.goToNewWindow(NavigationService.WindowType.INICIO_USUARIO, window);
    }

    @Override
    public void setValues() {
        PacienteModel pacienteModel = eMAApplication.databaseService.getPacienteModel(cedula);

        lbNombre.setText(pacienteModel.Nombre);
        lbCedula.setText(cedula);
        lbEdad.setText(pacienteModel.Edad);
    }
}