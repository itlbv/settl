package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.itlbv.settl.GameWorld;
import com.itlbv.settl.enumsObjectType.MapObjectType;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.Tile;

public class CollisionDetector implements RaycastCollisionDetector<Vector2> {

    Map map;
    World world;
    Box2dRaycastCallback callback;

    public CollisionDetector () {
        map = Map.getInstance();
        this.world = GameWorld.world;
        this.callback = new Box2dRaycastCallback();
    }


    @Override
    public boolean collides (Ray<Vector2> ray) {
        return findCollision(null, ray);
    }

    /*
    @Override
    public boolean collides(Ray<Vector2> ray) {
        int x0 = (int)ray.start.x;
        int y0 = (int)ray.start.y;
        int x1 = (int)ray.end.x;
        int y1 = (int)ray.end.y;

        int tmp;
        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if (steep) {
            // Swap x0 and y0
            tmp = x0;
            x0 = y0;
            y0 = tmp;
            // Swap x1 and y1
            tmp = x1;
            x1 = y1;
            y1 = tmp;
        }
        if (x0 > x1) {
            // Swap x0 and x1
            tmp = x0;
            x0 = x1;
            x1 = tmp;
            // Swap y0 and y1
            tmp = y0;
            y0 = y1;
            y1 = tmp;
        }

        int deltax = x1 - x0;
        int deltay = Math.abs(y1 - y0);
        int error = 0;
        int y = y0;
        int ystep = (y0 < y1 ? 1 : -1);
        for (int x = x0; x <= x1; x++) {
            Map map = Map.getInstance();
            Tile tile = steep ? map.getTileFromPosition(new Vector2(y, x)) : map.getTileFromPosition(new Vector2(x, y));
            if (tile.getType() != MapObjectType.GRASS) return true; // We've hit a wall
            error += deltay;
            if (error + error >= deltax) {
                y += ystep;
                error -= deltax;
            }
        }
        return false;
    }
    */

    @Override
    public boolean findCollision (Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        callback.collided = false;
        if (!inputRay.start.epsilonEquals(inputRay.end, MathUtils.FLOAT_ROUNDING_ERROR)) {
            callback.outputCollision = outputCollision;
            world.rayCast(callback, inputRay.start, inputRay.end);
        }
        if (isCornerTile(inputRay.end)) {
            return true;
        }
        return callback.collided;
    }

    private boolean isCornerTile(Vector2 tilePosition) {
        Tile tile = map.getTileFromPosition(tilePosition);
        char[] codeArray = tile.getCode().toCharArray();
        int code = 0;
        for (char c : codeArray) {
            if (c == '1') {
                code++;
            }
        }
        return (code > 0 && code < 3) ? true : false;
    }

    public static class Box2dRaycastCallback implements RayCastCallback {
        public Collision<Vector2> outputCollision;
        public boolean collided;

        public Box2dRaycastCallback () {
        }

        @Override
        public float reportRayFixture (Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            if (outputCollision != null) outputCollision.set(point, normal);
            collided = true;
            return fraction;
        }
    }
        /*
        @Override
        public boolean collides(Ray<Vector2> ray) {
            int x0 = (int)ray.start.x;
            int y0 = (int)ray.start.y;
            int x1 = (int)ray.end.x;
            int y1 = (int)ray.end.y;

            int tmp;
            boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
            if (steep) {
                // Swap x0 and y0
                tmp = x0;
                x0 = y0;
                y0 = tmp;
                // Swap x1 and y1
                tmp = x1;
                x1 = y1;
                y1 = tmp;
            }
            if (x0 > x1) {
                // Swap x0 and x1
                tmp = x0;
                x0 = x1;
                x1 = tmp;
                // Swap y0 and y1
                tmp = y0;
                y0 = y1;
                y1 = tmp;
            }

            int deltax = x1 - x0;
            int deltay = Math.abs(y1 - y0);
            int error = 0;
            int y = y0;
            int ystep = (y0 < y1 ? 1 : -1);
            for (int x = x0; x <= x1; x++) {
                N tile = steep ? worldMap.getNode(y, x) : worldMap.getNode(x, y);
                if (tile.type != TiledNode.TILE_FLOOR) return true; // We've hit a wall
                error += deltay;
                if (error + error >= deltax) {
                    y += ystep;
                    error -= deltax;
                }
            }

            return false;
        }
        */
}
