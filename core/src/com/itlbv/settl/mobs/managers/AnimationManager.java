package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.utils.MobAnimationHelper;

public class AnimationManager {
    private final Mob owner;
    private MobState currentState;
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
        TextureRegion texture = currentAnimation.getKeyFrame(animationTime, currentState.isLooping());
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
        if (currentAnimation.isAnimationFinished(animationTime)) {
            owner.setState(MobState.IDLE);
        }
    }

    private void initializeTextures() {
        idle = MobAnimationHelper.getAnimation(owner.getType(), MobState.IDLE);
        walking = MobAnimationHelper.getAnimation(owner.getType(), MobState.WALK);
        attack = MobAnimationHelper.getAnimation(owner.getType(), MobState.ATTACK);
        gotHit = MobAnimationHelper.getAnimation(owner.getType(), MobState.GOT_HIT);
        dead = MobAnimationHelper.getAnimation(owner.getType(), MobState.DEAD);
    }
}
