package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class StartFightingTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        owner.startFighting();
        System.out.println(owner.getClass().getSimpleName() + " starting fight");
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
