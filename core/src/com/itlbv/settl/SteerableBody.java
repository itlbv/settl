package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class SteerableBody extends SteerableAdapter<Vector2> {
    private GameObject owner;
    public SteerableBody(BodyDef bodyDef, GameObject owner) {
        body = GameWorld.world.createBody(bodyDef); //TODO Gameworld class refactoring?;
        this.owner = owner;
    }

    /*
    ** Wrapping around box2d.Body
     */
    Body body;
    public void createFixture(FixtureDef fixtureDef) {
        body.createFixture(fixtureDef);
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
        maxLinearAcceleration = 100; //TODO magical number!
        this.steeringBehavior = steeringBehavior;
    }

    public void updateSteering() {
        if (steeringBehavior == null) {
            return; //TODO delete when steering behavior is ready
        }
        steeringBehavior.calculateSteering(steeringOutput);
        applySteering(Gdx.app.getGraphics().getDeltaTime()); //TODO fix delta time usage
        owner.updatePosition();
    }

    private void applySteering (float time) {
        linearVelocity.mulAdd(steeringOutput.linear, time).limit(this.getMaxLinearSpeed());
        body.setLinearVelocity(linearVelocity);
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
        return linearVelocity;
    }
}