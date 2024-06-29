package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GameAssetFactory {

    public Texture createTexture(String filePath) {
        return new Texture(Gdx.files.internal(filePath));
    }

    public Sound createSound(String filePath) {
        return Gdx.audio.newSound(Gdx.files.internal(filePath));
    }

    public Music createMusic(String filePath) {
        return Gdx.audio.newMusic(Gdx.files.internal(filePath));
    }
}
