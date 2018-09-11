package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.SteerableBody;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.enumsStateMachine.MobState;

public abstract class Mob extends GameObject {
    private MobObjectType type;

    public Mob(float x, float y, TextureRegion texture, MobObjectType type,
               float bodyWidth, float bodyHeight) {
        super(x, y, texture, type);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
        this.type = type;
    }

    public void createSteeringBehavior(float speed, SteeringBehavior<Vector2> steeringBehavior) {
        SteerableBody body = super.getBody(); //TODO make a class variable?
        if (body == null) {
            return; //TODO make it work smhw
        }
        body.initializeSteeringBehavior(speed, steeringBehavior);
    }

    public void updateSteering() {
        SteerableBody body = super.getBody(); //TODO make a class variable?
        body.updateSteering();
    }

    /*
    **State machine implementation
     */
    public StateMachine<Mob, MobState> stateMachine = new DefaultStateMachine<Mob, MobState>(this, MobState.IDLE);

    public void updateState() {
        stateMachine.update();
    }

    public void setTarget(GameObject target) { //TODO delete when state machine is ready
        Arrive<Vector2> behavior = new Arrive<Vector2>(this.getBody(), target.getBody());
        behavior.setArrivalTolerance(50f);
        behavior.setDecelerationRadius(10f);
        createSteeringBehavior(50f, behavior);
    }

    /*
    **Getters & setters
     */
    public MobObjectType getType() {
        return this.type;
    }
}
