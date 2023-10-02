package com.example.ema_java.utils;

import com.example.ema_java.models.HistoriaModel;
import com.example.ema_java.models.PacienteModel;

public class TextProcessor {
    public static String getHistoriaText(HistoriaModel historiaModel, PacienteModel pacienteModel) {
        return "Nombre: " + pacienteModel.Nombre + "\n" +
                "Cédula: " + pacienteModel.Cedula + "\n" +
                "Edad: " + pacienteModel.Edad + "\n" +
                "Fecha: " + historiaModel.Fecha + "\n" +
                "Antecedentes: " + historiaModel.Antecedentes + "\n" +
                "Alergias: " + historiaModel.Alergias + "\n" +
                "Enfermedad actual: " + historiaModel.EnfermedadActual + "\n" +
                "Motivo de consulta: " + historiaModel.MotivoDeConsulta + "\n" +
                "Examen físico: " + historiaModel.ExamenFisico + "\n" +
                "Recipe: " + historiaModel.Recipe + "\n" +
                "Indicaciones: " + historiaModel.Indicaciones + "\n" +
                "Diagnostico: " + historiaModel.Diagnostico;
    }
}
