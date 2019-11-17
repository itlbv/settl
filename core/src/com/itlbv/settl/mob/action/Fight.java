package com.itlbv.settl.mob.action;

import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;

import static com.itlbv.settl.mob.action.util.ActionUtil.approachMobAndFight;
import static com.itlbv.settl.mob.action.util.ActionUtil.setDefend;
import static com.itlbv.settl.mob.animation.util.MobAnimationType.ATTACK;
import static com.itlbv.settl.mob.util.MobTargetUtil.getTargetMob;

public class Fight extends Action {

    private float fightingTimer;

    public Fight(Mob owner, Mob target) {
        super(owner, ActionType.FIGHT, target);
    }

    @Override
    public void run() {
        if (targetIsDead()) {
            return;
        }
        if (targetIsNotReached()) {
            approachMobAndFight(owner, getTargetMob(owner));
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
            owner.getAnimation().setIncoming(ATTACK);
            setDefend(getTargetMob(owner), owner);
            fightingTimer = 0;
        }
    }

    private boolean targetIsDead() {
        return !getTargetMob(owner).isAlive();
    }

    private boolean targetIsNotReached() {
        return !owner.isTargetReached();
    }
}
