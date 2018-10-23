package com.itlbv.settl.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameWorld;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.Mob;

public class Player extends Mob {
    private Vector2 UP, DOWN, LEFT, RIGHT;
    FrictionJoint joint;

    public Player(float x, float y) {
        super(MobObjectType.HUMAN_PEASANT, "", 1f, x, y);
        //super.setTexture(new TextureRegion(new Texture("black_dot.png")));
        UP = new Vector2(0, 0);
        DOWN = new Vector2(0, 0);
        LEFT = new Vector2(0, 0);
        RIGHT = new Vector2(0, 0);
        createFrictionJoint();
    }

    private void createFrictionJoint() {

        FrictionJointDef jointDef = new FrictionJointDef();
        jointDef.initialize(getBody(), Game.map.mapSensor, getBody().getPosition());
        jointDef.maxForce = 25;
        GameWorld.world.createJoint(jointDef);
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            UP.set(0, 3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            DOWN.set(0, -3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            LEFT.set(-3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            RIGHT.set(3, 0);
        }


        if (!Gdx.input.isKeyPressed(Input.Keys.T)) {
            UP.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.G)) {
            DOWN.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.F)) {
            LEFT.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.H)) {
            RIGHT.set(0, 0);
        }
        float x = UP.x + DOWN.x + LEFT.x + RIGHT.x;
        float y = UP.y + DOWN.y + LEFT.y + RIGHT.y;
        getBody().setLinearVelocity(new Vector2(x, y));
        updateRenderPosition();
    }
}
