package com.itlbv.settl;

import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.itlbv.settl.mob.movement.util.Target;
import com.itlbv.settl.mob.util.MobConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.itlbv.settl.util.GameConstants.MOB_TEXTURE_SIZE_PXL;

/**
 * GameObject extends SteerableAdapter in order to be a target for Steering Behavior
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class GameObject extends Target {

    private Body body;
    private Body sensor;
    private Sprite sprite;

    protected GameObject() {
        this.sprite = new Sprite();
    }

   public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setTexture(TextureRegion texture) {
        sprite.setRegion(texture, 0, 0, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL);
    }
}
