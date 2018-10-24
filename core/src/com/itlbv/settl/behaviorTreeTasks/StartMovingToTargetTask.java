package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class StartMovingToTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        getObject().startMoving();
        return Status.SUCCEEDED;
}

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
