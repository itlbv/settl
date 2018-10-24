package com.itlbv.settl.mobs.managers;

import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.mobs.Mob;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskManager {
    private Mob owner;
    private MovementManager movementManager;
    private ActionManager actionManager;

    public TaskManager(Mob owner, MovementManager movementManager, ActionManager actionManager) {
        this.owner = owner;
        this.movementManager = movementManager;
        this.actionManager = actionManager;
    }

    public void initFightingEnemy() {
        actionManager.initFighting();
    }

    public void initMovingToTarget() {
        Objects.requireNonNull(getTarget());
        movementManager.initMoving();
    }

    public void stopMoving() {
        movementManager.stopMoving();
    }

    private final float enemyCheckFreq = 1f;
    private float enemyCheckTimeCount = 0f;
    public void chooseEnemy() {
        if (getTarget() == null) chooseClosestEnemy();
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

        System.out.println("choosing closest enemy");
        setTarget(target);
    }

    private GameObject getTarget() {
        return owner.getTarget();
    }

    private void setTarget(GameObject target) {
        owner.setTarget(target);
    }
}
