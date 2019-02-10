package com.sillygames.sJump.clientEntities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.sillygames.sJump.managers.WorldRenderer;
import com.sillygames.sJump.pool.AssetLoader;
import com.sillygames.sJump.serverEntities.ServerArrow;

public class ClientBullet extends ClientEntity {
    
    private Sprite sprite;
    boolean markForDispose;
    
    public ClientBullet(short id, float x, float y, WorldRenderer renderer) {
        super(id, x, y, renderer);
        markForDispose = false;
        sprite = new Sprite(AssetLoader.instance.getTexture("sprites/bullet.png"));
        sprite.setSize(ServerArrow.RADIUS * 4 , 
                ServerArrow.RADIUS * 1.5f);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(x  - sprite.getWidth() / 2,
                y  - sprite.getHeight() / 2);
        renderer.audioPlayer.shoot();
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        sprite.setRotation(angle * MathUtils.radiansToDegrees);

        float x = position.x - sprite.getWidth() / 2;
        float y = position.y - sprite.getHeight() / 2;
        drawAll(sprite, batch, x, y);
    }

    @Override
    public void dispose() {
    }

}
