package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.MobState;
import com.itlbv.settl.SteerableBody;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public abstract class Mob extends GameObject {
    private MobObjectType type;
    private MovementManager movementHandler;
    private BehaviorTree<Mob> bhvTree;
    private Mob target;
    private boolean alive;
    private float speed;
    private MobState state;
    private AnimationManager animationManager;

    private boolean targetWithinReach = false;

    public Mob(float x, float y, MobObjectType type,float width, float height,
               float bodyWidth, float bodyHeight, float speed, String bhvTree) {
        super(x, y, type, width, height);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
        this.type = type;
        this.speed = speed;
        this.movementHandler = new MovementManager(speed, this);
        this.animationManager = new AnimationManager(this);
        this.bhvTree = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(bhvTree, this);
        this.alive = true;
        this.state = MobState.IDLE;
    }


    public void update() {
        bhvTree.step();
        movementHandler.update();
        updatePosition();
        animationManager.updateAnimation();
    }

    public void fight() {
        target.setAlive(false);
        System.out.println("target defeated!");
    }

    /*
    **Getters & setters
     */
    public MobObjectType getType() {
        return this.type;
    }
    @Override
    public SteerableBody getBody() {
        return super.getBody();
    }

    public MovementManager getMovementHandler() {
        return movementHandler;
    }

    public Mob getTarget() {
        return target;
    }

    public void setTarget(Mob target) {
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

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public MobState getState() {
        return state;
    }

    public void setState(MobState state) {
        this.state = state;
    }

    /*
     **Steering behavior

    private void updateSteering() {
        getBody().updateSteering();
    }

    private void createSteeringBehavior(float speed, SteeringBehavior<Vector2> steeringBehavior) {
        SteerableBody body = getBody(); //TODO make a class variable?
        if (body == null) {
            return; //TODO make it work smhw
        }
        body.initializeSteeringBehavior(speed, steeringBehavior);
    }
    */
}
