package com.itlbv.settl.behavior.conditions;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.ActionUtil;

public class IsEnemyAround extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();

        // already has a target
        if (owner.getTarget() != null)
            return Status.SUCCEEDED;

        // find a mob of different type
        for (Mob mob : Game.mobs) {
            if (mob.getType() != owner.getType())
                return Status.SUCCEEDED;
        }

        // remove all actions if all enemies are killed
        ActionUtil.clearActions(owner);
        return Status.FAILED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}