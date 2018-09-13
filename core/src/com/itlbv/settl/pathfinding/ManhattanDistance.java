package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.itlbv.settl.map.Tile;

public class ManhattanDistance implements Heuristic<Tile> {
    @Override
    public float estimate(Tile node, Tile endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }
}
