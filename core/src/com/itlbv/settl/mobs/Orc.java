package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public abstract class Orc extends Mob{
    private static final float WIDTH = 3f;
    private static final float HEIGHT = 3f;
    private static final float SPEED = 7f;

    // size of collision box
    private static final float BODY_WIDTH = .8f;
    private static final float BODY_HEIGHT = .6f;

    public Orc(float x, float y, MobObjectType type, TextureRegion texture, String bhvTree) {
        super(x, y, type, texture, WIDTH, HEIGHT, BODY_WIDTH, BODY_HEIGHT, SPEED, bhvTree);
    }
}
