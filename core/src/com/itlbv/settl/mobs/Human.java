package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public class Human extends Mob {
    private static final MobObjectType MOB_TYPE = MobObjectType.HUMAN;
    private static final float SPEED = 50f;

    // size of collision box
    private static final float BODY_WIDTH = 6f;
    private static final float BODY_HEIGHT = 4f;

    public Human(float x, float y, TextureRegion texture) {
        super(x, y, texture, MOB_TYPE, BODY_WIDTH, BODY_HEIGHT);
    }
}
