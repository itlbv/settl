package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.SteerableBody;
import com.itlbv.settl.enums.GameObjectType;

public abstract class Mob extends GameObject {

    public Mob(float x, float y, Texture texture, GameObjectType type,
               float bodyWidth, float bodyHeight) {
        super(x, y, texture, type);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
    }

    public void createSteeringBehavior(float speed, SteeringBehavior<Vector2> steeringBehavior) {
        SteerableBody body = super.getBody(); //TODO make a class variable?
        if (body == null) {
            return; //TODO make it work smhw
        }
        body.initializeSteeringBehavior(speed, steeringBehavior);
    }

    public void update() {
        SteerableBody body = super.getBody(); //TODO make a class variable?
        body.update();
    }
}
