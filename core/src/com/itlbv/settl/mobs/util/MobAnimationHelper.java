package com.itlbv.settl.mobs.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobType;

import static com.itlbv.settl.GameConstants.MOB_TEXTURE_SIZE_PXL;

public class MobAnimationHelper {
    private static final float ANIMATION_SPEED = 0.1f;

    private static Texture man01 = new Texture("textures/mobs/man01.png");
    private static Texture knight01 = new Texture("textures/mobs/knight01.png");
    private static Texture knight02 = new Texture("textures/mobs/knight02.png");
    private static Texture orcShaman01 = new Texture("textures/mobs/orcShaman01.png");
    private static Texture orcShaman02 = new Texture("textures/mobs/orcShaman02.png");

    public static Animation<TextureRegion> getAnimation(MobType type, MobAnimationState state) {
        Texture textureOfType = getTextureOfType(type);
        return getAnimationOfState(textureOfType, state);
    }

    private static Texture getTextureOfType(MobType type) {
        switch (type) {
            case PEASANT:
                return man01;
            case KNIGHT:
                return knight01;
            case ORC:
                return orcShaman01;
            default:
                return man01;
        }
    }

    private static Animation<TextureRegion> getAnimationOfState(Texture textureOfType, MobAnimationState state) {
        switch (state) {
            case IDLE:
                return getIdle(textureOfType);
            case WALK:
                return getWalking(textureOfType);
            case ATTACK:
                return getAttack(textureOfType);
            case GOT_HIT:
                return getGotHit(textureOfType);
            case DEAD:
                return getDead(textureOfType);
            default:
                return getWalking(textureOfType);
        }
    }

    private static Animation<TextureRegion> getIdle(Texture textureOfType) {
        TextureRegion[] allFrames = TextureRegion.split(textureOfType, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL)[0];
        TextureRegion[] idleFrames = {allFrames[0]};
        return new Animation<>(ANIMATION_SPEED, idleFrames);
    }

    private static Animation<TextureRegion> getWalking(Texture textureOfType) {
        TextureRegion[] allFrames = TextureRegion.split(textureOfType, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL)[0];
        TextureRegion[] walkFrames = {allFrames[0],
                allFrames[1],
                allFrames[0],
                allFrames[2]};
        return new Animation<>(ANIMATION_SPEED, walkFrames);
    }

    private static Animation<TextureRegion> getAttack(Texture textureOfType) {
        TextureRegion[] allFrames = TextureRegion.split(textureOfType, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL)[0];
        TextureRegion[] attackFrames = {allFrames[3],
                allFrames[4],
                allFrames[5]};
        return new Animation<>(ANIMATION_SPEED, attackFrames);
    }

    private static Animation<TextureRegion> getGotHit(Texture textureOfType) {
        TextureRegion[] allFrames = TextureRegion.split(textureOfType, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL)[0];
        TextureRegion[] gotHit = {allFrames[6],
                allFrames[7]};
        return new Animation<>(ANIMATION_SPEED, gotHit);
    }

    private static Animation<TextureRegion> getDead(Texture textureOfType) {
        TextureRegion[] allFrames = TextureRegion.split(textureOfType, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL)[0];
        TextureRegion[] deadFrames = {allFrames[8]};
        return new Animation<>(ANIMATION_SPEED, deadFrames);
    }
}
