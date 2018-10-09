package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.itlbv.settl.map.Tile;

public class ManhattanDistance implements Heuristic<Tile> {
    @Override
    public float estimate(Tile node, Tile endNode) {
        return Math.abs(endNode.getRenderX() - node.getRenderX()) + Math.abs(endNode.getRenderY() - node.getRenderY());
    }
}
