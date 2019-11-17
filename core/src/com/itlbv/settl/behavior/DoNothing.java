package com.itlbv.settl.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mob.Mob;

public class DoNothing extends LeafTask<Mob> {
    @Override
    public Status execute() {
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
