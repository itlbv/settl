package com.itlbv.settl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enums.MobObjectType;
import com.itlbv.settl.enumsStateMachine.MobState;

public class MobTextureHelper {
    private static final float ANIMATION_SPEED = 0.1f;

    private static Texture man01 = new Texture("textures/mobs/human/man01.png");

    public static TextureRegion getTexture(MobObjectType type, MobState state) {
        Texture textureOfType = getTextureOfType(type);
        return getTextureOfState(textureOfType, state);
    }

    private static Texture getTextureOfType(MobObjectType type) {
        switch (type) {
            case HUMAN:
                return man01;
            case ORC:
                return man01;
            default:
                return man01;
        }
    }

    private static TextureRegion getTextureOfState(Texture textureOfType, MobState state) {
        switch (state) {
            case IDLE:
                return getIdle(textureOfType);
            default:
                return getIdle(textureOfType);
        }
    }

    private static TextureRegion getIdle(Texture textureOfType) {
        return new TextureRegion(textureOfType,20, 20);
    }

    /*
    **Animation section
     */
    public static Animation<TextureRegion> getAnimation(MobObjectType type, MobState state) {
        Texture textureOfType = getTextureOfType(type);
        return getAnimationOfState(textureOfType, state);
    }

    private static Animation<TextureRegion> getAnimationOfState(Texture textureOfType, MobState state) {
        switch (state) {
            case APPROACHING:
                return getWalking(textureOfType);
            default:
                return getWalking(textureOfType);
        }
    }

    private static Animation<TextureRegion> getWalking(Texture textureOfType) {
        TextureRegion[] allFrames = TextureRegion.split(textureOfType, 20,20)[0];
        TextureRegion[] walkFrames = {allFrames[0],
                allFrames[1],
                allFrames[0],
                allFrames[2]};
        return new Animation<TextureRegion>(ANIMATION_SPEED, walkFrames);
        /*
        TextureRegion[] attackFrames = {allFrames[3],
                allFrames[4],
                allFrames[5]};
        TextureRegion[] gotHit = {allFrames[6],
                allFrames[7]};
        TextureRegion deadFrame = allFrames[8];
         */
    }
}
