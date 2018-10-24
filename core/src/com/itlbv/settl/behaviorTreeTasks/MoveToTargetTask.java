package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class MoveToTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        if (owner.isTargetWithinReach()) {
            owner.stopMoving();
            return Status.SUCCEEDED;
        } else {
            owner.move();
            return Status.RUNNING;
        }

    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
