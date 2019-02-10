package com.itlbv.settl.util;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.util.MobConstants;

public class BodyFactory {

    public static void createBodyAndSensorForMob(int x, int y, Mob mob) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body body = Game.world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(MobConstants.MOB_BODY_RADIUS);
        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = circleShape;
        bodyFixtureDef.isSensor = false;
        body.createFixture(bodyFixtureDef);
        body.setUserData(mob);
        mob.setBody(body);
        setCollisionBits(bodyFixtureDef, mob);
        createFrictionJoint(body);

        Body sensor = Game.world.createBody(bodyDef);
        circleShape.setRadius(MobConstants.MOB_SENSOR_RADIUS);
        FixtureDef sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = circleShape;
        sensorFixtureDef.isSensor = true;
        sensor.createFixture(sensorFixtureDef);
        sensor.setUserData(mob);
        mob.setSensor(sensor);
        setCollisionBits(sensorFixtureDef, mob);

        circleShape.dispose();
    }

    private static void createFrictionJoint(Body ownerBody) {
        FrictionJointDef jointDef = new FrictionJointDef();
        jointDef.initialize(ownerBody, Game.map.mapSensor, ownerBody.getPosition());
        jointDef.maxForce = 25;
        Game.world.createJoint(jointDef);
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

    public static Body createAndGetMapSensorForObjectsFrictionJoints() {
        Body mapSensor;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(20, 20);
        mapSensor = Game.world.createBody(bodyDef);
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(1, 1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        mapSensor.createFixture(fixtureDef);
        mapSensor.setUserData(null);
        polygonShape.dispose();
        return mapSensor;
    }
}
