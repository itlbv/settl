package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.util.TestObject;
import com.itlbv.settl.enumsObjectType.MapObjectType;
import com.itlbv.settl.map.Node;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.pathfinding.Path;
import com.itlbv.settl.pathfinding.PathHelper;

public class PathMovement {
    private Mob owner;
    public Path path;
    private float maxLinearSpeed;

    public PathMovement(Mob owner, float maxLinearSpeed){
        this.owner = owner;
        this.path = new Path();
        this.maxLinearSpeed = maxLinearSpeed;
    }

    public void initAndCalculatePath() {
        Vector2 startPosition = owner.getPosition();
        Vector2 targetPosition = getTarget().getPosition(); //TODO what if target doesn't have a body
        path = PathHelper.getPath(startPosition, targetPosition);
        drawPath();
    }

    public Vector2 calculateAndGet() {
        Vector2 nextWaypoint = getNextWaypoint();
        return getVectorToWaypoint(nextWaypoint);
    }

    private Vector2 getNextWaypoint() {
        Vector2 nextPosition = path.getFirstPosition();
        Vector2 currentPosition = owner.getPosition();
        if(nextPosition.dst(currentPosition) < .7f) { //TODO touches corners with 0.7
            path.nodes.removeIndex(0);
            if (path.size() == 0) {
                return currentPosition;
            }
            nextPosition = path.getFirstPosition();
        }
        return nextPosition;
    }

    private Vector2 getVectorToWaypoint(Vector2 nextWaypoint) {
        return nextWaypoint.cpy().sub(owner.getPosition()).nor().scl(maxLinearSpeed);
    }

    private void drawPath() {
        for (Node node : path.nodes) {
            TextureRegion texture = new TextureRegion(new Texture("white_dot.png"));
            TestObject o = new TestObject(node.getX(),node.getY(), MapObjectType.TESTOBJECT, texture);
            Game.testObjects.add(o);
        }
    }

    private Mob getTarget() {
        return (Mob) owner.getTarget();
    }

    public boolean isEmpty() {
        return path.size() == 0;
    }

    public void clearPath() {
        path.clear();
    }
}
