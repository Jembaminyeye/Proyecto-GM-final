package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class FallingObject implements Actualizable {
    protected float x, y, width, height;
    protected Texture texture;

    public FallingObject(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void actualizar();

    public void dibujar(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public boolean colisionaCon(Rectangle rect) {
        Rectangle gotaRect = new Rectangle(x, y, width, height);
        return gotaRect.overlaps(rect);
    }
}
