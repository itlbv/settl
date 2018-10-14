package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;

public class InitializeMovingToTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        owner.initMovingToTarget();
        System.out.println(owner.getClass().getSimpleName() + " initializing movement to target");
        owner.setState(MobState.WALK); //TODO remove and test immediately
        return Status.SUCCEEDED;
}

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
