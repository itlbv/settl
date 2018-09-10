package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.enums.MobType;

public class Human extends Mob {
    private static final MobType MOB_TYPE = MobType.HUMAN;
    private static final float SPEED = 50f;

    // size of collision box
    private static final float BODY_WIDTH = 6f;
    private static final float BODY_HEIGHT = 4f;


    public Human(float x, float y, Texture texture) {
        super(x, y, texture, MOB_TYPE, BODY_WIDTH, BODY_HEIGHT);
    }

    public void setTarget(GameObject target) {
        Arrive<Vector2> behavior = new Arrive<Vector2>(this.getBody(), target.getBody());
        behavior.setArrivalTolerance(50f);
        behavior.setDecelerationRadius(10f);
        super.createSteeringBehavior(SPEED, behavior);
    }
}
