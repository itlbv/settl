package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameWorld;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.Node;

public class CollisionDetector implements RaycastCollisionDetector<Vector2> {

    private Map map;
    private World world;
    private Box2dRaycastCallback callback;

    CollisionDetector () {
        map = Game.map;
        this.world = GameWorld.world;
        this.callback = new Box2dRaycastCallback();
    }

    @Override
    public boolean collides (Ray<Vector2> ray) {
        return findCollision(null, ray);
    }

    @Override
    public boolean findCollision (Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        if (endsInCornerMapTile(inputRay.end)) return true;
        callback.collided = false;
        if (!inputRay.start.epsilonEquals(inputRay.end, MathUtils.FLOAT_ROUNDING_ERROR)) {
            callback.outputCollision = outputCollision;
            world.rayCast(callback, inputRay.start, inputRay.end);
        }
        return callback.collided;
    }


    private boolean endsInCornerMapTile(Vector2 tilePosition) {
        Node node = Game.map.getNodeFromPosition(tilePosition);
        char[] codeArray = node.getCode().toCharArray();
        int code = 0;
        for (char c : codeArray) {
            if (c == '1') {
                code++;
            }
        }
        return code > 0 && code < 3;
    }

    public static class Box2dRaycastCallback implements RayCastCallback {
        Collision<Vector2> outputCollision;
        boolean collided;

        @Override
        public float reportRayFixture (Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            if (outputCollision != null) outputCollision.set(point, normal);
            collided = true;
            return fraction;
        }
    }
}
