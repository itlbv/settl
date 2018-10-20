package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.map.Node;

public class PathHelper {

    private static Path path;

    public static Path getPath(Vector2 startPosition, Vector2 targetPosition) {
        //TODO check if already in position
        path = new Path();
        Node startNode = Game.map.getNodeFromPosition(startPosition);
        Node targetNode = Game.map.getNodeFromPosition(targetPosition);
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
}
