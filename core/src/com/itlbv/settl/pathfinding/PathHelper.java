package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.Tile;

public class PathHelper {

    private static Path path;

    public static Path getPath(Vector2 startPosition, Vector2 targetPosition) {
        //TODO check if already in position
        path = new Path();
        Tile startNode = Map.getInstance().getTileFromPosition(startPosition);
        Tile targetNode = Map.getInstance().getTileFromPosition(targetPosition);

        calculatePath(startNode, targetNode);
        //path = smoothPath(path); //TODO path smoothing
        return path;
    }

    private static void calculatePath(Tile startNode, Tile targetNode) {
        ManhattanDistance heuristic = new ManhattanDistance();
        IndexedAStarPathFinder<Tile> pathFinder = new IndexedAStarPathFinder<>(Map.getInstance());
        pathFinder.searchNodePath(startNode, targetNode, heuristic, path);
    }

    private static void smoothPath() {
        //PathSmoother<Tile, Vector2> smoother = new PathSmoother<>();
    }
}
