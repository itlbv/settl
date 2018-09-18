package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class FollowPathTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        System.out.println("Following path");
        Mob owner = getObject();
        if (owner.path.size() > 0) {
            owner.followPath();
            return Status.RUNNING;
        }
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
