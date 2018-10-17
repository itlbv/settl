package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mobs.Mob;

public class SteeringMovement {
    private final SteeringAcceleration<Vector2> steeringOutput;
    public final Seek<Vector2> steeringBehavior;
    private Mob owner;
    private float maxLinearSpeed;

    public SteeringMovement(Mob owner, float maxLinearSpeed) {
        this.owner = owner;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
        this.steeringBehavior = new Seek<>(owner);
        this.maxLinearSpeed = maxLinearSpeed;
    }

    public void init() {
        steeringBehavior.setTarget(getTarget());
        steeringBehavior.setEnabled(true);
    }

    /*
    TODO steering movement updates when the path is empty but target isn't reached and steering behavior isn't set
     */
    public Vector2 calculateAndGet() {
        if (steeringBehavior.getTarget() == null) {
            return new Vector2(0,0);
        }
        if (!steeringBehavior.isEnabled()) {
            return new Vector2(0,0);
        }
        steeringBehavior.calculateSteering(steeringOutput);
        //linearVelocity.mulAdd(steeringOutput.linear, Game.DELTA_TIME).limit(maxLinearSpeed);
        return steeringOutput.linear.limit(maxLinearSpeed);
    }

    private Mob getTarget() {
        return (Mob) owner.getTarget();
    }
}
