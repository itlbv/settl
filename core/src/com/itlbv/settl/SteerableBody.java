package com.itlbv.settl;

import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class SteerableBody extends SteerableAdapter<Vector2> {
    private GameObject owner;
    private float bodyWidth, bodyHeight;
    private Body body;

    public SteerableBody(BodyType bodyType, float bodyWidth, float bodyHeight, GameObject owner) {
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        this.owner = owner;
        body = BodyFactory.createBody(bodyWidth, bodyHeight, bodyType, owner, false);
    }

    public void updateOwnersPosition() {
        float x = getPosition().x - owner.getWidth()/2;
        float y = getPosition().y - bodyHeight/2;
        owner.getPosition().set(x, y);
    }

    /*
     ** Wrapping around box2d.Body
     */
    public void setLinearVelocity(Vector2 vector) {
        body.setLinearVelocity(vector);
        owner.getSensor().setLinearVelocity(vector);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getMaxLinearAcceleration() {
        return 100; //TODO magical number! move to MovingManager?
    }

    public BodyType getType() {
        return body.getType();
    }
}