package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLluviaMenu extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    private int higherScore;

    //private static GameLluviaMenu instance;

    private GameLluviaMenu() {
    }

    private static class SingletonHolder {
        // Instancia única del Singleton
        private static final GameLluviaMenu INSTANCE = new GameLluviaMenu();
    }

    // Método público para obtener la instancia única
    public static GameLluviaMenu getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHigherScore() {
        return higherScore;
    }

    public void setHigherScore(int higherScore) {
        this.higherScore = higherScore;
    }
}