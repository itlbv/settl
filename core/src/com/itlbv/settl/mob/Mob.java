package com.itlbv.settl.mob;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.Target;
import com.itlbv.settl.mob.action.Action;
import com.itlbv.settl.mob.animation.AnimationProcessor;
import com.itlbv.settl.mob.util.MobType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

@Data
@EqualsAndHashCode(callSuper = false)
public class Mob extends GameObject {

    private static final Logger log = LoggerFactory.getLogger(Mob.class);

    private Stack<Action> actions;
    private AnimationProcessor animation;
    private BehaviorTree<Mob> behavior;
    private int id;
    private MobType type;
    private int hitpoints;
    private boolean alive;
    private boolean targetReached;

    public Mob(MobType type, String behavior) {
        this.type = type;
        this.hitpoints = type.getHitpoints();
        this.alive = true;
        actions = new Stack<>();
        animation = new AnimationProcessor(this);
        setBehavior(behavior);
    }

    public void update() {
        if (hasNoActions()) {
            behavior.step();
        } else {
            actions.peek().run();
        }
        animation.update();
    }

    public Action getAction() {
        if (hasNoActions())
            return null;
        return actions.peek();
    }

    public Action.ActionType getActionType() {
        if (hasNoActions())
            return null;
        return actions.peek().getType();
    }

    public Target getTarget() {
        if (hasNoActions())
            return null;
        return getAction().getTarget();
    }

    public boolean hasNoActions() {
        return actions.isEmpty();
    }

    private void setBehavior(String behavior) {
        this.behavior = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(behavior, this);
    }

    @Override
    public String toString() {
        return "{" + type.toString() + "_" + id + "}";
    }
}
