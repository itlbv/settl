package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.SteerableBody;
import com.itlbv.settl.TestObject;
import com.itlbv.settl.enumsObjectType.MapObjectType;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.pathfinding.Path;
import com.itlbv.settl.pathfinding.PathHelper;

public class MoveManager {
    private Mob owner;
    private Path path;
    private SteerableBody body;
    private float speed;

    public MoveManager(float speed, Mob owner) {
        this.owner = owner;
        this.body = owner.getBody();

        this.path = new Path();
        this.speed = speed;
    }

    public boolean calculatePathToTarget() {
        GameObject target = owner.getTarget();
        if (target == null) {
            return false;
        }
        Vector2 startPosition = body.getPosition();
        Vector2 targetPosition = target.getBodyPosition();
        path = PathHelper.getPath(startPosition, targetPosition);
        return path.size() > 0;
    }

    public void stop() {
        body.setLinearVelocity(new Vector2(0f, 0f));
    }

    public void update() {
        updatePathMovement();
    }

    private void updatePathMovement() {
        if (path.size() == 0) {
            return;
        }
        followPath();
        drawPath();
    }

    private void followPath() {
        Vector2 nextWaypoint = getNextWaypoint();
        Vector2 vectorToWaypoint = getVectorToWaypoint(nextWaypoint);
        body.setLinearVelocity(vectorToWaypoint);
    }

    private Vector2 getNextWaypoint() {
        Vector2 nextPosition = path.getFirstPosition();
        Vector2 currentPosition = body.getPosition();
        if(nextPosition.cpy().sub(currentPosition).len() < .2f) {
            Game.testObjects.removeIndex(0); //TODO path drawing
            path.nodes.removeIndex(0);
            if (path.size() == 0) {
                return currentPosition;
            }
            nextPosition = path.getFirstPosition();
        }
        return nextPosition;
    }

    private Vector2 getVectorToWaypoint(Vector2 nextWaypoint) {
        return nextWaypoint.cpy().sub(body.getPosition()).nor().scl(speed);
    }

    private void drawPath() {
        for (Tile node : path.nodes) {
            TextureRegion texture = new TextureRegion(new Texture("white_dot.png"));
            TestObject o = new TestObject(node.getX() + .5f,node.getY() + .5f, MapObjectType.TESTOBJECT, texture);
            Game.testObjects.add(o);
        }
    }
}
