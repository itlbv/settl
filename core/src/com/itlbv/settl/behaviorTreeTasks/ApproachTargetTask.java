package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.mobs.Mob;

public class ApproachTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        //System.out.println("Approaching target");
        Mob owner = getObject();
        if (owner.isTargetWithinReach()) {
            System.out.println("Stop moving");
            owner.stopMoving();
            owner.setState(MobState.IDLE);
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
