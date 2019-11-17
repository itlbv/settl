package com.itlbv.settl.mob.action;

import com.itlbv.settl.Target;
import com.itlbv.settl.mob.Mob;

public class Move extends Action {

    public Move(Mob owner, Target target) {
        super(owner, ActionType.MOVE, target);
    }

    @Override
    public void run() {
        owner.movement.move();
    }
}
