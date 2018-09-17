package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.enumsStateMachine.MobState;

public class MobTextureHelper {
    private static final float ANIMATION_SPEED = 0.1f;
    private static final int TEXTURE_SIZE = 24;

    private static Texture man01 = new Texture("textures/mobs/man01.png");
    private static Texture knight01 = new Texture("textures/mobs/knight01.png");
    private static Texture knight02 = new Texture("textures/mobs/knight02.png");
    private static Texture orcShaman01 = new Texture("textures/mobs/orcShaman01.png");
    private static Texture orcShaman02 = new Texture("textures/mobs/orcShaman02.png");


    public static TextureRegion getTexture(MobObjectType type, MobState state) {
        Texture textureOfType = getTextureOfType(type);
        return getTextureOfState(textureOfType, state);
    }

    private static Texture getTextureOfType(MobObjectType type) {
        switch (type) {
            case HUMAN_PEASANT:
                return man01;
            case HUMAN_KNIGHT:
                return knight01;
            case ORC_SHAMAN:
                return orcShaman01;
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
        return new TextureRegion(textureOfType,TEXTURE_SIZE, TEXTURE_SIZE);
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
        TextureRegion[] allFrames = TextureRegion.split(textureOfType, TEXTURE_SIZE,TEXTURE_SIZE)[0];
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
