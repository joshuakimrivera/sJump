package com.sillygames.sJump.serverEntities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sillygames.sJump.categories.ExplodingWeaponCategory;
import com.sillygames.sJump.helpers.Utils;
import com.sillygames.sJump.helpers.EntityUtils.ActorType;
import com.sillygames.sJump.managers.WorldBodyUtils;
import com.sillygames.sJump.managers.WorldRenderer;
import com.sillygames.sJump.managers.physics.Body.BodyType;
import com.sillygames.sJump.networking.messages.EntityState;

public class ServerBomb extends ServerEntity implements ExplodingWeaponCategory {

    public static final float RADIUS = 10f;
    public ServerEntity bomber;
    public float destroyTime;
    private float velocity;
    
    public ServerBomb(short id, float x, float y, WorldBodyUtils world) {
        super(id, x, y, world);
        actorType = ActorType.BOMB;
        body = world.addBox(RADIUS, RADIUS, position.x, position.y,
                BodyType.DynamicBody);
        body.setLinearVelocity(velocity, 0);
        body.setUserData(this);
        body.restitutionX = 0.5f;
        body.restitutionY = 0.5f;
        destroyTime = 1.5f;
        body.setGravityScale(0.75f);
        body.xDamping = 0.02f;
    }
    
    @Override
    public void update(float delta) {
        destroyTime -= delta;
        position.set(body.getPosition());
        
        if (Utils.wrapBody(position)) {
            body.setTransform(position, 0);
        }
        
        if (destroyTime < 0) {
            explode();
        }
    }
    
    @Override
    public void dispose() {
        world.destroyBody(body);
    }

    @Override
    public void updateState(EntityState state) {
        super.updateState(state);
        state.angle = MathUtils.atan2(body.getLinearVelocity().y,
                body.getLinearVelocity().x);
    }

    @Override
    public float getWidth() {
        return RADIUS;
    }

    @Override
    public void explode() {
        if (body.toDestroy) {
            return;
        }
        world.audio.explode();
        Vector2 position = body.getPosition();
        world.destroyEntities(this, 35, position);
        
        if (position.x > WorldRenderer.VIEWPORT_WIDTH - 20) {
            position.x -= WorldRenderer.VIEWPORT_WIDTH;
        } else if (position.x < 20) {
            position.x += WorldRenderer.VIEWPORT_WIDTH;
        }
        world.destroyEntities(this, 35, position);
          
        position = body.getPosition();

        if (position.y > WorldRenderer.VIEWPORT_HEIGHT - 20) {
            position.y -= WorldRenderer.VIEWPORT_HEIGHT;
        } else if (position.x < 20) {
            position.y += WorldRenderer.VIEWPORT_HEIGHT;
        }
        world.destroyEntities(this, 35, position);
        
        dispose();
    }

    @Override
    public ServerEntity getBomber() {
        return bomber;
    }

}
