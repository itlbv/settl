package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.utils.MobAnimationState;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobAnimationHelper;

public class AnimationManager {
    private final Mob owner;
    private MobAnimationState currentState;
    private float animationTime;
    private Animation<TextureRegion> currentAnimation;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> walking;
    private Animation<TextureRegion> attack;
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
        updateAnimationTime();
        setNewAnimationIfStateChanged();
        TextureRegion texture = currentAnimation.getKeyFrame(animationTime, true);
        setFrameToDraw(texture);
        resetAnimationIfFinished();
    }

    private void updateAnimationTime() {
        animationTime += Game.DELTA_TIME;
    }

    private void setNewAnimationIfStateChanged() {
        if (currentState != owner.getState()) {
            currentState = owner.getState();
            animationTime = 0;
            setNewAnimation();
        }
    }

    private void setNewAnimation() {
        switch (currentState) {
            case IDLE:
                currentAnimation = idle;
                break;
            case WALK:
                currentAnimation = walking;
                break;
            case ATTACK:
                currentAnimation = attack;
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

    private void setFrameToDraw(TextureRegion texture) {
        owner.setTexture(texture);
    }

    private void resetAnimationIfFinished() {
        if (currentState.isOneTime() &&
                currentAnimation.isAnimationFinished(animationTime)) {
            owner.setState(MobAnimationState.IDLE);
        }
    }

    private void initializeTextures() {
        idle = MobAnimationHelper.getAnimation(owner.getType(), MobAnimationState.IDLE);
        walking = MobAnimationHelper.getAnimation(owner.getType(), MobAnimationState.WALK);
        attack = MobAnimationHelper.getAnimation(owner.getType(), MobAnimationState.ATTACK);
        gotHit = MobAnimationHelper.getAnimation(owner.getType(), MobAnimationState.GOT_HIT);
        dead = MobAnimationHelper.getAnimation(owner.getType(), MobAnimationState.DEAD);
    }
}
