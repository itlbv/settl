package com.itlbv.settl.behavior;

import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class BehaviorUtil {

    private static final float enemyCheckFreq = 1f;
    private static float enemyCheckTimeCount = 0f;

    static Mob chooseEnemy(Mob owner) {
        if (owner.getTarget() == null)
            return chooseClosestEnemy(owner);
        enemyCheckTimeCount += Game.DELTA_TIME;
        if (enemyCheckTimeCount > enemyCheckFreq) {
            enemyCheckTimeCount = 0;
            return chooseClosestEnemy(owner);
        }
        return null;
    }

    private static Mob chooseClosestEnemy(Mob owner) {
        List<Mob> potentialTargets = Game.mobs.stream()
                .filter(mob -> mob.getType() != owner.getType())
                .collect(Collectors.toList());

        if (potentialTargets.size() == 0)
            return null;

        return potentialTargets.stream()
                .min(Comparator.comparing(mob -> mob.getPosition().dst(owner.getPosition())))
                .get();
    }
}
