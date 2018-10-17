package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.mobs.utils.MobState;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.managers.AnimationManager;
import com.itlbv.settl.mobs.managers.ActionManager;
import com.itlbv.settl.mobs.managers.MovementManager;

public abstract class Mob extends GameObject {
    private final MovementManager movementManager;
    private final AnimationManager animationManager;
    private final ActionManager actionManager;
    private BehaviorTree<Mob> bhvTree;
    private MobObjectType type;
    private MobState state;

    private GameObject target;
    private boolean alive;
    private boolean targetWithinReach = false;

    private int hitpoints = 2;
    public void minusHitpoint() {
        hitpoints--;
        System.out.println(this.getClass().getSimpleName() + " hitpoints: " + hitpoints);
        if (hitpoints == 0) {
            alive = false;
        }
    }

    public Mob(float x, float y, MobObjectType type,float width, float height,
               float bodyWidth, float bodyHeight, float speed, String bhvTree) {
        super(x, y, type, width, height);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
        this.alive = true;
        this.type = type;
        this.state = MobState.IDLE;
        this.movementManager = new MovementManager(speed, this);
        this.animationManager = new AnimationManager(this);
        this.actionManager = new ActionManager(this);
        this.bhvTree = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(bhvTree, this);
    }

    public void update() {
        if (!alive) {
            state = MobState.DEAD;
            System.out.println(this.getClass().getSimpleName() + " IS DEAD");
            animationManager.update();
            return;
        }


        bhvTree.step();
        movementManager.update();
        animationManager.update();
        updateRenderPosition();
    }

    public void initMoving() {
        if (target == null) {
            return;
        }
        movementManager.initMoving();
    }

    public void stopMoving() {
        movementManager.stopMoving();
    }

    public void initFighting() {
        actionManager.initFighting();
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

    public boolean isAlive() {
        return alive;
    }

    public MobState getState() {
        return state;
    }

    public void setState(MobState state) {
        this.state = state;
    }
}
