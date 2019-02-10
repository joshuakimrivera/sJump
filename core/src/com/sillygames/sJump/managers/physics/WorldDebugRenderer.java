package com.sillygames.sJump.managers.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;

public class WorldDebugRenderer {
    
    protected ShapeRenderer renderer;
    protected World world;
    
    public WorldDebugRenderer(World world) {
        renderer = new ShapeRenderer();
        this.world = world;
    }

    public void render(Matrix4 projectionMatrix) {
        renderer.begin(ShapeType.Line);
        renderer.setProjectionMatrix(projectionMatrix);
        
        renderer.setColor(Color.CYAN);
        for (int i = 0; i< world.bodies.size(); i++) {
            drawRectangle(world.bodies.get(i).rectangle);
        }
        
        renderer.end();
    }
    
    public void drawRectangle(Rectangle r) {
        renderer.line(r.x, r.y, r.x + r.width, r.y);
        renderer.line(r.x, r.y, r.x, r.y + r.height);
        renderer.line(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
        renderer.line(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
    }
}
