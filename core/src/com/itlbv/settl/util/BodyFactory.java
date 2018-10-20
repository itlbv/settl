package com.itlbv.settl.util;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.GameWorld;
import com.itlbv.settl.mobs.Mob;

public class BodyFactory {
    private static float width, height;
    private static BodyType bodyType;
    private static GameObject owner;
    private static boolean isSensor;

    private static BodyDef bodyDef;
    private static Body body;

    private static void setClassFields(float widthSrc, float heightSrc, BodyType bodyTypeSrc, GameObject ownerSrc, boolean isSensorSrc) {
        width = widthSrc;
        height = heightSrc;
        bodyType = bodyTypeSrc;
        owner = ownerSrc;
        isSensor = isSensorSrc;
    }
    public static Body createBody(BodyType bodyTypeSrc, float widthSrc, float heightSrc,
                                  GameObject ownerSrc, boolean isSensorSrc) {
        setClassFields(widthSrc, heightSrc, bodyTypeSrc, ownerSrc, isSensorSrc);
        createBodyDefinition();
        body = GameWorld.world.createBody(bodyDef);
        createPolygonShapeAndFixtureDef();
        body.setUserData(owner);
        return body;
    }

    private static void createBodyDefinition() {
        bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        if (isSensor) {
            setSensorPositionToCenterOfBody();
        } else {
            calculateBodyPosition();
        }
    }

    private static void setSensorPositionToCenterOfBody() {
        bodyDef.position.set(owner.getPosition());
    }

    private static void calculateBodyPosition() {
        //float bodyX = 4;
        //float bodyY = 4;
        float bodyX = owner.getRenderPosition().x + owner.getRenderWidth()/2;
        float bodyY = owner.getRenderPosition().y + height / 2;
        bodyDef.position.set(bodyX, bodyY);
    }

    private static void createPolygonShapeAndFixtureDef() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width/2, height/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.friction = .5f;
        setCollisionBits(fixtureDef, owner);
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    public static Body createSensorForMap() {
        Body mapSensor;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-5, -5);
        mapSensor = GameWorld.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10/2, 10/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        mapSensor.createFixture(fixtureDef);
        mapSensor.setUserData(null);
        polygonShape.dispose();
        return mapSensor;
    }

    public static Body createBodyForMap(int x, int y, TiledMapTile tile) {
        //TODO make it to BodyFactory
        Body tileBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        tileBody = GameWorld.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(.5f, .5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        tileBody.createFixture(fixtureDef);
        tileBody.setUserData(tile);
        polygonShape.dispose();
        return tileBody;
    }

    private static void setCollisionBits(FixtureDef fixtureDef, GameObject owner) {
        if (owner instanceof Mob) {
            if (fixtureDef.isSensor) {
                fixtureDef.filter.categoryBits = CollisionBits.MOB_SENSOR;
                fixtureDef.filter.maskBits = CollisionBits.MOB_BODY;
            } else {
                fixtureDef.filter.categoryBits = CollisionBits.MOB_BODY;
                fixtureDef.filter.maskBits = CollisionBits.MAP_TILE | CollisionBits.MOB_BODY | CollisionBits.MOB_SENSOR;
            }
        } else {
            fixtureDef.filter.categoryBits = CollisionBits.MAP_TILE;
            fixtureDef.filter.maskBits = CollisionBits.MOB_BODY;
        }
    }
}
