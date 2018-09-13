package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public class Human extends Mob {
    private static final MobObjectType MOB_TYPE = MobObjectType.HUMAN;
    private static final float WIDTH = 2;
    private static final float HEIGHT = 2;
    private static final float SPEED = 5f;

    // size of collision box
    private static final float BODY_WIDTH = .6f;
    private static final float BODY_HEIGHT = .4f;

    public Human(float x, float y, TextureRegion texture) {
        super(x, y, MOB_TYPE, texture, WIDTH, HEIGHT, BODY_WIDTH, BODY_HEIGHT, SPEED);
    }
}
