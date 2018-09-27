package com.itlbv.settl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.itlbv.settl.enumsObjectType.GameObjectType;

public abstract class GameObject{
    private Vector2 position;
    public TextureRegion texture; //TODO remove public after testing drawing path
    private float width, height;
    private SteerableBody body;
    private Body sensor;
    private GameObjectType type;
    private Animation<TextureRegion> animation;
    private float animationDuration;

    public GameObject(float x, float y, GameObjectType type, TextureRegion texture, float width, float height) {
        this.position = new Vector2(x, y);
        this.type = type;
        this.texture = texture;
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
        sensor = BodyFabric.createBody(sensorWidth, sensorHeight, body.getType(), this, true);
    }

    public void updatePosition() {
        getBody().updateOwnersPosition();
    }

    /*
    **Drawing section
     */
    public void draw() {
        if (animation == null) {
            drawTexture();
        } else {
            drawAnimation();
        }
    }

    public void setAnimationDurationToZero() {
        animationDuration = 0f;
    }

    private void updateAnimationDuration() {
        animationDuration += Game.DELTA_TIME;
    }

    private void drawAnimation() {
        updateAnimationDuration();
        texture = animation.getKeyFrame(animationDuration, true);
        drawTexture();
    }

    private void drawTexture() {
        //TODO needs better solution with mirroring the animation
        float textureWidth = width;
        float posX = position.x;
        if (animation != null) {
            if (getBody().getLinearVelocity().x < 0) {
                textureWidth *= -1;
                posX += width;
            }
        }
        Game.batch.draw(texture, posX, position.y, textureWidth, height);
    }

    //***Getters & setters***
    public SteerableBody getBody() {
        return body;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
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
}
