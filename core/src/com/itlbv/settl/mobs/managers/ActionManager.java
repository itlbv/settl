package com.itlbv.settl.mobs.managers;

import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;

public class ActionManager {
    private final Mob owner;

    public ActionManager(Mob owner) {
        this.owner = owner;
    }

    public void fight() {
        if (owner.getState() != MobState.FIGHT) {
            return;
        }
    }
}
