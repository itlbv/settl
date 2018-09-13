package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.map.Tile;

public class Path extends DefaultGraphPath<Tile> implements SmoothableGraphPath<Tile, Vector2> {
    @Override
    public Vector2 getNodePosition(int index) {
        return null;
    }

    @Override
    public void swapNodes(int index1, int index2) {

    }

    @Override
    public void truncatePath(int newLength) {

    }

    public int size() {
        return nodes.size;
    }

    public Vector2 getFirstPosition() {
        return nodes.first().getCenterPosition();
    }
}
