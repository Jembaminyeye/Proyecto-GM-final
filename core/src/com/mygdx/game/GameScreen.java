package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Bolsa bolsa;
    private Lluvia lluvia;
    private Texture textureFondo;

    public GameScreen(final GameLluviaMenu game) {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();
        this.font = game.getFont();

        // Utilizamos la fábrica para crear texturas y sonidos
        GameAssetFactory assetFactory = new GameAssetFactory();

        Sound hurtSound = assetFactory.createSound("hurt.mp3");
        bolsa = new Bolsa(assetFactory.createTexture("bucket.png"), hurtSound);

        textureFondo = assetFactory.createTexture("banco.png");

        Texture moneda = assetFactory.createTexture("drop.png");
        Texture bomba = assetFactory.createTexture("dropBad.png");
        Texture bonusItem = assetFactory.createTexture("bonus.png");

        Sound dropSound = assetFactory.createSound("drop.mp3");
        Sound bonusSound = assetFactory.createSound("x2.mp3");

        Music rainMusic = assetFactory.createMusic("rain.mp3");
        Music bonusMusic = assetFactory.createMusic("x2.mp3");

        lluvia = new Lluvia(moneda, bomba, bonusItem, dropSound, bonusSound, rainMusic, bonusMusic);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 560);
        batch = new SpriteBatch();
        bolsa.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(textureFondo, 0, 0, camera.viewportWidth, camera.viewportHeight);
        font.draw(batch, "Monedas totales: " + bolsa.getPuntos(), 5, camera.viewportHeight - 25);
        font.draw(batch, "Vidas : " + bolsa.getVidas(), camera.viewportWidth - 150, camera.viewportHeight - 25);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, camera.viewportHeight - 25);

        if (!bolsa.estaHerido()) {
            bolsa.actualizar();
            if (!lluvia.actualizarMovimiento(bolsa)) {
                if (game.getHigherScore() < bolsa.getPuntos()) {
                    game.setHigherScore(bolsa.getPuntos());
                }
                lluvia.detenerMusica(); 
                // Cambiar a la pantalla de Game Over sin llamar a dispose() inmediatamente
                game.setScreen(new GameOverScreen(game));
                // No llamamos a dispose() aquí
                return; // Salir del método render para evitar llamadas adicionales
            }
        }

        bolsa.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        lluvia.pausar();
    }

    @Override
    public void resume() {
        lluvia.continuar();
    }

    @Override
    public void dispose() {
        bolsa.destruir();
        lluvia.destruir();
        batch.dispose();
        font.dispose();
    }
}
