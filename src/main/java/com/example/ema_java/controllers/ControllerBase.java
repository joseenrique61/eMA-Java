package com.example.ema_java.controllers;

import com.example.ema_java.services.NavigationService;

public class ControllerBase {
    protected NavigationService.WindowType window;

    public ControllerBase(NavigationService.WindowType window) {
        this.window = window;
    }

    public void setValues() {

    }
}
