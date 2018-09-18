package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SteerableBody extends SteerableAdapter<Vector2> {
    private GameObject owner;
    public Body body; //TODO make private after changing update() method in GameObject

    public SteerableBody(BodyDef.BodyType bodyType, float bodyWidth, float bodyHeight, GameObject owner) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        float bodyX = owner.getPosition().x + owner.getWidth()/2;
        float bodyY = owner.getPosition().y + bodyHeight/2;
        bodyDef.position.set(bodyX, bodyY);
        body = GameWorld.world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyWidth/2, bodyHeight/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);
        polygonShape.dispose();

        body.setUserData(bodyHeight);

        this.owner = owner;
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
        applySteering(Gdx.app.getGraphics().getDeltaTime()); //TODO fix delta time usage
        owner.updatePosition();
    }

    private void applySteering (float time) {
        linearVelocity.mulAdd(steeringOutput.linear, time).limit(this.getMaxLinearSpeed());
        body.setLinearVelocity(linearVelocity);
    }

    /*
     ** Wrapping around box2d.Body
     */
    public void setLinearVelocity(Vector2 vector) {
        body.setLinearVelocity(vector);
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
}