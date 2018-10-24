package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class StartFightingTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        getObject().getTaskManager().initFightingEnemy();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
