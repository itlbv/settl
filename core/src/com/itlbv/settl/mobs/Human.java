package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public abstract class Human extends Mob {
    private static final float WIDTH = 2.5f;
    private static final float HEIGHT = 2.5f;
    private static final float SPEED = 4f;

    // size of collision box
    private static final float BODY_WIDTH = .6f;
    private static final float BODY_HEIGHT = .4f;

    public Human(float x, float y, MobObjectType type, TextureRegion texture) {
        super(x, y, type, texture, WIDTH, HEIGHT, BODY_WIDTH, BODY_HEIGHT, SPEED);
    }
}
