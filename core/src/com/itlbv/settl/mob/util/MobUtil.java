package com.itlbv.settl.mob.util;

import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;

public class MobUtil {

    public static Mob getTargetMob(Mob mob) {
        /*
        TODO check if target is MOB
          If owner is under attack while running and its target is not a Mob
          (Mob)owner.getTarget() causes exception
         */
        return (Mob) mob.getTarget();
    }

    public static boolean isTargetCloseAndVisible(Mob owner) {
        if (getDistanceToTarget(owner) > 20) {
            return false;
        }
        Ray<Vector2> rayToTarget = new Ray<>(owner.getPosition(), owner.getTarget().getPosition());

        RayCastToTarget collisionDetector = new RayCastToTarget(owner);
        Game.world.rayCast(collisionDetector, rayToTarget.start, rayToTarget.end);
        return !collisionDetector.collided;
    }

    private static float getDistanceToTarget(Mob owner) {
        return owner.getPosition().dst(owner.getTarget().getPosition());
    }

    private static class RayCastToTarget implements RayCastCallback {
        boolean collided;
        Mob owner;

        RayCastToTarget(Mob owner) {
            this.owner = owner;
        }

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            collided = fixture.getBody().getUserData() != owner.getTarget();
            return fraction;
        }
    }
}
