package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.itlbv.settl.enumsObjectType.GameObjectType;

public abstract class GameObject{
    private Vector2 position;
    public TextureRegion texture; //TODO remove public after testing drawing path
    private float width, height;
    private SteerableBody body;
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
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        float bodyX = this.position.x + width/2;
        float bodyY = this.position.y + bodyHeight/2;
        bodyDef.position.set(bodyX, bodyY);
        body = new SteerableBody(bodyDef, this);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyWidth/2, bodyHeight/2);
        fixtureDef.shape = polygonShape;

        body.createFixture(fixtureDef);
        getBody().body.setUserData(bodyHeight); //TODO done for updatePosition method. should be deleted
        polygonShape.dispose();
    }

    public void updatePosition() { //TODO redo this mess
        Vector2 bodyPosition = getBody().getPosition();
        float x = bodyPosition.x - width/2;
        float y = bodyPosition.y - Float.parseFloat(getBody().body.getUserData().toString())/2;
        position.set(x, y);
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
        animationDuration += Gdx.graphics.getDeltaTime(); //TODO refactoring of delta time
    }

    private void drawAnimation() {
        updateAnimationDuration();
        texture = animation.getKeyFrame(animationDuration, true);
        drawTexture();
    }

    private void drawTexture() {
        Game.getBatch().draw(texture, position.x, position.y, width, height);
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
}
