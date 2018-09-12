package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class ManhattanDistance<Tile> implements Heuristic<Tile> {

    Tile startNode, targetNode;
    @Override
    public float estimate(Tile node, Tile endNode) {
        return Math.abs(endNode.getNodeX() - node.getNodeX()) + Math.abs(endNode.getNodeY() - node.getNodeY());
    }
}
