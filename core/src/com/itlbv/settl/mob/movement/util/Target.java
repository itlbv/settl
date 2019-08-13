package com.itlbv.settl.mob.movement.util;

import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.math.Vector2;

public abstract class Target extends SteerableAdapter<Vector2> {
    /**
     * Override from SteerableAdapter.
     * maxLinearAcceleration shows how fast the object will gain speed.
     * Value is set to 100 which allows instant change of speed.
     */
    @Override
    public float getMaxLinearAcceleration() {
        return 100;
    }
}
