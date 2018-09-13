package com.itlbv.settl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MapObjectType;

public class TestObject extends GameObject {
    private MapObjectType type;
    private static final float WIDTH = .1f;
    private static final float HEIGHT = .1f;

    public TestObject(float x, float y, MapObjectType type, TextureRegion texture) {
        super(x, y, type, texture, WIDTH, HEIGHT);
        this.type = type;
    }

}
