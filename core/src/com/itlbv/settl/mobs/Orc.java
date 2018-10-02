package com.itlbv.settl.mobs;

import com.itlbv.settl.enumsObjectType.MobObjectType;

public abstract class Orc extends Mob{
    private static final float WIDTH = 2.5f;
    private static final float HEIGHT = 2.5f;
    private static final float SPEED = 5f;

    // size of collision box
    private static final float BODY_WIDTH = .8f;
    private static final float BODY_HEIGHT = .6f;

    public Orc(float x, float y, MobObjectType type, String bhvTree) {
        super(x, y, type, WIDTH, HEIGHT, BODY_WIDTH, BODY_HEIGHT, SPEED, bhvTree);
    }
}
