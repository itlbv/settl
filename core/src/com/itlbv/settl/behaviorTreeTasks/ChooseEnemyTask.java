package com.itlbv.settl.behaviorTreeTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.Game;
import com.itlbv.settl.mobs.Mob;
import sun.plugin2.message.OverlayWindowMoveMessage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseEnemyTask extends LeafTask<Mob>{
    @Override
    public Status execute() {
        Mob owner = getObject();

        if (owner.getTarget() != null) {
            Mob target = (Mob) owner.getTarget();
            if (target.isAlive()) {
                return Status.SUCCEEDED;
            }
        }

        List<Mob> targets = Game.mobs.stream()
                .filter(mob -> mob.getType() != owner.getType())
                .filter(Mob::isAlive).collect(Collectors.toList());
        if (targets.size() == 0) {
            return Status.FAILED;
        }

        Mob target = targets.stream()
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
