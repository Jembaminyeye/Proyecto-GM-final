package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<FallingObject> rainDropsPos;
    private long lastDropTime;
    private long lastBonusDropTime;
    private Texture moneda;
    private Texture bomba;
    private Texture bonusItemTexture;
    private Sound dropSound;
    private Sound bonusSound;
    private Music rainMusic;
    private Music bonusMusic;
    private boolean bonusActive;
    private long bonusEndTime;
    private float dropSpeed;

    public Lluvia(Texture moneda, Texture bomba, Texture bonusItemTexture, Sound dropSound, Sound bonusSound, Music rainMusic, Music bonusMusic) {
        this.rainMusic = rainMusic;
        this.dropSound = dropSound;
        this.bonusSound = bonusSound;
        this.moneda = moneda;
        this.bomba = bomba;
        this.bonusItemTexture = bonusItemTexture;
        this.bonusMusic = bonusMusic;
        this.bonusActive = false;
        this.dropSpeed = 200;
    }

    public void crear() {
        rainDropsPos = new Array<>();
        rainMusic.setLooping(true);
        rainMusic.play();
        lastBonusDropTime = TimeUtils.nanoTime();
        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Bolsa bolsa) {
        if (bonusActive && TimeUtils.nanoTime() > bonusEndTime) {
            desactivarBonus();
        }

        if (TimeUtils.nanoTime() - lastDropTime > 300000000) {
            crearGotaDeLluvia();
        }

        if (TimeUtils.nanoTime() - lastBonusDropTime > 15_000_000_000L) {
            crearGotaDeLluviaConBono();
        }

        for (int i = 0; i < rainDropsPos.size; i++) {
            FallingObject raindrop = rainDropsPos.get(i);
            raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
            if (raindrop.y + raindrop.height < 0) {
                rainDropsPos.removeIndex(i);
                i--;
            }
            if (raindrop.colisionaCon(bolsa.getArea())) {
                if (raindrop instanceof Moneda) {
                    if (bonusActive) {
                        bolsa.sumarPuntos(200);
                        incrementarVelocidad();
                    } else {
                        bolsa.sumarPuntos(100);
                        incrementarVelocidad();
                    }
                    dropSound.play();
                } else if (raindrop instanceof Bomba) {
                    bolsa.dañar();
                    if (bolsa.getVidas() <= 0) return false;
                } else if (raindrop.texture == bonusItemTexture) { // Usar el campo texture directamente
                    activarBonus();
                }
                rainDropsPos.removeIndex(i);
                i--;
            }
        }
        return true;
    }

    private void crearGotaDeLluvia() {
        FallingObject raindrop;
        if (bonusActive) {
            raindrop = new Moneda(moneda, MathUtils.random(0, 800 - 64), 480, 48, 64, 100); // Anchura de 32 para hacerla más delgada
        } else {
            int type = MathUtils.random(1, 9);
            if (type < 5)
                raindrop = new Moneda(moneda, MathUtils.random(0, 800 - 64), 480, 48, 64, 100); // Anchura de 32 para hacerla más delgada
            else
                raindrop = new Bomba(bomba, MathUtils.random(0, 800 - 64), 480, 64, 64, 100);
        }
        rainDropsPos.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }


    private void crearGotaDeLluviaConBono() {
        FallingObject bonusObject = new FallingObject(bonusItemTexture, MathUtils.random(0, 800 - 64), 480, 64, 64) {
            @Override
            public void actualizar() {
                this.y -= 300 * Gdx.graphics.getDeltaTime();
            }
        };
        rainDropsPos.add(bonusObject);
        lastBonusDropTime = TimeUtils.nanoTime();
    }

    private void activarBonus() {
        bonusActive = true;
        bonusEndTime = TimeUtils.nanoTime() + 10_000_000_000L; // Duración de 10 segundos
        rainMusic.pause();
        bonusMusic.play();
        bonusSound.play();
    }

    private void desactivarBonus() {
        bonusActive = false;
        bonusMusic.stop();
        rainMusic.play();
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (FallingObject raindrop : rainDropsPos) {
            raindrop.dibujar(batch);
        }
    }

    public void continuar() {
        rainMusic.play();
    }

    public void pausar() {
        rainMusic.pause();
    }

    public void destruir() {
        rainMusic.dispose();
        dropSound.dispose();
        bonusSound.dispose();
        moneda.dispose();
        bomba.dispose();
        bonusItemTexture.dispose();
        bonusMusic.dispose();
    }

    private void incrementarVelocidad() {
        dropSpeed += 2;
    }

    public void detenerMusica() {
        rainMusic.stop();
        bonusMusic.stop();
    }
}
