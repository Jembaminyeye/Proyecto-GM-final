package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Music gameOverMusic;
    private Texture textureFondo;

    public GameOverScreen(final GameLluviaMenu game) {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();
        this.font = game.getFont();
        

        // Utilizamos la fábrica para crear texturas y sonidos
        GameAssetFactory assetFactory = new GameAssetFactory();

        this.gameOverMusic = assetFactory.createMusic("MenuFinal.mp3");
        this.textureFondo = assetFactory.createTexture("joker.png"); // Asegúrate de que el archivo de fondo esté disponible
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
        gameOverMusic.setLooping(false);
        gameOverMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(textureFondo, 0, 0, camera.viewportWidth, camera.viewportHeight); // Dibujar el fondo
        
        font.draw(batch, "Toca en cualquier lado para reiniciar.", 20, camera.viewportHeight / 2 - 50);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        gameOverMusic.stop();
    }

    @Override
    public void dispose() {
        gameOverMusic.dispose();
        textureFondo.dispose();
    }
}
