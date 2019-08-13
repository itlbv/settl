package com.itlbv.settl.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Node {
    private int x, y;
    private String code;
    private Vector2 position;
    private Array<Connection<Node>> connections;
    private boolean passable;

    Node(int x, int y, boolean passable) {
        this.x = x;
        this.y = y;
        connections = new Array<>();
        this.position = new Vector2(x, y);
        this.passable = passable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    Array<Connection<Node>> getConnections() {
        return connections;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isPassable() {
        return passable;
    }
}
