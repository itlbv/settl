package com.itlbv.settl.mob.animation.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.mob.util.MobType;

import static com.itlbv.settl.util.GameConstants.MOB_TEXTURE_SIZE_PXL;

public class AnimationUtil {
    private static final float ANIMATION_SPEED = 0.1f; //Time between frames

    private static Texture man01 = new Texture("textures/mobs/man01.png");
    private static Texture knight01 = new Texture("textures/mobs/knight01.png");
    private static Texture orcShaman01 = new Texture("textures/mobs/orcShaman01.png");

    public static Animation<TextureRegion> getAnimation(MobType mobType, MobAnimationType animationType) {
        Texture textureOfType = getTextureOfType(mobType);
        return getAnimationOfAction(textureOfType, animationType);
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

    private static Animation<TextureRegion> getAnimationOfAction(Texture textureOfType, MobAnimationType animationType) {
        switch (animationType) {
            case IDLE:
                return getIdle(textureOfType);
            case WALK:
                return getWalk(textureOfType);
            case ATTACK:
                return getAttack(textureOfType);
            case GET_HIT:
                return getGetHit(textureOfType);
            case DEAD:
                return getDead(textureOfType);
            default:
                return getIdle(textureOfType);
        }
    }

    private static Animation<TextureRegion> getIdle(Texture textureOfType) {
        TextureRegion[] allFrames = getFramesFromTexture(textureOfType);
        TextureRegion[] idleFrames = {allFrames[0]};
        return new Animation<>(ANIMATION_SPEED, idleFrames);
    }

    private static Animation<TextureRegion> getWalk(Texture textureOfType) {
        TextureRegion[] allFrames = getFramesFromTexture(textureOfType);
        TextureRegion[] walkFrames = {allFrames[0],
                allFrames[1],
                allFrames[0],
                allFrames[2]};
        return new Animation<>(ANIMATION_SPEED, walkFrames);
    }

    private static Animation<TextureRegion> getAttack(Texture textureOfType) {
        TextureRegion[] allFrames = getFramesFromTexture(textureOfType);
        TextureRegion[] attackFrames = {allFrames[3],
                allFrames[4],
                allFrames[5]};
        return new Animation<>(ANIMATION_SPEED, attackFrames);
    }

    private static Animation<TextureRegion> getGetHit(Texture textureOfType) {
        TextureRegion[] allFrames = getFramesFromTexture(textureOfType);
        TextureRegion[] gotHitFrames = {allFrames[6],
                allFrames[7]};
        return new Animation<>(ANIMATION_SPEED, gotHitFrames);
    }

    private static Animation<TextureRegion> getDead(Texture textureOfType) {
        TextureRegion[] allFrames = getFramesFromTexture(textureOfType);
        TextureRegion[] deadFrames = {allFrames[8]};
        return new Animation<>(ANIMATION_SPEED, deadFrames);
    }

    private static TextureRegion[] getFramesFromTexture(Texture texture) {
        return TextureRegion.split(texture, MOB_TEXTURE_SIZE_PXL, MOB_TEXTURE_SIZE_PXL)[0];
    }
}
