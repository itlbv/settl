package com.itlbv.settl.map;

import com.badlogic.gdx.graphics.Texture;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.enums.MapObjectType;

public class Tile extends GameObject {

    public Tile(float x, float y, Texture texture, MapObjectType type) {
        super(x, y, texture, type);
    }
}
