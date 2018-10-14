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
    private Path path;
    private Vector2 linearVelocity;
    private float maxLinearSpeed;
    private Seek<Vector2> steeringBehavior;
    private SteeringAcceleration<Vector2> steeringOutput;

    public MovementManager(float speed, Mob owner) {
        this.owner = owner;
        this.path = new Path();
        this.linearVelocity = new Vector2();
        this.maxLinearSpeed = speed;
        this.steeringOutput = new SteeringAcceleration<>(new Vector2());
        this.steeringBehavior = new Seek<>(owner);
    }

    public void update() {
        if (getTarget() == null) { //TODO
            return;
        }
        if (path.size() > 0) {
            updatePathMovement();
            checkTargetDistanceAndVisibility();
        } else {
            updateSteeringMovement();
        }
        checkSensorAlignment();
    }

    public void initMovingToTarget() {
        owner.setState(MobState.WALK);
        if (isTargetCloseAndVisible()) {
            setSteeringMovement();
        } else {
            setPathMovement();
        }
    }

    private float TIME_TRESHOLD = 0f;

    private void checkTargetDistanceAndVisibility() {
        TIME_TRESHOLD += Game.DELTA_TIME;
        if (TIME_TRESHOLD < 2f) {
            return;
        }
        TIME_TRESHOLD = 0f;
        if (isTargetCloseAndVisible()) {
            path.clear();
            setSteeringMovement();
        }
    }
    private boolean isTargetCloseAndVisible() {
        if (getDistanceToTarget() > 1000) {
            return false;
        }
        RayCastHelper collisionCallback = new RayCastHelper();
        collisionCallback.collided = false;
        Ray<Vector2> rayToTarget = new Ray<>(owner.getPosition(), getTarget().getPosition());
        GameWorld.world.rayCast(collisionCallback, rayToTarget.start, rayToTarget.end);
        if (collisionCallback.collided) {
            return false;
        }
        return true;
    }

    private float getDistanceToTarget() {
        return owner.getPosition().dst(getTarget().getPosition());
    }

    private void setSteeringMovement() {
        steeringBehavior.setTarget(getTarget());
        steeringBehavior.setEnabled(true);
    }

    private void updateSteeringMovement() {
        if (!steeringBehavior.isEnabled()) {
            return;
        }
        steeringBehavior.calculateSteering(steeringOutput);
        linearVelocity.mulAdd(steeringOutput.linear, Game.DELTA_TIME).limit(maxLinearSpeed);
        setLinearVelocity();
    }

    private void checkSensorAlignment() {
        Vector2 bodyPosition = owner.getBody().getPosition();
        Vector2 sensorPosition = owner.getSensor().getPosition();
        if (bodyPosition.equals(sensorPosition)) {
            return;
        }
        Vector2 vectorToBody = bodyPosition.sub(sensorPosition);
        owner.getSensor().setLinearVelocity(owner.getBody().getLinearVelocity().cpy().mulAdd(vectorToBody,10));
    }

    private void setLinearVelocity() {
        owner.getBody().setLinearVelocity(linearVelocity);
        owner.getSensor().setLinearVelocity(linearVelocity);
    }

    private void setPathMovement() {
        Vector2 startPosition = owner.getPosition();
        Vector2 targetPosition = getTarget().getPosition(); //TODO what if target doesn't have a body
        path = PathHelper.getPath(startPosition, targetPosition);
    }

    private void updatePathMovement() {
        followPath();
        //drawPath();
    }

    private void followPath() {
        Vector2 nextWaypoint = getNextWaypoint();
        linearVelocity = getVectorToWaypoint(nextWaypoint);
        setLinearVelocity();
    }

    private Vector2 getNextWaypoint() {
        Vector2 nextPosition = path.getFirstPosition();
        Vector2 currentPosition = owner.getPosition();
        if(nextPosition.dst(currentPosition) < .2f) { //TODO what if render frequency changes
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
        owner.setState(MobState.IDLE);
        steeringBehavior.setEnabled(false);
        path.clear();
        linearVelocity.set(0f, 0f);
        setLinearVelocity();
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

    private GameObject getTarget() {
        return owner.getTarget();
    }
}
