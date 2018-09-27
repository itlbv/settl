package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class CalculatePathToTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        System.out.println("Calculating path to target");
        Mob owner = getObject();
        boolean result = owner.getMoveManager().calculatePathToTarget();
        return result ? Status.SUCCEEDED : Status.FAILED;

}

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
