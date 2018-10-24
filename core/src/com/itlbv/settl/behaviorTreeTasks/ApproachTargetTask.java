package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.managers.TaskManager;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;

public class ApproachTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        if (owner.isTargetWithinReach()) {
            owner.getTaskManager().stopMoving();
            return Status.SUCCEEDED;
        } else {
            return Status.RUNNING;
        }
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
