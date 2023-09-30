package com.example.ema_java.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseConnection {
    public static void connect() {
        Connection conn = createDatabase();

        if (conn != null) {
            createTables(conn);
        }

    }

    private static Connection createDatabase() {
        Connection conn = null;

        try {
            String pathString = "c:/eMA";
            Path path = Paths.get(pathString);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String url = "jdbc:sqlite:" + path + "\\database.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    private static void createTables(Connection conn) {
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
                \t"FechaDeNacimiento"\tTEXT,
                \t"Cedula"\tTEXT,
                \t"Paciente_Id"\tINTEGER,
                \tPRIMARY KEY("Paciente_Id")
                );""";

        String sqlMedico = """
                CREATE TABLE IF NOT EXISTS "Medicos" (
                \t"Nombre"\tTEXT,
                \t"Cedula"\tTEXT,
                \t"Especialidad"\tTEXT,
                \t"Medico_Id"\tINTEGER,
                \tPRIMARY KEY("Medico_Id")
                );""";

        String sqlUsuario = """
                CREATE TABLE IF NOT EXISTS "Usuarios" (
                \t"Medico_Id"\tINTEGER NOT NULL,
                \t"Usuario"\tTEXT,
                \t"Contrasena"\tTEXT NOT NULL,
                \tPRIMARY KEY("Usuario")
                );""";

        try {
            Statement statement = conn.createStatement();

            statement.execute(sqlHistoria);
            statement.execute(sqlPaciente);
            statement.execute(sqlMedico);
            statement.execute(sqlUsuario);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
