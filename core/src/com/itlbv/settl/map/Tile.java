package com.itlbv.settl.map;

import com.badlogic.gdx.graphics.Texture;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.enums.MapObjectType;

public class Tile extends GameObject {

    private boolean passable;

    public Tile(float x, float y, float width, float height, Texture texture, MapObjectType type) {
        super(x, y, width, height, texture, type);
    }
}
