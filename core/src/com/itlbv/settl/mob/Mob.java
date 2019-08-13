package com.itlbv.settl.mob;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.mob.action.ActionProcessor;
import com.itlbv.settl.mob.animation.AnimationProcessor;
import com.itlbv.settl.mob.movement.util.Target;
import com.itlbv.settl.mob.util.MobType;
import com.itlbv.settl.mob.action.Action;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Mob extends GameObject {

    private int id;
    private MobType type;
    private int hitpoints;
    private boolean alive;

    private BehaviorTree<Mob> behavior;
    private List<Action> actions;
    private ActionProcessor actionProcessor;
    private AnimationProcessor animationProcessor;

    private Target target;
    private boolean targetReached;

    public Mob(MobType type, String behavior) {
        this.type = type;
        this.hitpoints = type.getHitpoints();
        this.alive = true;
        setBehavior(behavior);
        actions = new LinkedList<>();
        actionProcessor = new ActionProcessor(this);
        animationProcessor = new AnimationProcessor(this);
    }

    public void update() {
        if (hasNoActions()) {
            behavior.step();
        }
        actionProcessor.update();
        animationProcessor.update();
    }

    private void setBehavior(String behavior) {
        this.behavior = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(behavior, this);
    }

    public boolean hasNoActions() {
        return actions.isEmpty();
    }

    @Override
    public String toString() {
        return type.toString() + "_" + id;
    }
}
