package com.itlbv.settl.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.enumsObjectType.MapObjectType;

public class Tile extends GameObject {
    private MapObjectType type;
    private static final float WIDTH = 1f;
    private static final float HEIGHT = 1f;
    private boolean collidable;
    private Array<Connection<Tile>> connections;
    private String code;

    public Tile(float x, float y, MapObjectType type, TextureRegion texture) {
        super(x, y, type, WIDTH, HEIGHT);
        super.setTexture(texture);
        this.type = type;
        connections = new Array<Connection<Tile>>();
    }

    /*
    **Getters & setters
     */

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Array<Connection<Tile>> getConnections() {
        return connections;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean isCollidable() {
        return this.collidable;
    }

    public MapObjectType getType() {
        return this.type;
    }

    public Vector2 getCenterPosition() {
        return new Vector2(getRenderPosition().x + WIDTH/2, getRenderPosition().y + HEIGHT/2);
    }
}
