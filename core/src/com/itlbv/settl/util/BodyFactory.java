package com.itlbv.settl.util;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;

public class BodyFactory {

    /*
    public static Body createBody(BodyType bodyTypeSrc, float widthSrc, float heightSrc,
                                  GameObject ownerSrc, boolean isSensorSrc) {
        setClassFields(widthSrc, heightSrc, bodyTypeSrc, ownerSrc, isSensorSrc);
        createBodyDefinition();
        body = Game.world.createBody(bodyDef);
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
    */
    public static Body createBody(int x, int y, BodyType bodyType, Object owner) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x, y);
        body = Game.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        //polygonShape.setAsBox(.5f,.5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        //fixtureDef.isSensor = false;
        //setCollisionBits()
        polygonShape.dispose();
        body.createFixture(fixtureDef);
        body.setUserData(owner);
        //createFrictionJoint
        return body;
    }

    private static void createFrictionJoint(Body ownerBody) {
        FrictionJointDef jointDef = new FrictionJointDef();
        jointDef.initialize(ownerBody, Game.map.mapSensor, ownerBody.getPosition());
        jointDef.maxForce = 25;
        Game.world.createJoint(jointDef);
    }

    public static Body createAndGetMobBody(int x, int y, Mob owner, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body body = Game.world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        if (isSensor) {
            circleShape.setRadius(1f);
        } else {
            circleShape.setRadius(.3f);
        }
        /*
        PolygonShape polygonShape = new PolygonShape();
        if (isSensor) {
            polygonShape.setRadius(1f);
        } else {
            polygonShape.setRadius(.2f);
        }
        */
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = isSensor;
        body.createFixture(fixtureDef);
        body.setUserData(owner);
        circleShape.dispose();
        //createFrictionJoint(body);
        setCollisionBits(fixtureDef, owner);
        return body;
    }

    public static void createMapTileBody(int x, int y, TiledMapTile owner) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(x, y);
        Body body = Game.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(.5f, .5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);
        body.setUserData(owner);
        polygonShape.dispose();
        setCollisionBits(fixtureDef, owner);
    }

    private static FixtureDef createFixtureDef(boolean boxShape, float size, boolean isSensor) {
        PolygonShape polygonShape = new PolygonShape();
        if (boxShape) {
            polygonShape.setAsBox(size, size);
        } else {
            polygonShape.setRadius(size);
        }

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        polygonShape.dispose();
        fixtureDef.isSensor = isSensor;
        return fixtureDef;
    }

    private static BodyDef createBodyDefinition(int x, int y, BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x, y);
        return bodyDef;
    }

    public static Body createBodyForMap(int x, int y, TiledMapTile tile) {
        Body tileBody;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        tileBody = Game.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(.5f, .5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        tileBody.createFixture(fixtureDef);
        tileBody.setUserData(tile);
        polygonShape.dispose();
        return tileBody;
    }

    private static void setCollisionBits(FixtureDef fixtureDef, Object owner) {
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

    public static Body createSensorForMap() {
        Body mapSensor;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-5, -5);
        mapSensor = Game.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(5/2, 5/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        mapSensor.createFixture(fixtureDef);
        mapSensor.setUserData(null);
        polygonShape.dispose();
        return mapSensor;
    }
}
