package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.Game;
import com.itlbv.settl.MobState;

public class AnimationManager {
    private final Mob owner;
    private MobState currentState;
    private float animationDurationTime;
    private Animation<TextureRegion> currentAnimation;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> walking;
    private Animation<TextureRegion> fighting;
    private Animation<TextureRegion> gotHit;
    private Animation<TextureRegion> dead;

    public AnimationManager(Mob owner) {
        this.owner = owner;
        initializeTextures();
        this.currentState = owner.getState();
        this.currentAnimation = idle;
        update();
    }

    public void update(){
        updateAnimationDurationTime();
        setNewAnimationIfStateChanged();
        TextureRegion texture = currentAnimation.getKeyFrame(animationDurationTime, true);

        if (currentState != MobState.WALKING) {
            if (currentAnimation.isAnimationFinished(animationDurationTime)) {
                owner.setState(MobState.IDLE);
                //System.out.println("****ANIMATION RESET*********");

            }
        }

        setFrameToDraw(texture);
    }

    private void setNewAnimationIfStateChanged() {
        if (currentState != owner.getState()) {
            currentState = owner.getState();
            animationDurationTime = 0;
            setNewAnimation();
        }
    }

    private void setNewAnimation() {
        switch (currentState) {
            case IDLE:
                currentAnimation = idle;
                break;
            case WALKING:
                currentAnimation = walking;
                break;
            case FIGHTING:
                currentAnimation = fighting;
                break;
            case GOT_HIT:
                currentAnimation = gotHit;
                break;
            case DEAD:
                currentAnimation = dead;
                break;
        default:
            currentAnimation = idle;
            break;
        }
    }

    private void updateAnimationDurationTime() {
        animationDurationTime += Game.DELTA_TIME;
    }

    private void setFrameToDraw(TextureRegion texture) {
        owner.setTexture(texture);
    }

    private void initializeTextures() {
        idle = MobAnimationHelper.getAnimation(owner.getType(), MobState.IDLE);
        walking = MobAnimationHelper.getAnimation(owner.getType(), MobState.WALKING);
        fighting = MobAnimationHelper.getAnimation(owner.getType(), MobState.FIGHTING);
        gotHit = MobAnimationHelper.getAnimation(owner.getType(), MobState.GOT_HIT);
        dead = MobAnimationHelper.getAnimation(owner.getType(), MobState.DEAD);
    }
}
