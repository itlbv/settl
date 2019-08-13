package com.itlbv.settl.behaviorTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.util.ActionUtil;

import static com.itlbv.settl.mob.util.MobUtil.getTargetMob;

public class ApproachAndFight extends LeafTask<Mob> {
    @Override
    public Status execute() {
        ActionUtil.setApproachAndFight(getObject(), getTargetMob(getObject()));
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Mob> copyTo(Task<Mob> task) {
        return task;
    }
}
