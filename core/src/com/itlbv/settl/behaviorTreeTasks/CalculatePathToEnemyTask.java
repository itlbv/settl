package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class CalculatePathToEnemyTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        System.out.println("Calculating path to Enemy");
        Mob owner = getObject();
        owner.setTarget(owner.target);
        if (owner.path.size() > 0) {
            return Status.SUCCEEDED;
        }
        return Status.FAILED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
