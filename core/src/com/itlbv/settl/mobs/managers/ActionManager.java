package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.utils.ActionState;
import com.itlbv.settl.mobs.utils.MobAnimationState;
import com.itlbv.settl.mobs.Mob;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActionManager {
    private final Mob owner;
    private ActionState actionState;
    public ActionManager(Mob owner) {
        this.owner = owner;
        this.actionState = ActionState.READY_TO_ATTACK;
    }
    public void update() {
        switch (actionState) {
            case IN_FIGHT:
                fight();
                break;
            case ON_HOLD:
                hold();
                break;
        }
    }

    public void startFighting() {
        actionState = ActionState.IN_FIGHT;
        fight();
    }


    public float fightingTimeCount = 0f;
    private final float ATTACK_FREQ = 1f;
    private void fight() {
        fightingTimeCount += Game.DELTA_TIME;
        if (fightingTimeCount > ATTACK_FREQ) {
            if (MathUtils.randomBoolean(.3f)) {
                fightingTimeCount = 0;
                attack(); //TODO crashes when target is dead and is placed higher up in list of mobs
            }
        }
    }

    private void attack() {
        owner.setState(MobAnimationState.ATTACK);
        getTarget().defend();
    }

    private float onHoldTimeCount;
    private final float HOLD_TIME = 0.3f;
    public void defend() {
        actionState = ActionState.ON_HOLD;
        onHoldTimeCount = 0;
        hold();
    }

    private void hold() {
        onHoldTimeCount += Game.DELTA_TIME;
        if (onHoldTimeCount > HOLD_TIME) {
            owner.setState(MobAnimationState.GOT_HIT);
            minusHitpoint();
            actionState = ActionState.IN_FIGHT;
            fightingTimeCount = 0;
            getTarget().setFightingTimeCountToZero();
        }
    }

    private int hitpoints = 2;
    private void minusHitpoint() {
        hitpoints--;
        if (hitpoints == 0) {
            owner.setAlive(false);
        }
    }

    private final float enemyCheckFreq = 1f;
    private float enemyCheckTimeCount = 0f;
    public void chooseEnemy() {
        if (getTarget() == null) chooseClosestEnemy();
        enemyCheckTimeCount += Game.DELTA_TIME;
        if (enemyCheckTimeCount > enemyCheckFreq){
            enemyCheckTimeCount = 0;
            chooseClosestEnemy();
        }
    }
    private void chooseClosestEnemy() {
        List<Mob> potentialTargets = Game.mobs.stream()
                .filter(mob -> mob.getType() != owner.getType())
                .collect(Collectors.toList());
        if (potentialTargets.size() == 0) return;
        Mob target = potentialTargets.stream()
                .min(Comparator.comparing(mob -> mob.getPosition().dst(owner.getPosition())))
                .get();
        owner.setTarget(target);
    }

    private Mob getTarget() {
        return (Mob) owner.getTarget();
    }
}
