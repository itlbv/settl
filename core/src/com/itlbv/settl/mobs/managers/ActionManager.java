package com.itlbv.settl.mobs.managers;

import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.utils.ActionState;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;

public class ActionManager {
    private final Mob owner;
    private ActionState actionState;
    public ActionManager(Mob owner) {
        this.owner = owner;
        this.actionState = ActionState.IDLE;
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

    public void initFighting() {
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
                attack();
            }
        }
    }

    private void attack() {
        owner.setState(MobState.ATTACK);
        //System.out.println(owner.getClass().getSimpleName() + " ATTACK " + Game.RENDER_ITERATION);
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
        //System.out.println(owner.getClass().getSimpleName() + " ON HOLD " + Game.RENDER_ITERATION);
        if (onHoldTimeCount > HOLD_TIME) {
            owner.setState(MobState.GOT_HIT);
            owner.minusHitpoint();
            //System.out.println(owner.getClass().getSimpleName() + " GOT HIT " + Game.RENDER_ITERATION);
            actionState = ActionState.IN_FIGHT;
            fightingTimeCount = 0;
            getTarget().setFightingTimeCountToZero();
        }
    }

    private Mob getTarget() {
        return (Mob) owner.getTarget();
    }
}
