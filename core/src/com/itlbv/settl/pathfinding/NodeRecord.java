package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.utils.BinaryHeap;
import com.itlbv.settl.map.Tile;

public class NodeRecord extends BinaryHeap.Node {
    public int searchId;

    public enum Category
    {
        UNVISITED,
        OPEN,
        CLOSED
    }

    public Category category;

    public Tile node;

    public NodeConnection connection;

    public float cost;

    public NodeRecord () {
        super(0);
    }

    public float getEstimatedTotalCost () {
        return getValue();
    }

    public float heuristic()
    {
        return getEstimatedTotalCost() - cost;
    }
}
