package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("GameLluviaMenu");
        config.setWindowedMode(800, 600); // Puedes ajustar estas dimensiones según tus necesidades
        new Lwjgl3Application(GameLluviaMenu.getInstance(), config);
    }
}
