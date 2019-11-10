package com.itlbv.settl.mob.action;

import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.movement.util.Target;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Action {
    protected Mob owner;
    private ActionType type;
    private Target target;

    public abstract void run();

    public enum ActionType {
        MOVE,
        FIGHT,
        DEFEND
    }
}
