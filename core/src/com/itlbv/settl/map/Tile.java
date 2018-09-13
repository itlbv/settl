package com.itlbv.settl.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
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

    public Tile(float x, float y, MapObjectType type, TextureRegion texture) {
        super(x, y, type, texture, WIDTH, HEIGHT);
        this.type = type;
        connections = new Array<Connection<Tile>>();
    }

    TextureRegion texture; //TODO delete after testing path drawing
    public void setTexture(TextureRegion texture) {
        super.texture = texture;
    }

    /*
    **Getters & setters
     */
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
        return new Vector2(getPosition().x + .5f, getPosition().y + .5f);
    }
}
