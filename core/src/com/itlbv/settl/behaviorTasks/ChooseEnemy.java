package com.itlbv.settl.behaviorTasks;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseEnemy extends LeafTask<Mob>{
    private Mob owner;

    @Override
    public Status execute() {
        owner = getObject();
        chooseEnemy();
        return Status.SUCCEEDED;
    }

    private final float enemyCheckFreq = 1f;
    private float enemyCheckTimeCount = 0f;
    private void chooseEnemy() {
        if (owner.getTarget() == null) chooseClosestEnemy();
        enemyCheckTimeCount += Game.DELTA_TIME;
        if (enemyCheckTimeCount > enemyCheckFreq){
            enemyCheckTimeCount = 0;
            chooseClosestEnemy();
        }
    }

    private void chooseClosestEnemy() {
        List<Mob> potentialTargets = Game.mobs.stream()
                .filter(mob -> mob.getType() != owner.getType())
                .collect(Collectors.toList());
        if (potentialTargets.size() == 0) return;
        Mob target = potentialTargets.stream()
                .min(Comparator.comparing(mob -> mob.getPosition().dst(owner.getPosition())))
                .get();
        owner.setTarget(target);
    }

    @Override
    protected Task copyTo(Task task) {
        return task;
    }
}