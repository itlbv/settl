package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.Game;
import com.itlbv.settl.MobState;

public class AnimationManager {
    private Mob owner;
    private MobState currentState;
    private float animationDuration;
    private Animation<TextureRegion> animationToDraw;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> walking;
    private Animation<TextureRegion> fighting;
    private Animation<TextureRegion> gotHit;
    private Animation<TextureRegion> dead;

    public AnimationManager(Mob owner) {
        this.owner = owner;
        this.currentState = MobState.IDLE;
        initializeTextures();
        setAnimationToDraw();
        TextureRegion texture = animationToDraw.getKeyFrame(animationDuration, true);
        setTextureToDraw(texture);
    }

    public void updateAnimation(){
        updateAnimationDuration();
        if (currentState != owner.getState()) {
            currentState = owner.getState();
            animationDuration = 0;
            setAnimationToDraw();
        }
        TextureRegion texture = animationToDraw.getKeyFrame(animationDuration, true);
        setTextureToDraw(texture);
    }

    private void updateAnimationDuration() {
        animationDuration += Game.DELTA_TIME;
    }

    private void setAnimationToDraw() {
        switch (currentState) {
            case IDLE:
                animationToDraw = idle;
                break;
            case WALKING:
                animationToDraw = walking;
                break;
            case FIGHTING:
                animationToDraw = fighting;
                break;
            case GOT_HIT:
                animationToDraw = gotHit;
                break;
            case DEAD:
                animationToDraw = dead;
                break;
        default:
            animationToDraw = idle;
            break;
        }
    }

    private void setTextureToDraw(TextureRegion texture) {
        owner.setTexture(texture);
    }

    private void initializeTextures() {
        idle = MobTextureHelper.getAnimation(owner.getType(), MobState.IDLE);
        walking = MobTextureHelper.getAnimation(owner.getType(), MobState.WALKING);
        fighting = MobTextureHelper.getAnimation(owner.getType(), MobState.FIGHTING);
        gotHit = MobTextureHelper.getAnimation(owner.getType(), MobState.GOT_HIT);
        dead = MobTextureHelper.getAnimation(owner.getType(), MobState.DEAD);
    }
}
