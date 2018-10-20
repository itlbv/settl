package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.itlbv.settl.enumsObjectType.GameObjectType;
import com.itlbv.settl.util.BodyFactory;

public abstract class GameObject extends SteerableAdapter<Vector2> {
    private Vector2 renderPosition;
    private GameObjectType type;
    private TextureRegion texture;
    private float renderWidth, renderHeight;
    private float bodyWidth, bodyHeight;
    private Body body;
    private Body sensor;

    public GameObject(float x, float y, GameObjectType type, float renderWidth, float renderHeight) {
        this.renderPosition = new Vector2(x, y);
        this.type = type;
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
        //Gdx.app.log(this.toString(), "created");
    }

    public void updateRenderPosition() {
        float x = body.getPosition().x - renderWidth/2;
        float y = body.getPosition().y - bodyHeight/2;
        renderPosition.set(x, y);
    }

    public void draw() {
        Game.batch.draw(texture, renderPosition.x, renderPosition.y, renderWidth, renderHeight);
    }

    //***Getters & setters***

    public Body getBody() {
        return body;
    }

    public void setBody(Body body, float bodyWidth, float bodyHeight) {
        this.body = body;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
    }

    public Body getSensor() {
        return sensor;
    }

    public void setSensor(Body sensor) {
        this.sensor = sensor;
    }

    public Vector2 getRenderPosition() {
        return renderPosition;
    }

    public float getRenderWidth() {
        return renderWidth;
    }

    public float getRenderHeight() {
        return renderHeight;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public float getMaxLinearAcceleration() {
        return 100; //TODO magical number!
    }
}
