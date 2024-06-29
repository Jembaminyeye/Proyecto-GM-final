package com.mygdx.game;

import com.badlogic.gdx.audio.Music;

public class PauseStrategy implements Pausable {
    private Music rainMusic;

    public PauseStrategy(Music rainMusic) {
        this.rainMusic = rainMusic;
    }

    @Override
    public void pause() {
        // Lógica para pausar el juego y la música
        System.out.println("Juego pausado");
        if (rainMusic != null && rainMusic.isPlaying()) {
            rainMusic.pause();
        }
    }

    @Override
    public void resume() {
        // No hace nada al intentar reanudar desde un estado ya pausado
    }
}
