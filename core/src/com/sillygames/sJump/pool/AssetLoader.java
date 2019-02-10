package com.sillygames.sJump.pool;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
    
    public static final AssetLoader instance = new AssetLoader();
    public AssetManager manager;
    
    public AssetLoader() {
        manager = new AssetManager();
    }
    
    public void loadAll() {
        manager.load("sprites/player.png", Texture.class);
        manager.load("sprites/blob.png", Texture.class);
        manager.load("sprites/blob_dead.png", Texture.class);
        manager.load("sprites/fly.png", Texture.class);
        manager.load("sprites/arrow.png", Texture.class);
        manager.load("sprites/bullet.png", Texture.class);
        manager.load("sprites/bomb.png", Texture.class);
        manager.load("sprites/frog.png", Texture.class);
        manager.load("sprites/explosion.png", Texture.class);
        manager.load("sprites/green_loader.png", Texture.class);
    }
    
    public Texture getTexture(String path) {
        manager.finishLoading();
        return manager.get(path, Texture.class);
    }
    
}
