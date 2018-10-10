package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.itlbv.settl.*;
import com.itlbv.settl.enumsObjectType.MapObjectType;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.pathfinding.Path;
import com.itlbv.settl.pathfinding.PathHelper;

public class MovementManager {
    private final Mob owner;
    private GameObject target;
    private Path path;
    private Vector2 linearVelocity;
    private float maxLinearSpeed;
    private Seek<Vector2> steeringBehavior;
    private SteeringAcceleration<Vector2> steeringOutput;

    private float TIME_TRESHOLD = 0f;

    public MovementManager(float speed, Mob owner) {
        this.owner = owner;
        this.target = owner.getTarget();
        this.path = new Path();
        this.linearVelocity = new Vector2();
        this.maxLinearSpeed = speed;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
        this.steeringBehavior = new Seek<>(owner);
    }

    public void update() {
        if (path.size() > 0) {
            updatePathMovement();
            TIME_TRESHOLD += Game.DELTA_TIME;
            checkForTargetDistanceAndVisibility();
        } else {
            updateSteeringMovement();
        }
    }

    public void initMovingToTarget() {
        target = owner.getTarget();
        if (target == null) {
            return; //TODO throw an exception
        }
        if (target instanceof GameObject) {
            calculatePathToTarget();
        } else if (target instanceof Mob) {
            if (targetIsCloseAndVisible()) {
                setTargetForSteering();
            } else {
                calculatePathToTarget();
            }
        }
    }

    private void checkForTargetDistanceAndVisibility() {
        if (TIME_TRESHOLD < 1f) {
            return;
        }
        TIME_TRESHOLD = 0f;
        if (targetIsCloseAndVisible()) {
            path.clear();
            setTargetForSteering();
        }
    }

    private boolean targetIsCloseAndVisible() {
        if (owner.getPosition().dst(target.getPosition()) > 1000) {
            return false;
        }
        RayCastHelper collisionCallback = new RayCastHelper();
        collisionCallback.collided = false;
        Ray<Vector2> rayToTarget = new Ray<>(owner.getPosition(), target.getPosition());
        GameWorld.world.rayCast(collisionCallback, rayToTarget.start, rayToTarget.end);
        if (collisionCallback.collided) {
            return false;
        }
        return true;
    }

    private void setTargetForSteering() {
        steeringBehavior.setTarget(target);
        steeringBehavior.setEnabled(true);
    }

    private void updateSteeringMovement() {
        if (!steeringBehavior.isEnabled()) {
            return;
        }
        if (steeringBehavior.getTarget() == null) {
            return;
        }
        steeringBehavior.calculateSteering(steeringOutput);
        linearVelocity.mulAdd(steeringOutput.linear, Game.DELTA_TIME).limit(maxLinearSpeed);
        setLinearVelocityToOwner();
    }

    private void setLinearVelocityToOwner() {
        if (owner.getState() != MobState.WALK) {
            owner.setState(MobState.WALK);
        }
        owner.setLinearVelocity(linearVelocity);
    }

    public void calculatePathToTarget() {
        GameObject target = owner.getTarget();
        if (target == null) {
            return; //TODO throw an exception
        }

        //System.out.println("Calculating path");

        Vector2 startPosition = owner.getPosition();
        Vector2 targetPosition = target.getPosition(); //TODO what if target doesn't have a body
        path = PathHelper.getPath(startPosition, targetPosition);
    }

    private void updatePathMovement() {
        followPath();
        drawPath();
    }

    private void followPath() {
        Vector2 nextWaypoint = getNextWaypoint();
        linearVelocity = getVectorToWaypoint(nextWaypoint);
        setLinearVelocityToOwner();
    }

    private Vector2 getNextWaypoint() {
        Vector2 nextPosition = path.getFirstPosition();
        Vector2 currentPosition = owner.getPosition();
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
        return nextWaypoint.cpy().sub(owner.getPosition()).nor().scl(maxLinearSpeed);
    }

    private void drawPath() {
        for (Tile node : path.nodes) {
            TextureRegion texture = new TextureRegion(new Texture("white_dot.png"));
            TestObject o = new TestObject(node.getRenderX() + .5f,node.getRenderY() + .5f, MapObjectType.TESTOBJECT, texture);
            Game.testObjects.add(o);
        }
    }

    public void stopMoving() {
        owner.setLinearVelocity(new Vector2(0f, 0f));
        path.clear();
        steeringBehavior.setEnabled(false);
        owner.setState(MobState.IDLE);
    }

    private class RayCastHelper implements RayCastCallback {
        boolean collided;
        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            if (fixture.getBody().getUserData() == owner.getTarget()) {
                return -1;
            } else {
                collided = true;
                return 0;
            }
        }
    }
}
