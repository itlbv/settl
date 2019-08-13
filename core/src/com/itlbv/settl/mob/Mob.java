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



















    /*
    private final AnimationProcessor animationManager;
    private final ActionProcessor actionProcessor;
    private MobType type;
    private MobAnimationType state;
    private static final Logger log = getLogger(Mob.class);

    private GameObject target;
    private boolean alive;
    private boolean targetWithinReach = false;

    private Node destination;
    public List<MobAction> actions;

    private Rectangle selectingRectangle;

    private int id;

    public Mob(MobType type, String bhvTree, float speed) {
        this.alive = true;
        this.type = type;
        this.state = IDLE;
        this.movement = new Movement(speed, this);
        this.animationManager = new AnimationProcessor(this);
        this.actionProcessor = new ActionProcessor(this);

        actions = new LinkedList<>();

        this.selectingRectangle = new Rectangle();
        setBhvTree(bhvTree);
    }

    public void update() {
        if (actions.size() == 0) {
            bhvTree.step();
        } else {
            actionProcessor.update();
        }
        animationManager.update();
        updateSpritePosition();
    }

    public void chooseEnemy() {
        actionProcessor.chooseEnemy();
    }

    public void startMoving() {
        Objects.requireNonNull(target);
        setState(WALK);
        movement.initMoving();
    }

    public void move() {
        movement.update();
    }

    public void stopMoving() {
        setState(IDLE);
        movement.stopMoving();
    }

    public void startFighting() {
        actionProcessor.startFighting();
    }

    public void fight() {
        actionProcessor.update();
    }

    public void defend() {
        actionProcessor.defend();
    }

    public void setFightingTimeCountToZero() {
        actionProcessor.fightingTimeCount = 0;
    }

    public void die() {
        setState(MobAnimationType.DEAD);
        animationManager.update();
        log.info(this + " is dead");
        Game.world.destroyBody(getBody());
        Game.world.destroyBody(getSensor());
    }



    /*
    **Getters & setters

    public MobType getType() {
        return this.type;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public boolean isTargetWithinReach() {
        return targetWithinReach;
    }

    public void setTargetWithinReach(boolean targetWithinReach) {
        this.targetWithinReach = targetWithinReach;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public MobAnimationType getState() {
        return state;
    }

    public void setState(MobAnimationType state) {
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Path getPath() {
        return movement.pathMovement.path; // TODO for path drawing in Game class
    }
}*/

