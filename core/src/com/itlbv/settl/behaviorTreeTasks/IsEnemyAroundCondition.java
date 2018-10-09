package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Human;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.Orc;

public class IsEnemyAroundCondition extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();

        if (owner.getTarget() != null) {
            return Status.SUCCEEDED;
        }

        for (Mob mob : Game.mobs) {
            if (!owner.getClass().isInstance(mob)) {
                return Status.SUCCEEDED;
            }
        }
        return Status.FAILED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
