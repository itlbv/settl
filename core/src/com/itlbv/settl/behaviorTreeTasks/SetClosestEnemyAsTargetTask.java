package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;

import java.util.Comparator;

public class SetClosestEnemyAsTargetTask extends LeafTask<Mob>{
    @Override
    public Status execute() {
        Mob owner = getObject();
        Mob target = Game.mobs.stream()
                .filter(mob -> !mob.getClass().isInstance(owner))
                .filter(Mob::isAlive)
                .min(Comparator.comparing(mob -> mob.getPosition().dst(owner.getPosition())))
                .get();
        owner.setTarget(target);
        System.out.println(owner.getClass().getSimpleName() + " setting closest enemy as a target");
        return Status.SUCCEEDED;
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}
