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



    private GameObject getTarget() {
        return owner.getTarget();
    }

    private void setTarget(GameObject target) {
        owner.setTarget(target);
    }
}
