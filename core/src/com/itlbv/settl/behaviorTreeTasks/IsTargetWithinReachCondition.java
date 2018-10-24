package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class IsTargetWithinReachCondition extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        if (owner.isTargetWithinReach()) return Status.SUCCEEDED;
        else return Status.FAILED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
