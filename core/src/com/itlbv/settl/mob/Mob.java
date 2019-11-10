package com.itlbv.settl.mob;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.mob.action.Action;
import com.itlbv.settl.mob.animation.AnimationProcessor;
import com.itlbv.settl.mob.movement.Movement;
import com.itlbv.settl.mob.movement.util.Target;
import com.itlbv.settl.mob.util.MobType;
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
    public Movement movement;
    public List<Action> actions;
    //private ActionProcessor actionProcessor;
    public AnimationProcessor animation;

    private Target target;
    private boolean targetReached;

    public Mob(MobType type, String behavior) {
        this.type = type;
        this.hitpoints = type.getHitpoints();
        this.alive = true;
        setBehavior(behavior);
        movement = new Movement(this);
        actions = new LinkedList<>();
        //actionProcessor = new ActionProcessor(this);
        animation = new AnimationProcessor(this);
    }

    public void update() {
        if (hasNoActions()) {
            behavior.step();
        }
        if (!actions.isEmpty()) {
            actions.get(0).run();
        }
        // actionProcessor.update();
        animation.update();
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
