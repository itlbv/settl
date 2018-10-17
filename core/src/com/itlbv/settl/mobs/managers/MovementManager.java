package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
    private PathMovement pathMovement;
    private SteeringMovement steeringMovement;

    private Vector2 linearVelocity;
    private float maxLinearSpeed;

    public MovementManager(float speed, Mob owner) {
        this.owner = owner;
        this.linearVelocity = new Vector2();
        this.maxLinearSpeed = speed;
        this.pathMovement = new PathMovement(owner, maxLinearSpeed);
        this.steeringMovement = new SteeringMovement(owner, maxLinearSpeed);
    }

    public void initMoving() {
        owner.setState(MobState.WALK);
        if (isTargetCloseAndVisible()) {
            steeringMovement.init();
        } else {
            pathMovement.init();
        }
    }

    public void update() {
        if (!pathMovement.isEmpty()) {
            linearVelocity = pathMovement.calculateAndGet();
            checkTargetDistanceAndVisibility();
        } else {
            linearVelocity = steeringMovement.calculateAndGet();
        }
        setLinearVelocity();
        checkSensorAlignment();
    }

    private float TIME_TRESHOLD = 0f;

    private void checkTargetDistanceAndVisibility() {
        TIME_TRESHOLD += Game.DELTA_TIME;
        if (TIME_TRESHOLD < 2f) {
            return;
        }
        TIME_TRESHOLD = 0f;
        if (isTargetCloseAndVisible()) {
            pathMovement.path.clear();
            steeringMovement.init();
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

    private void checkSensorAlignment() {
        Vector2 bodyPosition = owner.getBody().getPosition();
        Vector2 sensorPosition = owner.getSensor().getPosition();
        if (bodyPosition.epsilonEquals(sensorPosition, MathUtils.FLOAT_ROUNDING_ERROR)) {
            return;
        }
        Vector2 vectorToBody = bodyPosition.sub(sensorPosition);
        owner.getSensor().setLinearVelocity(owner.getBody().getLinearVelocity().cpy().mulAdd(vectorToBody,10));
    }

    private void setLinearVelocity() {
        owner.getBody().setLinearVelocity(linearVelocity);
        owner.getSensor().setLinearVelocity(linearVelocity);
    }

    public void stopMoving() {
        owner.setState(MobState.IDLE);
        steeringMovement.steeringBehavior.setEnabled(false);
        pathMovement.path.clear();
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
