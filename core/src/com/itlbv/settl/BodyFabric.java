package com.itlbv.settl;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class BodyFabric {
    public static Body createBody(float width, float height, BodyType bodyType, GameObject owner, boolean isSensor) {
        BodyDef bodyDef = getBodyDefinition(bodyType, owner, height);
        Body body = GameWorld.world.createBody(bodyDef);
        createPolygonShapeAndFixtureDef(body, width, height, isSensor);
        return body;
    }

    private static BodyDef getBodyDefinition(BodyType bodyType, GameObject owner, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        float bodyX = owner.getPosition().x + owner.getWidth()/2;
        float bodyY = owner.getPosition().y + height/2;
        bodyDef.position.set(bodyX, bodyY);
        return bodyDef;
    }

    private static void createPolygonShapeAndFixtureDef(Body body, float width, float height, boolean isSensor) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width/2, height/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = isSensor;
        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }
}
