package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.itlbv.settl.map.Node;

public class ManhattanDistance implements Heuristic<Node> {
    @Override
    public float estimate(Node node, Node endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }
}
