package com.itlbv.settl.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mob.Mob;

public class ChooseEnemy extends LeafTask<Mob> {
    @Override
    public Status execute() {
        return Status.SUCCEEDED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}