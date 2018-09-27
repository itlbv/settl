package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.MobState;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.MovementManager;

public class ApproachTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        System.out.println("Approaching target");
        Mob owner = getObject();
        MovementManager movementHandler = owner.getMovementHandler();
        if (owner.isTargetWithinReach()) {
            movementHandler.stop();
            return Status.SUCCEEDED;
        }
        movementHandler.update();
        owner.setState(MobState.WALKING);
        if (owner.isTargetWithinReach()) {
            movementHandler.stop();
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
