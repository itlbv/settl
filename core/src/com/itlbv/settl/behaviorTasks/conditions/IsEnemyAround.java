package com.itlbv.settl.behaviorTasks.conditions;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.util.ActionUtil;

public class IsEnemyAround extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        if (owner.getTarget() != null) return Status.SUCCEEDED;
        for (Mob mob : Game.mobs) {
            if (mob.getType() != owner.getType()) return Status.SUCCEEDED;
        }
        ActionUtil.clearActions(owner);
        return Status.FAILED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}