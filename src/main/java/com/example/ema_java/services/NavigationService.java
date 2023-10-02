package com.example.ema_java.services;

import com.example.ema_java.controllers.*;
import com.example.ema_java.eMAApplication;
import com.example.ema_java.utils.WindowData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class NavigationService {
    public enum WindowType {
        INICIO_SESION,
        INICIO_USUARIO,
        REGISTRAR_USUARIO,
        REGISTRAR_PACIENTE,
        REGISTRAR_HISTORIA,
        LEER_HISTORIA
    }

    private final Dictionary<WindowType, WindowData> windowData = new Hashtable<>();

    public NavigationService() {
        windowData.put(WindowType.INICIO_SESION, new WindowData("inicio-sesion-view.fxml", "Iniciar sesión", 240, 320));
        windowData.put(WindowType.INICIO_USUARIO, new WindowData("inicio-usuario-view.fxml", "Inicio", 420, 540));
        windowData.put(WindowType.REGISTRAR_USUARIO, new WindowData("registrar-usuario-view.fxml", "Registrar usuario", 640, 320));
        windowData.put(WindowType.REGISTRAR_PACIENTE, new WindowData("registrar-paciente-view.fxml", "Registrar paciente", 440, 320));
        windowData.put(WindowType.REGISTRAR_HISTORIA, new WindowData("registrar-historia-view.fxml", "Registrar historia", 640, 520));
        windowData.put(WindowType.LEER_HISTORIA, new WindowData("leer-historia-view.fxml", "Leer historia", 300, 300));
    }

    public void startApplication() {
        Stage inicio = createScene(WindowType.INICIO_SESION, null);
        eMAApplication.windows.put(WindowType.INICIO_SESION, inicio);
    }

    public void goToNewWindow(WindowType newWindow, WindowType currentWindow, Object... params) {
        Stage newStage = createScene(newWindow, currentWindow, params);
        showNewWindow(currentWindow, newWindow, newStage);
    }

    private Stage createScene(WindowType newWindow, WindowType currentWindow, Object... params) {
        ControllerBase controller = switch (newWindow) {
            case INICIO_SESION -> new InicioSesionController(newWindow);
            case INICIO_USUARIO -> new InicioUsuarioController(newWindow);
            case REGISTRAR_USUARIO -> new RegistroUsuarioController(newWindow);
            case REGISTRAR_PACIENTE -> new RegistroPacienteController(newWindow);
            case REGISTRAR_HISTORIA -> new RegistroHistoriaController(newWindow, params[0].toString());
            case LEER_HISTORIA -> new LeerHistoriaController(newWindow, params[0].toString(), params[1].toString());
        };

        Stage newStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(eMAApplication.class.getResource(windowData.get(newWindow).View_Name));

            fxmlLoader.setController(controller);

            Scene scene = new Scene(fxmlLoader.load(), windowData.get(newWindow).H_Size, windowData.get(newWindow).V_Size);

            controller.setValues();

            if (currentWindow != null && newWindow != WindowType.INICIO_USUARIO && newWindow != WindowType.INICIO_SESION) {
                newStage.setOnCloseRequest((windowEvent) -> goToNewWindow(currentWindow, newWindow));
            }

            newStage.setTitle(windowData.get(newWindow).Title);
            newStage.setScene(scene);
            newStage.show();

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error inesperado al crear la nueva escena.\n" + e.getMessage());
        }
        return newStage;
    }

    private void showNewWindow(WindowType currentWindow, WindowType newWindow, Stage newStage) {
        eMAApplication.windows.put(newWindow, newStage);
        eMAApplication.windows.get(currentWindow).close();
        eMAApplication.windows.remove(currentWindow);
    }
}
