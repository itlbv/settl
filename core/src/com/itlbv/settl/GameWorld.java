package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.itlbv.settl.util.CollisionHandler;

public class GameWorld {
    public static World world = new World(new Vector2(.0f, .0f), true);
    private static Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    static {
        world.setContactListener(new CollisionHandler());
    }

    public GameWorld(){
    }

    public static void tick(OrthographicCamera camera){
        debugRenderer.render(world, camera.combined);

        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        world.clearForces(); //TODO should it be here?
    }
}