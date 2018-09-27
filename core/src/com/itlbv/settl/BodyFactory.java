package com.itlbv.settl;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.itlbv.settl.mobs.Mob;

public class BodyFactory {
    private static float width, height;
    private static BodyType bodyType;
    private static GameObject owner;
    private static boolean isSensor;

    private static BodyDef bodyDef;
    private static Body body;

    public static Body createBody(float widthSrc, float heightSrc, BodyType bodyTypeSrc,
                                  GameObject ownerSrc, boolean isSensorSrc) {
        setClassFields(widthSrc, heightSrc, bodyTypeSrc, ownerSrc, isSensorSrc);
        createBodyDefinition();
        body = GameWorld.world.createBody(bodyDef);
        createPolygonShapeAndFixtureDef();
        body.setUserData(owner);
        return body;
    }
    private static void setClassFields(float widthSrc, float heightSrc, BodyType bodyTypeSrc, GameObject ownerSrc, boolean isSensorSrc) {
        width = widthSrc;
        height = heightSrc;
        bodyType = bodyTypeSrc;
        owner = ownerSrc;
        isSensor = isSensorSrc;
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
        bodyDef.position.set(owner.getBodyPosition().x, owner.getBodyPosition().y);
    }

    private static void calculateBodyPosition() {
        float bodyX = owner.getPosition().x + owner.getWidth()/2;
        float bodyY = owner.getPosition().y + height / 2;
        bodyDef.position.set(bodyX, bodyY);
    }

    private static void createPolygonShapeAndFixtureDef() {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width/2, height/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = isSensor;
        setCollisionBits(fixtureDef, owner);
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    private static void setCollisionBits(FixtureDef fixtureDef, GameObject owner) {
        if (owner instanceof Mob) {
            if (fixtureDef.isSensor) {
                fixtureDef.filter.categoryBits = CollisionBits.MOB_SENSOR;
                fixtureDef.filter.maskBits = CollisionBits.MOB_BODY;
            } else {
                fixtureDef.filter.categoryBits = CollisionBits.MOB_BODY;
                fixtureDef.filter.maskBits = CollisionBits.MOB_SENSOR;
            }
        } else {
            fixtureDef.filter.categoryBits = CollisionBits.MAP_TILE;
            fixtureDef.filter.maskBits = CollisionBits.MOB_BODY;
        }
    }
}
