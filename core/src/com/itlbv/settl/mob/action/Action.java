package com.itlbv.settl.mob.action;

import com.itlbv.settl.mob.action.util.ActionType;
import com.itlbv.settl.mob.movement.util.Target;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Action {
    private ActionType type;
    private Target target;
}
