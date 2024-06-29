package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class GameOverScreenFactory implements ScreenFactory {
    @Override
    public Screen createScreen(GameLluviaMenu game) {
        return new GameOverScreen(game);
    }
}
