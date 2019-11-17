package com.itlbv.settl.mob.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.animation.util.MobAnimationType;
import com.itlbv.settl.mob.util.MobConstants;
import com.itlbv.settl.mob.util.MobType;

import static com.itlbv.settl.mob.action.Action.ActionType.MOVE;
import static com.itlbv.settl.mob.animation.util.MobAnimationType.*;

public class AnimationProcessor {
    private final Mob owner;

    private MobAnimation idle;
    private MobAnimation walk;
    private MobAnimation attack;
    private MobAnimation getHit;
    private MobAnimation dead;

    private MobAnimation animation;
    private MobAnimation incoming;

    private float timer;

    public AnimationProcessor(Mob owner) {
        this.owner = owner;
        initializeTextures(owner.getType());
    }

    public void update() {
        if (!owner.isAlive()) {
            drawDead();
            return;
        }
        updateTimer();
        process();
        setFrameToDraw();
        updateSpritePosition();
    }

    private void process() {
        if (incoming != null) {
            processIncoming();
        } else {
            processUsual();
        }
    }

    private void processIncoming() {
        setAnimationIfChanged(incoming);
        resetIncomingIfFinished();
    }

    private void processUsual() {
        if (isCurrentMobActionMove()) {
            setAnimationIfChanged(walk);
        } else {
            setAnimationIfChanged(idle);
        }
    }

    private boolean isCurrentMobActionMove() {
        return owner.getActionType() == MOVE;
    }

    private void setAnimationIfChanged(MobAnimation animationToSet) {
        if (animation != animationToSet) {
            animation = animationToSet;
            setTimerToZero();
        }
    }

    private void resetIncomingIfFinished() {
        if (animation.getAnimation().isAnimationFinished(timer)) {
            incoming = null;
            animation = idle;
        }
    }

    public void setIncoming(MobAnimationType type) {
        switch (type) {
            case ATTACK:
                incoming = attack;
                break;
            case GET_HIT:
                incoming = getHit;
                break;
            default:
                incoming = idle;
                break;
        }
    }

    private void updateTimer() {
        timer += Game.DELTA_TIME;
    }

    private void setTimerToZero() {
        timer = 0;
    }

    private void updateSpritePosition() {
        float x = owner.getBody().getPosition().x - owner.getSprite().getWidth()/2;
        float y = owner.getBody().getPosition().y - MobConstants.MOB_BODY_RADIUS;
        owner.getSprite().setPosition(x, y);
    }

    private void setFrameToDraw() {
        TextureRegion texture = animation.getAnimation().getKeyFrame(timer, true);
        owner.setTexture(texture);
    }

    private void initializeTextures(MobType type) {
        idle = new MobAnimation(type, IDLE);
        walk = new MobAnimation(type, WALK);
        attack = new MobAnimation(type, ATTACK);
        getHit = new MobAnimation(type, GET_HIT);
        dead = new MobAnimation(type, DEAD);
    }

    private void drawDead() {
        owner.setTexture(dead.getAnimation().getKeyFrames()[0]);
    }
}
