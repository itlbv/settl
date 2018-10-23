package com.itlbv.settl.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.enumsObjectType.MapObjectType;

public class TestObject extends GameObject {
    private MapObjectType type;
    private static final float WIDTH = .1f;
    private static final float HEIGHT = .1f;

    public TestObject(float x, float y, MapObjectType type, TextureRegion texture) {
        super(type, WIDTH, HEIGHT);
        super.setTexture(texture);
        this.type = type;
    }

}
