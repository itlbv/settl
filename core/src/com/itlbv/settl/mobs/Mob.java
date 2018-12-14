package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.math.Rectangle;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.mobs.util.MobAnimationState;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.managers.AnimationManager;
import com.itlbv.settl.mobs.managers.ActionManager;
import com.itlbv.settl.mobs.managers.MovementManager;
import com.itlbv.settl.pathfinding.Path;
import org.slf4j.Logger;

import java.util.Objects;

import static com.itlbv.settl.mobs.util.MobAnimationState.IDLE;
import static com.itlbv.settl.mobs.util.MobAnimationState.WALK;
import static org.slf4j.LoggerFactory.getLogger;


public class Mob extends GameObject {
    private final MovementManager movementManager;
    private final AnimationManager animationManager;
    private final ActionManager actionManager;
    private BehaviorTree<Mob> bhvTree;
    private MobObjectType type;
    private MobAnimationState state;
    private static final Logger log = getLogger(Mob.class);

    private GameObject target;
    private boolean alive;
    private boolean targetWithinReach = false;

    private Rectangle selectingRectangle;

    private int id;

    public Mob(MobObjectType type, String bhvTree, float speed) {
        super(type);
        this.alive = true;
        this.type = type;
        this.state = IDLE;
        this.movementManager = new MovementManager(speed, this);
        this.animationManager = new AnimationManager(this);
        this.actionManager = new ActionManager(this);
        this.selectingRectangle = new Rectangle();
        setBhvTree(bhvTree);
    }

    public void update() {
        bhvTree.step();
        animationManager.update();
        updateRenderPosition();
    }

    public void chooseEnemy() {
        actionManager.chooseEnemy();
    }

    public void startMoving() {
        Objects.requireNonNull(target);
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
        log.info(this + " is dead");
        Game.world.destroyBody(getBody());
        Game.world.destroyBody(getSensor());
    }

    @Override
    public String toString() {
        return type.toString() + "_" + id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Path getPath() {
        return movementManager.pathMovement.path; // TODO for path drawing in Game class
    }

    public Rectangle getSelectingRectangle() {
        selectingRectangle.set(getPosition().x - .5f,
                getPosition().y,
                1,1.5f);
        return selectingRectangle;
    }
}
