package com.itlbv.settl;

import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.itlbv.settl.mobs.util.MobConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.itlbv.settl.GameConstants.MOB_TEXTURE_SIZE_PXL;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class GameObject extends SteerableAdapter<Vector2> {

    private Body body;
    private Body sensor;
    private Sprite sprite;

    protected GameObject() {
        sprite = new Sprite();
    }

    public void updateSpritePosition() {
        // TODO make check if this is Mob
        float x = body.getPosition().x - sprite.getWidth()/2;
        float y = body.getPosition().y - MobConstants.MOB_BODY_RADIUS;
        sprite.setPosition(x, y);
    }

   public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setTexture(TextureRegion texture) {
        sprite.setRegion(texture, 0, 0, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL);
    }

    public float getMaxLinearAcceleration() {
        return 100; //TODO magical number! REMOVE PARAMETER FORM HERE
    }
}
