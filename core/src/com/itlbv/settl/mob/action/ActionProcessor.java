package com.itlbv.settl.mob.action;

import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.animation.util.MobAnimationType;
import com.itlbv.settl.mob.movement.Movement;

import java.util.Objects;

import static com.itlbv.settl.mob.action.util.ActionUtil.*;
import static com.itlbv.settl.mob.animation.util.MobAnimationType.ATTACK;
import static com.itlbv.settl.mob.animation.util.MobAnimationType.GET_HIT;

public class ActionProcessor {
    private final Mob owner;
    private Movement movement;

    public ActionProcessor(Mob owner) {
        this.owner = owner;
        this.movement = new Movement(owner);
    }

    public void update() {
        if (owner.hasNoActions()) {
            return;
        }
        setOwnersTarget();
        switch (Objects.requireNonNull(getTypeOfCurrentAction(owner))) {
            case MOVE:
                movement.move();
                break;
            case FIGHT:
                fight();
                break;
            case DEFEND:
                defend();
                break;
        }
    }

    private float fightingTimer;
    private void fight() {
        if (targetIsDead()) {
            return;
        }
        if (targetIsNotReached()) {
            setApproachAndFight(owner, getTargetMob());
            return;
        }
        if (fightingTimer > 1) {
            attack();
        } else {
            fightingTimer += Game.DELTA_TIME;
        }
    }

    private void attack() {
        if (MathUtils.randomBoolean(.1f)) {
            setIncomingAnimation(ATTACK);
            setDefend(getTargetMob(), owner);
            fightingTimer = 0;
        }
    }

    private float holdTimer;
    private void defend() {
        holdTimer += Game.DELTA_TIME;
        if (holdTimer < 0.3f) {
            return;
        }
        holdTimer = 0;

        if (MathUtils.randomBoolean(.5f)) {
            //System.out.println(owner.toString() + " GET HIT");
            setIncomingAnimation(GET_HIT);
            removeCurrentAction(owner);
            minusHitpoint();
        } else {
            //System.out.println(owner.toString() + " DEFEND");
            removeCurrentAction(owner);
        }
    }

    private void minusHitpoint() {
        owner.setHitpoints(owner.getHitpoints() - 1);
        System.out.println(owner.toString() + " hp:" + owner.getHitpoints());
        checkHealthAndDie();
    }

    private void checkHealthAndDie() {
        if (owner.getHitpoints() > 0) {
            return;
        }
        owner.setAlive(false);
        owner.getBody().destroyFixture(owner.getBody().getFixtureList().get(0));
        owner.getSensor().destroyFixture(owner.getSensor().getFixtureList().get(0));
    }

    private void setIncomingAnimation(MobAnimationType animationType) {
        owner.getAnimationProcessor().setIncoming(animationType);
    }

    private void setOwnersTarget() {
        owner.setTarget(Objects.requireNonNull(getCurrentAction(owner)).getTarget());
    }

    private boolean targetIsNotReached() {
        return !owner.isTargetReached();
    }

    private boolean targetIsDead() {
        return !getTargetMob().isAlive();
    }

    private Mob getTargetMob() {
        /*
        TODO check if target is MOB
          If owner is under attack while running and its target is not a Mob
          (Mob)owner.getTarget() causes exception
         */
        return (Mob) owner.getTarget();
    }
}
