package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.GameWorld;
import com.itlbv.settl.mobs.managers.TaskManager;
import com.itlbv.settl.mobs.utils.MobAnimationState;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.managers.AnimationManager;
import com.itlbv.settl.mobs.managers.ActionManager;
import com.itlbv.settl.mobs.managers.MovementManager;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.itlbv.settl.mobs.utils.MobAnimationState.IDLE;
import static com.itlbv.settl.mobs.utils.MobAnimationState.WALK;

public class Mob extends GameObject {
    private final MovementManager movementManager;
    private final AnimationManager animationManager;
    private final ActionManager actionManager;
    private final TaskManager taskManager;
    private BehaviorTree<Mob> bhvTree;
    private MobObjectType type;
    private MobAnimationState state;

    private GameObject target;
    private boolean alive;
    private boolean targetWithinReach = false;

    public Mob(MobObjectType type, String bhvTree, float speed, float renderWidth, float renderHeight) {
        super(type, renderWidth, renderHeight);
        this.alive = true;
        this.type = type;
        this.state = IDLE;
        this.movementManager = new MovementManager(speed, this);
        this.animationManager = new AnimationManager(this);
        this.actionManager = new ActionManager(this);
        this.taskManager = new TaskManager(this, movementManager, actionManager);
        setBhvTree(bhvTree);
    }

    public void update() {
        bhvTree.step();
        animationManager.update();
        updateRenderPosition();
    }

    public void startMoving() {
        Objects.requireNonNull(getTarget());
        setState(WALK);
        movementManager.initMoving();
    }

    public void move() {
        movementManager.update();
    }

    public void stopMoving() {
        setState(IDLE);
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
                .filter(mob -> mob.getType() != getType())
                .collect(Collectors.toList());
        if (potentialTargets.size() == 0) return;
        Mob target = potentialTargets.stream()
                .min(Comparator.comparing(mob -> mob.getPosition().dst(getPosition())))
                .get();
        setTarget(target);
    }

    public void startFighting() {
        actionManager.startFighting();
    }

    public void fight() {
        actionManager.update();
    }

    public void defend() {
        actionManager.defend();
    }

    public void setFightingTimeCountToZero() {
        actionManager.fightingTimeCount = 0;
    }

    public void die() {
        setState(MobAnimationState.DEAD);
        animationManager.update();
        System.out.println(getClass().getSimpleName() + " is dead");
        GameWorld.world.destroyBody(getBody());
        GameWorld.world.destroyBody(getSensor());
    }

    /*
    **Getters & setters
     */
    public MobObjectType getType() {
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

    public MobAnimationState getState() {
        return state;
    }

    public void setState(MobAnimationState state) {
        this.state = state;
    }

    private void setBhvTree(String bhvTree) {
        this.bhvTree = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(bhvTree, this);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}
