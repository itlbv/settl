package com.itlbv.settl.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;

public class Player extends GameObject {
    private Vector2 UP, DOWN, LEFT, RIGHT;
    private Vector2 linearVelocity;
    FrictionJoint joint;

    public Player(int x, int y) {
        UP = new Vector2(0, 0);
        DOWN = new Vector2(0, 0);
        LEFT = new Vector2(0, 0);
        RIGHT = new Vector2(0, 0);
        linearVelocity = new Vector2();

        setTexture(new TextureRegion(new Texture(Gdx.files.internal("white_dot.png"))));
        getSprite().setSize(1,1);
        createBodyAndSensor(x,y);

        //createFrictionJoint();
    }

    private void createBodyAndSensor(int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body body = Game.world.createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(.3f);
        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.shape = circleShape;
        bodyFixtureDef.isSensor = false;
        body.createFixture(bodyFixtureDef);
        body.setUserData(this);
        setBody(body);
        bodyFixtureDef.filter.categoryBits = CollisionBits.MOB_BODY;
        bodyFixtureDef.filter.maskBits = CollisionBits.MAP_TILE | CollisionBits.MOB_BODY | CollisionBits.MOB_SENSOR;

        Body sensor = Game.world.createBody(bodyDef);
        circleShape.setRadius(1f);
        FixtureDef sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = circleShape;
        sensorFixtureDef.isSensor = true;
        sensor.createFixture(sensorFixtureDef);
        sensor.setUserData(this);
        setSensor(sensor);
        sensorFixtureDef.filter.categoryBits = CollisionBits.MOB_SENSOR;
        sensorFixtureDef.filter.maskBits = CollisionBits.MOB_BODY;

        circleShape.dispose();
    }

    private void createFrictionJoint() {
        FrictionJointDef jointDef = new FrictionJointDef();
        jointDef.initialize(getBody(), Game.map.mapSensor, getBody().getPosition());
        jointDef.maxForce = 25;
        Game.world.createJoint(jointDef);
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            UP.set(0, 3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            DOWN.set(0, -3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            LEFT.set(-3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            RIGHT.set(3, 0);
        }


        if (!Gdx.input.isKeyPressed(Input.Keys.UP)) {
            UP.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            DOWN.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            LEFT.set(0, 0);
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            RIGHT.set(0, 0);
        }
        float x = UP.x + DOWN.x + LEFT.x + RIGHT.x;
        float y = UP.y + DOWN.y + LEFT.y + RIGHT.y;
        linearVelocity.set(x, y);
        getBody().setLinearVelocity(linearVelocity);
        getSensor().setLinearVelocity(linearVelocity);
        checkSensorAlignment();

        float xPos = getBody().getPosition().x - .5f;
        float yPos = getBody().getPosition().y - .5f;
        getSprite().setPosition(xPos, yPos);
    }

    private void checkSensorAlignment() {
        Vector2 bodyPosition = getBody().getPosition();
        Vector2 sensorPosition = getSensor().getPosition();
        if (bodyPosition.epsilonEquals(sensorPosition, MathUtils.FLOAT_ROUNDING_ERROR)) {
            return;
        }
        Vector2 vectorToBody = bodyPosition.sub(sensorPosition);
        getSensor().setLinearVelocity(getBody().getLinearVelocity().cpy().mulAdd(vectorToBody,10));
    }
}
