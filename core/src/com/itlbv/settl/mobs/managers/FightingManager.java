package com.itlbv.settl.mobs.managers;

import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;

public class FightingManager {
    private final Mob owner;
    private Mob enemy;
    public FightingManager(Mob owner) {
        this.owner = owner;
    }

    public void fight() {
        if (owner.getState() != MobState.FIGHT) {
            return;
        }
    }
    /*
    private float combatPhaseTime = 0;
    public void fight() {
        combatPhaseTime += Game.DELTA_TIME;
        if (combatPhaseTime > 1f) {
            if (MathUtils.randomBoolean(.3f)) {
                attackEnemy();
                state = MobState.FIGHT;
                combatPhaseTime = 0f;
            }
        }
    }

    private void attackEnemy() {
        System.out.println("*********************ATTACK***************************");
        //enemy.defend();
    }

    public void defend() {
        if (MathUtils.randomBoolean(.5f)) {
            state = MobState.GOT_HIT;
        }
    }
     */
}
