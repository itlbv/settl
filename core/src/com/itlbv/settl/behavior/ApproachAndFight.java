package com.itlbv.settl.behavior;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mob.Mob;

import static com.itlbv.settl.mob.action.util.ActionUtil.approachMobAndFight;

public class ApproachAndFight extends LeafTask<Mob> {
    @Override
    public Status execute() {
        Mob owner = getObject();
        Mob targetMob = BehaviorUtil.chooseEnemy(owner);

        if (targetMob == null) {
            return Status.FAILED;
        }

        approachMobAndFight(getObject(), targetMob);
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
