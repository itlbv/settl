package com.itlbv.settl;

import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
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

    /*
    ** Steering behavior implementation
     */
    private Vector2 linearVelocity;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private SteeringBehavior<Vector2> steeringBehavior;
    private static final SteeringAcceleration<Vector2> steeringOutput = //TODO why final?
            new SteeringAcceleration<Vector2>(new Vector2());

    public void initializeSteeringBehavior(float speed, SteeringBehavior<Vector2> steeringBehavior) {
        linearVelocity = new Vector2();
        maxLinearSpeed = speed;
        maxLinearAcceleration = 10; //TODO magical number!
        this.steeringBehavior = steeringBehavior;
    }

    public void updateSteering() {
        if (steeringBehavior == null) {
            return; //TODO delete when steering behavior is ready
        }
        steeringBehavior.calculateSteering(steeringOutput);
        applySteering(Game.DELTA_TIME);
        updateOwnersPosition();
    }

    private void applySteering (float time) {
        linearVelocity.mulAdd(steeringOutput.linear, time).limit(this.getMaxLinearSpeed());
        body.setLinearVelocity(linearVelocity);
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
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public BodyType getType() {
        return body.getType();
    }
}