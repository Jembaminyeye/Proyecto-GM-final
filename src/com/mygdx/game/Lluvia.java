package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private long lastBonusDropTime;
    private Texture moneda;
    private Texture bomba;
    private Texture bonusItem;
    private Sound dropSound;
    private Sound bonusSound;
    private Music rainMusic;
    private Music bonusMusic;
    private boolean bonusActive;
    private long bonusEndTime;
    private float dropSpeed;

    public Lluvia(Texture moneda, Texture bomba, Texture bonusItem, Sound dropSound, Sound bonusSound, Music rainMusic, Music bonusMusic) {
        this.rainMusic = rainMusic;
        this.dropSound = dropSound;
        this.bonusSound = bonusSound;
        this.moneda = moneda;
        this.bomba = bomba;
        this.bonusItem = bonusItem;
        this.bonusMusic = bonusMusic;
        this.bonusActive = false;
        this.dropSpeed = 200; 
    }

    private void crearGotaDeLluviaConBono() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);
        rainDropsType.add(3); 
        lastBonusDropTime = TimeUtils.nanoTime(); 
    }

    public void crear() {
        rainDropsPos = new Array<>();
        rainDropsType = new Array<>();
        for (int i = 0; i < 4; i++) { 
            crearGotaDeLluvia();
        }
        rainMusic.setLooping(true);
        rainMusic.play();
        lastBonusDropTime = TimeUtils.nanoTime();
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
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                i--; 
            }
            if (raindrop.overlaps(bolsa.getArea())) {
                if (rainDropsType.get(i) == 1) {
                    if (bonusActive) {
                        bolsa.sumarPuntos(200); 
                        incrementarVelocidad();
                    } else {
                        bolsa.sumarPuntos(100);
                        incrementarVelocidad();
                    }
                    dropSound.play();
                } else if (rainDropsType.get(i) == 2) {
                    bolsa.dañar();
                    if (bolsa.getVidas() <= 0) return false;
                } else if (rainDropsType.get(i) == 3) {
                    activarBonus();
                }
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                i--; 
            }
        }
        return true;
    }
    
    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);
        if (bonusActive) {
            rainDropsType.add(1); 
        } else {
            int type = MathUtils.random(1, 9);
            if (type < 5)
                rainDropsType.add(1); 
            else
                rainDropsType.add(2);
        }
        lastDropTime = TimeUtils.nanoTime();
    }

    private void activarBonus() {
        bonusActive = true;
        bonusEndTime = TimeUtils.nanoTime() + 10_000_000_000L; 
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
        for (int i = 0; i < rainDropsPos.size; i++) {
            Texture texture;
            switch (rainDropsType.get(i)) {
                case 1:
                    texture = moneda;
                    break;
                case 2:
                    texture = bomba;
                    break;
                case 3:
                    texture = bonusItem;
                    break;
                default:
                    texture = moneda;
            }
            batch.draw(texture, rainDropsPos.get(i).x, rainDropsPos.get(i).y);
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
        bonusItem.dispose();
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
