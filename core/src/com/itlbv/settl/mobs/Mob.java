package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.MobState;
import com.itlbv.settl.SteerableBody;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public abstract class Mob extends GameObject {
    private MobObjectType type;
    private final MovementManager movementManager;
    private final AnimationManager animationManager;
    private BehaviorTree<Mob> bhvTree;
    private MobState state;

    private GameObject target;
    private Mob enemy;
    private boolean alive;
    private boolean targetWithinReach = false;

    public Mob(float x, float y, MobObjectType type,float width, float height,
               float bodyWidth, float bodyHeight, float speed, String bhvTree) {
        super(x, y, type, width, height);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
        this.type = type;
        this.state = MobState.IDLE;
        this.movementManager = new MovementManager(speed, this);
        this.animationManager = new AnimationManager(this);
        this.bhvTree = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(bhvTree, this);
        this.alive = true;
    }


    public void update() {
        bhvTree.step();
        movementManager.update();
        animationManager.update();
        updatePosition();
    }

    /*
    **Combat
     */
    float combatPhaseTime = 0;
    public void fight() {
        combatPhaseTime += Game.DELTA_TIME;
        if (combatPhaseTime > 1f) {
            if (MathUtils.randomBoolean(.3f)) {
                attackEnemy();
                state = MobState.FIGHTING;
                combatPhaseTime = 0f;
            }
        }
    }

    private void attackEnemy() {
        System.out.println("*********************ATTACK***************************");
        enemy.defend();
    }

    public void defend() {
        if (MathUtils.randomBoolean(.5f)) {
            state = MobState.GOT_HIT;
        }
    }

    public Mob getEnemy() {
        return enemy;
    }

    public void setEnemy(Mob enemy) {
        this.enemy = enemy;
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

    public MovementManager getMovementManager() {
        return movementManager;
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
