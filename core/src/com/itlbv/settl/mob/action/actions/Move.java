package com.itlbv.settl.mob.action.actions;

import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.Action;
import com.itlbv.settl.mob.movement.util.Target;

public class Move extends Action {

    public Move(Mob owner, Target target) {
        super(owner, ActionType.MOVE, target);
    }

    @Override
    public void run() {
        owner.movement.move();
    }
}
