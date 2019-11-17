package com.itlbv.settl.mob.action;

import com.itlbv.settl.Target;
import com.itlbv.settl.mob.Mob;
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

    public String toString() {
        return type.name();
    }
}
