package com.itlbv.settl.mob.action.movement;

import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.movement.util.Path;
import com.itlbv.settl.mob.movement.util.PathUtil;

public class PathMovement {
    private Mob owner;
    private Path path;
    private float maxLinearSpeed;

    public PathMovement(Mob owner, float maxLinearSpeed) {
        this.owner = owner;
        this.maxLinearSpeed = maxLinearSpeed;
        this.path = new Path();
    }

    public void init() {
        Vector2 startPosition = owner.getPosition();
        Vector2 targetPosition = owner.getTarget().getPosition();
        path = PathUtil.getPath(startPosition, targetPosition);
    }

    public Vector2 getVelocity() {
        if (path.isEmpty()) {
            System.out.println("PATH IS EMPTY; MOB " + owner.toString()); //TODO throw an exception
            return new Vector2(0, 0);
        }
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

    public void clearPath() {
        path.clear();
    }

    public Path getPath() {
        //TODO only for path drawing in DebugRenderer
        return path;
    }
}
