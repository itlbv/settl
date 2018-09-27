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
        //System.out.println("Checking for enemies");
        Mob owner = getObject();

        if (owner.getTarget() != null) {
            return Status.SUCCEEDED;
        }

        if (owner instanceof Human) {
            for (Mob mob : Game.mobs) {
                if (mob instanceof Orc) {
                    owner.setTarget(mob);
                    return Status.SUCCEEDED;
                }
            }
        }
        if (owner instanceof Orc) {
            for (Mob mob : Game.mobs) {
                if (mob instanceof Human) {
                    owner.setTarget(mob);
                    return Status.SUCCEEDED;
                }
            }
        }
        return Status.FAILED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
