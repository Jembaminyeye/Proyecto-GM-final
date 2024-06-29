package com.mygdx.game;

import com.badlogic.gdx.audio.Music;

public class ResumeStrategy implements Pausable {
    private Music rainMusic;

    public ResumeStrategy(Music rainMusic) {
        this.rainMusic = rainMusic;
    }

    @Override
    public void pause() {
        // No hace nada al intentar pausar desde un estado ya reanudado
    }

    @Override
    public void resume() {
        // Lógica para reanudar el juego y la música
        System.out.println("Juego reanudado");
        if (rainMusic != null && !rainMusic.isPlaying()) {
            rainMusic.play();
        }
    }
}
