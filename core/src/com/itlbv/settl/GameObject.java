package com.itlbv.settl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.itlbv.settl.enumsObjectType.GameObjectType;

public abstract class GameObject{
    private Vector2 renderPosition;
    private GameObjectType type;
    public TextureRegion texture; //TODO remove public after testing drawing path
    private float renderWidth, renderHeight;
    private SteerableBody body;
    private Body sensor;

    public GameObject(float x, float y, GameObjectType type, float renderWidth, float renderHeight) {
        this.renderPosition = new Vector2(x, y);
        this.type = type;
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
    }

    public void createBody(BodyType bodyType, float bodyWidth, float bodyHeight) {
        body = new SteerableBody(bodyType, bodyWidth, bodyHeight, this);
    }

    public void replaceSensor() {
        GameWorld.world.destroyBody(sensor);
        createSensor(2f,2f); //TODO take it from constants class
    }

    public void createSensor(float sensorWidth, float sensorHeight) {
        if (body == null) {
            return; //TODO do smth with it
        }
        sensor = BodyFactory.createBody(sensorWidth, sensorHeight, body.getType(), this, true);
    }

    public void updateRenderPosition() {
        getBody().updateOwnersPosition();
    }

    public void draw() {
        Game.batch.draw(texture, renderPosition.x, renderPosition.y, renderWidth, renderHeight);
    }

    //***Getters & setters***
    public SteerableBody getBody() {
        return body;
    }

    public float getRenderX() {
        return renderPosition.x;
    }

    public float getRenderY() {
        return renderPosition.y;
    }

    public Vector2 getRenderPosition() {
        return renderPosition;
    }

    public Vector2 getBodyPosition() {
        return getBody().getPosition();
    }

    public float getRenderWidth() {
        return renderWidth;
    }

    public float getRenderHeight() {
        return renderHeight;
    }

    public Body getSensor() {
        return sensor;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}
