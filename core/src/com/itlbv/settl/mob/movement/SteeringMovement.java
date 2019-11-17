package com.itlbv.settl.mob.movement;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mob.Mob;

class SteeringMovement {
    private final SteeringAcceleration<Vector2> steeringOutput;
    private final Seek<Vector2> steeringBehavior;
    private Mob owner;
    private float maxLinearSpeed;

    SteeringMovement(Mob owner, float maxLinearSpeed) {
        this.owner = owner;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
        this.steeringBehavior = new Seek<>(owner);
        this.maxLinearSpeed = maxLinearSpeed;
    }

    void init() {
        steeringBehavior.setTarget(owner.getTarget())
                .setEnabled(true);
    }

    Vector2 getVelocity() {
        steeringBehavior.calculateSteering(steeringOutput);
        return steeringOutput.linear.limit(maxLinearSpeed);
    }

    void disable() {
        steeringBehavior.setEnabled(false);
    }
}
