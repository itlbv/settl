package com.itlbv.settl.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.enumsObjectType.MapObjectType;
import com.itlbv.settl.pathfinding.NodeConnection;

public class Tile extends GameObject {
    private MapObjectType type;
    private boolean collidable;
    private int nodeX, nodeY;
    private Array<NodeConnection> connections;

    public Tile(float x, float y, TextureRegion texture, MapObjectType type) {
        super(x, y, texture, type);
        this.type = type;
    }

    /*
    **Getters & setters
     */
    public Array<NodeConnection> getConnections() {
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

    public int getNodeX() {
        return nodeX;
    }

    public void setNodeX(int nodeX) {
        this.nodeX = nodeX;
    }

    public int getNodeY() {
        return nodeY;
    }

    public void setNodeY(int nodeY) {
        this.nodeY = nodeY;
    }
}
