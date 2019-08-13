package com.itlbv.settl.mob.action.util;

import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.Action;
import com.itlbv.settl.mob.movement.util.Destination;

import static com.itlbv.settl.mob.action.util.ActionType.*;

public class ActionUtil {
    public static void setMove(Mob owner, Vector2 position) {
        clearActions(owner);
        Destination destination = new Destination(position);
        addAction(owner, new Action(MOVE, destination));
    }

    public static void setApproachAndFight(Mob owner, Mob target) {
        clearActions(owner);
        addAction(owner, new Action(MOVE, target));
        addAction(owner, new Action(FIGHT, target));
    }

    public static void setDefend(Mob owner, Mob attacker) {
        addActionOnTop(owner, new Action(DEFEND, attacker));
    }

    public static void removeCurrentAction(Mob owner) {
        owner.getActions().remove(0);
    }

    public static ActionType getTypeOfCurrentAction(Mob owner) {
        //TODO throw exception?
        if (owner.hasNoActions()) {
            return null;
        }
        return owner.getActions().get(0).getType();
    }

    public static Action getCurrentAction(Mob owner) {
        //TODO throw exception?
        if (owner.hasNoActions()) {
            return null;
        }
        return owner.getActions().get(0);
    }

    public static void clearActions(Mob owner) {
        owner.getActions().clear();
    }

    private static void addAction(Mob owner, Action action) {
        owner.getActions().add(action);
        owner.setTarget(action.getTarget());
        owner.setTargetReached(false); //TODO set checkTargetReached() instead
    }

    private static void addActionOnTop(Mob owner, Action action) {
        owner.getActions().add(0, action);
    }
}
