package com.sillygames.sJump.clientEntities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sillygames.sJump.managers.WorldRenderer;
import com.sillygames.sJump.pool.AssetLoader;
import com.sillygames.sJump.serverEntities.ServerBlob;

public class ClientBlob extends ClientEntity {
    
    private Sprite sprite;
    boolean markForDispose;
    private Animation walk;
    private float walkDuration;
    private boolean previousXFlip;
    private float deadTimer;
    
    public ClientBlob(short id, float x, float y, WorldRenderer renderer) {
        super(id, x, y, renderer);
        markForDispose = false;
        Texture texture = AssetLoader.instance.getTexture("sprites/blob.png");
        sprite = new Sprite(texture);
        walk = new Animation(0.25f, TextureRegion.split(texture,
                texture.getWidth()/2, texture.getHeight())[0]);
        walk.setPlayMode(Animation.PlayMode.LOOP);
        sprite.setSize(ServerBlob.WIDTH + 5f, ServerBlob.HEIGHT);
        deadTimer = 2f;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        if ((extra & 0x1) == 1) {
            walkDuration += delta;
            if (vX < -5f && vY == 0) {
                sprite.setRegion(walk.getKeyFrame(walkDuration));
                previousXFlip = false;
            } else if (vX > 5f && vY == 0){
                sprite.setRegion(walk.getKeyFrame(walkDuration));
                sprite.flip(true, false);
                previousXFlip = true;
            } else {
                sprite.setRegion(walk.getKeyFrame(0));
                sprite.flip(previousXFlip, false);
            }
            
            if (destroy) {
                deadTimer -= delta;
                if (deadTimer <=0) {
                    remove = true;
                }
                sprite.setRegion(AssetLoader.instance.getTexture("sprites/explosion.png"));
                sprite.flip(false, false);
            }
        } else {
            sprite.setRegion(AssetLoader.instance.getTexture("sprites/green_loader.png"));
        }
        float x = position.x - sprite.getWidth() / 2 + 1f;
        float y = position.y - sprite.getHeight() / 2 + ServerBlob.YOFFSET;
        drawAll(sprite, batch, x, y);
    }
    
    @Override
    public void dispose() {
    }

}
