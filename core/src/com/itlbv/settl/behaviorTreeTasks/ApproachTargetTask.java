package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.MoveManager;

public class ApproachTargetTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        System.out.println("Approaching target");
        Mob owner = getObject();
        MoveManager moveManager = owner.getMoveManager();
        moveManager.update();
        if (targetIsNear()) {
            moveManager.stop();
            return Status.SUCCEEDED;
        } else {
            return Status.RUNNING;
        }
    }

    private boolean targetIsNear() {
        Vector2 ownerPosition = getObject().getPosition();
        Vector2 targetPosition = getObject().getTarget().getPosition();
        float distance = ownerPosition.dst(targetPosition);
        if (distance < 2f) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
