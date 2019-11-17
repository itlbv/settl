package com.itlbv.settl;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.itlbv.settl.util.GameConstants.MOB_TEXTURE_SIZE_PXL;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class GameObject extends Target {

    private static final Logger log = LoggerFactory.getLogger(GameObject.class);

    private Body body;
    private Body sensor;
    private Sprite sprite;

    protected GameObject() {
        this.sprite = new Sprite();
    }

    public void setTexture(TextureRegion texture) {
        sprite.setRegion(texture, 0, 0, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Vector2 getSensorPosition() {
        try {
            return getSensor().getPosition();
        } catch (NullPointerException e) {
            log.error(this.toString() + " has no sensor!");
            return null;
        }
    }
}
