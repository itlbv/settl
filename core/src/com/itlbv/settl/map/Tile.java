package com.itlbv.settl.map;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.enumsObjectType.MapObjectType;

public class Tile extends GameObject {
    private MapObjectType type;

    public Tile(float x, float y, TextureRegion texture, MapObjectType type) {
        super(x, y, texture, type);
        this.type = type;
    }

    public MapObjectType getType() {
        return this.type;
    }
}
