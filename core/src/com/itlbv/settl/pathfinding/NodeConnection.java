package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.itlbv.settl.map.Tile;

public class NodeConnection {

    private Tile startNode, targetNode;

    public NodeConnection(Tile startNode, Tile targetNode) {
        this.startNode = startNode;
        this.targetNode = targetNode;
    }

    public float getCost() {
        return 1f;
    }

    public Tile getStartNode() {
        return startNode;
    }

    public Tile getTargetNode() {
        return targetNode;
    }
}
