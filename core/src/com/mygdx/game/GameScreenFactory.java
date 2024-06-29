package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class GameScreenFactory implements ScreenFactory {
    @Override
    public Screen createScreen(GameLluviaMenu game) {
        return new GameScreen(game);
    }
}
