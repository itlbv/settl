package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.Tile;

public class PathHelper{

    public static DefaultGraphPath<Tile> getPath(Vector2 startPosition, Vector2 targetPosition) {
        if (alreadyInTargetTile()) {
            return null;
        }
        Tile startNode = Map.getInstance().getTileFromPosition(startPosition);
        Tile targetNode = Map.getInstance().getTileFromPosition(targetPosition);

        return getNodePath(startNode, targetNode);
    }

    private static DefaultGraphPath<Tile> getNodePath(Tile startNode, Tile targetNode) {
        IndexedAStarPath indexedAStarPath = new IndexedAStarPath(Map.getInstance());
        DefaultGraphPath<Tile> path = new DefaultGraphPath<>();
        ManhattanDistance<Tile> heuristic = new ManhattanDistance<>();
        indexedAStarPath.searchNodePath(startNode, targetNode, heuristic, path);
        return path;
    }

    private static boolean alreadyInTargetTile() {
        return false;
    } //TODO make it work
}
