package com.itlbv.settl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.itlbv.settl.enumsObjectType.GameObjectType;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public abstract class GameObject{
    private Vector2 position;
    private GameObjectType type;
    public TextureRegion texture; //TODO remove public after testing drawing path
    private float width, height;
    private SteerableBody body;
    private Body sensor;

    public GameObject(float x, float y, GameObjectType type, float width, float height) {
        this.position = new Vector2(x, y);
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public void createBody(BodyType bodyType, float bodyWidth, float bodyHeight) {
        body = new SteerableBody(bodyType, bodyWidth, bodyHeight, this);
    }

    public void createSensor(float sensorWidth, float sensorHeight) {
        if (body == null) {
            return; //TODO do smth with it
        }
        sensor = BodyFactory.createBody(sensorWidth, sensorHeight, body.getType(), this, true);
    }

    public void updatePosition() {
        getBody().updateOwnersPosition();
    }

    public void draw() {
        Game.batch.draw(texture, position.x, position.y, width, height);
    }

    //***Getters & setters***
    public SteerableBody getBody() {
        return body;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getBodyPosition() {
        return getBody().getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Body getSensor() {
        return sensor;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}
