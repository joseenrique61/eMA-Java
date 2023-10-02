package com.example.ema_java.controllers;

import com.example.ema_java.eMAApplication;
import com.example.ema_java.models.HistoriaModel;
import com.example.ema_java.models.PacienteModel;
import com.example.ema_java.services.NavigationService;
import com.example.ema_java.utils.TextProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LeerHistoriaController extends ControllerBase {
    private String cedula;
    private String fecha;

    @FXML
    private Label lbResumen;

    public LeerHistoriaController(NavigationService.WindowType window, String cedula, String fecha) {
        super(window);
        this.cedula = cedula;
        this.fecha = fecha;
    }

    @Override
    public void setValues() {
        HistoriaModel historiaModel = eMAApplication.databaseService.getHistoriaModel(eMAApplication.usuario, cedula, fecha);
        PacienteModel pacienteModel = eMAApplication.databaseService.getPacienteModel(cedula);

        String historia = TextProcessor.getHistoriaText(historiaModel, pacienteModel);
        lbResumen.setText(historia);
    }
}