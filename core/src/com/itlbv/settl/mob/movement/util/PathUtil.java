package com.itlbv.settl.mob.movement.util;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.map.Node;

public class PathUtil {

    private static Path path;

    public static Path getPath(Vector2 startPosition, Vector2 targetPosition) {
        path = new Path();
        Node startNode = Game.map.getNodeFromCoord(startPosition);
        Node targetNode = Game.map.getNodeFromCoord(targetPosition);
        calculatePath(startNode, targetNode);
        smoothPath();
        return path;
    }

    private static void calculatePath(Node startNode, Node targetNode) {
        ManhattanDistance heuristic = new ManhattanDistance();
        IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<>(Game.map);
        pathFinder.searchNodePath(startNode, targetNode, heuristic, path);
    }

    private static void smoothPath() {
        CollisionDetector collisionDetector = new CollisionDetector();
        PathSmoother<Node, Vector2> smoother = new PathSmoother<>(collisionDetector);
        smoother.smoothPath(path);
    }

    private static class ManhattanDistance implements Heuristic<Node> {
        @Override
        public float estimate(Node node, Node endNode) {
            return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
        }
    }
}
