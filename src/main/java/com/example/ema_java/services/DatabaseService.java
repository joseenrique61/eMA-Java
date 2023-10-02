package com.example.ema_java.services;

import com.example.ema_java.models.MedicoModel;
import com.example.ema_java.models.PacienteModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseService {
    public Connection connection;
    
    public DatabaseService() {
        connection = connect();
    }
    
    public boolean checkUserIsCorrect(String usuario, String contrasena) {
        String sqlReader = "SELECT Usuario " +
                        "FROM Usuarios " +
                        "WHERE Usuario = '" + usuario + "' AND Contrasena = '" + contrasena + "'" +
                        "LIMIT 1";
        return hasRows(connection, sqlReader);
    }

    public boolean checkUserAvailability(String usuario) {
        String sqlReader = "SELECT Usuario " +
                           "FROM Usuarios " +
                           "WHERE Usuario = '" + usuario + "'" +
                           "LIMIT 1";
        return hasRows(connection, sqlReader);
    }

    public boolean checkPacienteAvailability(String cedula) {
        String sqlReader = "SELECT Nombre " +
                           "FROM Pacientes " +
                           "WHERE Cedula = '" + cedula + "'" +
                           "LIMIT 1";
        return hasRows(connection, sqlReader);
    }

    public void createUser(String usuario, String contrasena, MedicoModel medicoModel) {
        String sqlMedico = "INSERT INTO Medicos ('Nombre', 'Cedula', 'Especialidad')\n" +
                     "VALUES ('" + medicoModel.Nombre + "', '" + medicoModel.Cedula + "', '" + medicoModel.Especialidad + "')";
        String sqlUsuario = "INSERT INTO Usuarios ('Usuario', 'Contrasena', 'Medico_Id')\n" +
                            "VALUES ('" + usuario + "', '" + contrasena + "', (SELECT Medico_Id FROM Medicos WHERE Cedula = '" + medicoModel.Cedula + "'))";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlMedico);
            statement.execute(sqlUsuario);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createPaciente(PacienteModel pacienteModel) {
        String sqlPaciente = "INSERT INTO Pacientes ('Nombre', 'Cedula', 'Edad')\n" +
                           "VALUES ('" + pacienteModel.Nombre + "', '" + pacienteModel.Cedula + "', '" + pacienteModel.Edad + "')";

        try {
            Statement statement = connection.createStatement();
            statement.execute(sqlPaciente);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public MedicoModel getMedicoModel(String usuario) {
        MedicoModel medicoModel = new MedicoModel();

        String sqlMedico = "SELECT Nombre, Cedula, Especialidad " +
                           "FROM Medicos " +
                           "WHERE Medico_Id = (SELECT Medico_Id FROM Usuarios WHERE Usuario = '" + usuario + "')" +
                           "LIMIT 1";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlMedico);

            while (resultSet.next()) {
                medicoModel.Nombre = resultSet.getString("Nombre");
                medicoModel.Cedula = resultSet.getString("Cedula");
                medicoModel.Especialidad = resultSet.getString("Especialidad");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return medicoModel;
    }

    public PacienteModel getPacienteModel(String cedula) {
        PacienteModel pacienteModel = new PacienteModel();

        String sqlPaciente = "SELECT Nombre, Cedula, Edad " +
                           "FROM Pacientes " +
                           "WHERE Cedula = '" + cedula + "'" +
                           "LIMIT 1";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPaciente);

            while (resultSet.next()) {
                pacienteModel.Nombre = resultSet.getString("Nombre");
                pacienteModel.Cedula = resultSet.getString("Cedula");
                pacienteModel.Edad = resultSet.getString("Edad");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return pacienteModel;
    }

    public ObservableList<String> getPacientesList() {
        ObservableList<String> list = FXCollections.observableArrayList();

        String sqlPacientes = "SELECT Nombre, Cedula FROM Pacientes";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPacientes);

            while (resultSet.next()) {
                list.add(resultSet.getString("Nombre") + " - " + resultSet.getString("Cedula"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public ObservableList<String> getHistoriasList(String usuario, String cedula) {
        ObservableList<String> list = FXCollections.observableArrayList();

        String sqlHistorias = "SELECT Fecha " +
                           "FROM Historias " +
                           "WHERE Paciente_Id = (SELECT Paciente_Id FROM Historias WHERE Medico_Id = (" +
                           "SELECT Medico_Id From Usuarios WHERE Usuario = '" + usuario + "')" +
                           "AND Medico_Id = (SELECT Medico_Id From Usuarios WHERE Usuario = '" + usuario + "'))";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlHistorias);

            while (resultSet.next()) {
                list.add(resultSet.getString("Fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    private boolean hasRows(Connection connection, String sqlReader) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlReader);

            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private Connection connect() {
        Connection connection = createDatabase();

        if (connection != null) {
            createTables(connection);
        }

        return connection;
    }

    private Connection createDatabase() {
        Connection connection = null;

        try {
            String pathString = "c:/eMA";
            Path path = Paths.get(pathString);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String url = "jdbc:sqlite:" + path + "\\database.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    private void createTables(Connection connection) {
        String sqlHistoria = """
                CREATE TABLE IF NOT EXISTS "Historias" (
                \t"Paciente_Id"\tINTEGER NOT NULL,
                \t"Fecha"\tTEXT,
                \t"MotCons"\tTEXT,
                \t"EnfAct"\tTEXT,
                \t"Antecedentes"\tTEXT,
                \t"Alergias"\tTEXT,
                \t"ExFis"\tTEXT,
                \t"Recipe"\tTEXT,
                \t"Indicaciones"\tTEXT,
                \t"Diagnostico"\tTEXT,
                \t"Medico_Id"\tINTEGER NOT NULL,
                \tPRIMARY KEY("Fecha")
                );""";

        String sqlPaciente = """
                CREATE TABLE IF NOT EXISTS "Pacientes" (
                \t"Nombre"\tTEXT,
                \t"Edad"\tTEXT,
                \t"Cedula"\tTEXT\tUNIQUE,
                \t"Paciente_Id"\tINTEGER,
                \tPRIMARY KEY("Paciente_Id" AUTOINCREMENT)
                );""";

        String sqlMedico = """
                CREATE TABLE IF NOT EXISTS "Medicos" (
                \t"Nombre"\tTEXT,
                \t"Cedula"\tTEXT,
                \t"Especialidad"\tTEXT,
                \t"Medico_Id"\tINTEGER,
                \tPRIMARY KEY("Medico_Id" AUTOINCREMENT)
                );""";

        String sqlUsuario = """
                CREATE TABLE IF NOT EXISTS "Usuarios" (
                \t"Medico_Id"\tINTEGER NOT NULL,
                \t"Usuario"\tTEXT,
                \t"Contrasena"\tTEXT NOT NULL,
                \tPRIMARY KEY("Usuario")
                );""";

        try {
            Statement statement = connection.createStatement();

            statement.execute(sqlHistoria);
            statement.execute(sqlPaciente);
            statement.execute(sqlMedico);
            statement.execute(sqlUsuario);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
