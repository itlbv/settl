package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.itlbv.settl.enumsObjectType.GameObjectType;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobConstants;
import com.itlbv.settl.util.BodyFactory;

import static com.itlbv.settl.GameConstants.MOB_TEXTURE_SIZE_PXL;

public abstract class GameObject extends SteerableAdapter<Vector2> {
    //private Vector2 renderPosition;
    private Body body;
    private Body sensor;
    private Sprite sprite;
    private GameObjectType type;

    public GameObject(GameObjectType type) {
        this.type = type;
        //this.renderPosition = new Vector2();
        sprite = new Sprite();
    }

    public void updateRenderPosition() {
        /*
        float x = body.getPosition().x - renderWidth/2;
        float y = body.getPosition().y - MobConstants.MOB_BODY_RADIUS;
        renderPosition.set(x, y);
        */
        // TODO make check if this is Mob
        float x = body.getPosition().x - sprite.getWidth()/2;
        float y = body.getPosition().y - MobConstants.MOB_BODY_RADIUS;
        sprite.setPosition(x, y);
    }

    //***Getters & setters***

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getSensor() {
        return sensor;
    }

    public void setSensor(Body sensor) {
        this.sensor = sensor;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setTexture(TextureRegion texture) {
        sprite.setRegion(texture, 0, 0, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL);
    }

    public float getMaxLinearAcceleration() {
        return 100; //TODO magical number!
    }
}
