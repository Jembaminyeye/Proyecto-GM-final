package com.mygdx.game;

import com.badlogic.gdx.audio.Music;

public class PauseContext {
    private Pausable state;
    private Music rainMusic;

    public PauseContext(Music rainMusic) {
        this.rainMusic = rainMusic;
        this.state = new ResumeStrategy(rainMusic); // Estado inicial
    }

    public void setState(Pausable state) {
        this.state = state;
    }

    public Pausable getState() {
        return state;
    }

    public void pause() {
        state.pause();
        if (state instanceof ResumeStrategy) {
            setState(new PauseStrategy(rainMusic));
            
        }
    }

    public void resume() {
        state.resume();
        if (state instanceof PauseStrategy) {
            setState(new ResumeStrategy(rainMusic));
        }
    }
}
