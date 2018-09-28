package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.MobState;
import com.itlbv.settl.mobs.Mob;

public class FightEnemyTask extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        if (!owner.isTargetWithinReach()) {
            return Status.FAILED;
        }
        //System.out.println("Fighting enemy");
        //owner.setState(MobState.IDLE);
        owner.fight();
        if (!owner.getEnemy().isAlive()) {
            return Status.SUCCEEDED;
        }
        return Status.RUNNING;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
