package com.itlbv.settl.mob.action.movement;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mob.Mob;

public class SteeringMovement {
    private final SteeringAcceleration<Vector2> steeringOutput;
    private final Seek<Vector2> steeringBehavior;
    private Mob owner;
    private float maxLinearSpeed;

    public SteeringMovement(Mob owner, float maxLinearSpeed) {
        this.owner = owner;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
        this.steeringBehavior = new Seek<>(owner);
        this.maxLinearSpeed = maxLinearSpeed;
    }

    public void init() {
        steeringBehavior.setTarget(owner.getTarget())
                .setEnabled(true);
    }

    public Vector2 getVelocity() {
        steeringBehavior.calculateSteering(steeringOutput);
        return steeringOutput.linear.limit(maxLinearSpeed);
    }

    public void disable() {
        steeringBehavior.setEnabled(false);
    }
}
