package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.pathfinding.Path;
import com.itlbv.settl.pathfinding.PathHelper;

public class PathMovement {
    private Mob owner;
    public Path path; // TODO public is for path drawing in Game class
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
    }

    public Vector2 calculateAndGet() {
        Vector2 nextWaypoint = getNextWaypoint();
        return getVectorToWaypoint(nextWaypoint);
    }

    private Vector2 getNextWaypoint() {
        Vector2 nextPosition = path.getFirstPosition(); //TODO falls when there is no path
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
