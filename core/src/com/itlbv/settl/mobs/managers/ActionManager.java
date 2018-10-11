package com.itlbv.settl.mobs.managers;

import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;

public class ActionManager {
    private final Mob owner;
    private final float ATTACK_FREQ = 1f;
    private float timeCount = 2f;

    public ActionManager(Mob owner) {
        this.owner = owner;
    }

    public void fight() {
        if (owner.getState() == MobState.ATTACK) {
            return;
        }
        Mob target = (Mob) owner.getTarget();
        timeCount += Game.DELTA_TIME;
        if (timeCount > ATTACK_FREQ) {
            attack(target);
            timeCount = 0;
        }
    }

    private void attack(Mob target) {
        owner.setState(MobState.ATTACK);
        //target.defend();
    }

    public void defend() {
        owner.setState(MobState.GOT_HIT);
    }
}
