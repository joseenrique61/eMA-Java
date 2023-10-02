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
        windowData.put(WindowType.INICIO_SESION, new WindowData("inicio-sesion-view.fxml", "Iniciar sesión", 320, 240));
        windowData.put(WindowType.INICIO_USUARIO, new WindowData("inicio-usuario-view.fxml", "Inicio", 420, 540));
        windowData.put(WindowType.REGISTRAR_USUARIO, new WindowData("registrar-usuario-view.fxml", "Registrar usuario", 320, 640));
        windowData.put(WindowType.REGISTRAR_PACIENTE, new WindowData("registrar-paciente-view.fxml", "Registrar paciente", 320, 440));
    }

    public void startApplication() {
        Stage inicio = createScene(WindowType.INICIO_SESION);
        eMAApplication.windows.put(WindowType.INICIO_SESION, inicio);
    }

    public void goToNewWindow(WindowType newWindow, WindowType currentWindow, Object... params) {
        Stage newStage = createScene(newWindow, params);
        showNewWindow(currentWindow, newWindow, newStage);
    }

    private Stage createScene(WindowType newWindow, Object... params) {
        ControllerBase controller = switch (newWindow) {
            case INICIO_SESION -> new InicioSesionController(newWindow);
            case INICIO_USUARIO -> new InicioUsuarioController(newWindow, params[0].toString());
            case REGISTRAR_USUARIO -> new RegistroUsuarioController(newWindow);
            case REGISTRAR_PACIENTE -> new RegistroPacienteController(newWindow);
            default -> null;
        };

        Stage newStage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(eMAApplication.class.getResource(windowData.get(newWindow).View_Name));

            fxmlLoader.setController(controller);

            Scene scene = new Scene(fxmlLoader.load(), windowData.get(newWindow).V_Size, windowData.get(newWindow).H_Size);

            if (controller != null) {
                controller.setValues();
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
