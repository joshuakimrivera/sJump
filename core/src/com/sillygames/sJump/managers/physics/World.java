package com.sillygames.sJump.managers.physics;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.sillygames.sJump.managers.WorldManager;
import com.sillygames.sJump.managers.physics.Body.BodyType;
import com.sillygames.sJump.serverEntities.ServerPlayer;

public class World {
    
    public final Vector2 gravity;
    public final ArrayList<Body> bodies;

    public World(Vector2 gravity) {
        this.gravity = gravity;
        bodies = new ArrayList<Body>();
    }

    public void step(float delta, int iterations, WorldManager worldManager) {
        int i = 0;
        while (i < bodies.size()) {
            Body body = bodies.get(i);
            if (body.toDestroy) {
                worldManager.destroyBody(body);
                bodies.remove(i);
            }
            i++;
        }
        for (i = 0; i< iterations; i++) {
            for (Body body: bodies) {
                if (body.getUserData() instanceof ServerPlayer)
                    body.update(delta/(float)iterations);
            }
            
            for (Body body: bodies) {
                if (body.bodyType == BodyType.DynamicBody && 
                        !(body.getUserData() instanceof ServerPlayer))
                    body.update(delta/(float)iterations);
            }
        }
    }
    
    public void addBody(Body body) {
        bodies.add(body);
    }

}
