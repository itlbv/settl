package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.map.Node;

public class Path extends DefaultGraphPath<Node> implements SmoothableGraphPath<Node, Vector2> {

    @Override
    public Vector2 getNodePosition(int index) {
        return Game.map.getNodePosition(index);
    }

    @Override
    public void swapNodes(int index1, int index2) {
        nodes.swap(index1,index2);
    }

    @Override
    public void truncatePath(int newLength) {
        nodes.truncate(newLength);
    }

    public int size() {
        return nodes.size;
    }

    public Vector2 getFirstPosition() {
        return nodes.get(0).getPosition();
    }
}
