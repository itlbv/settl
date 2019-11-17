package com.itlbv.settl.mob.action.util;

import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.Action;
import com.itlbv.settl.mob.action.Defend;
import com.itlbv.settl.mob.action.Fight;
import com.itlbv.settl.mob.action.Move;
import com.itlbv.settl.mob.movement.util.Destination;
import com.itlbv.settl.mob.util.NoActionsException;

public class ActionUtil {
    public static void moveToPosition(Mob owner, Vector2 position) {
        clearActions(owner);
        Destination destination = new Destination(position);
        pushActionOnTop(owner, new Move(owner, destination));
    }

    public static void approachMobAndFight(Mob owner, Mob target) {
        clearActions(owner);
        pushActionOnTop(owner, new Fight(owner, target));
        pushActionOnTop(owner, new Move(owner, target));
    }

    public static void setDefend(Mob owner, Mob attacker) {
        pushActionOnTop(owner, new Defend(owner, attacker));
    }

    private static void pushActionOnTop(Mob owner, Action action) {
        owner.getActions().push(action);
        //owner.setTargetReached(false); //TODO set checkTargetReached() instead
    }

    public static void removeCurrentAction(Mob owner) {
        if (owner.hasNoActions())
            throw new NoActionsException(owner.toString() + " has no actions!");
        owner.getActions().pop();
    }

    public static void clearActions(Mob owner) {
        owner.getActions().clear();
    }
}
