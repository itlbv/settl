package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mobs.Mob;

public class FightEnemyTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        Mob target = (Mob) owner.getTarget();
        if (target.isAlive()) {
            owner.fight();
            return Status.RUNNING;
        } else {
            owner.setTarget(null);
            owner.stopMoving();
            return Status.SUCCEEDED;
        }

    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
