package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.math.Rectangle;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.mobs.util.MobAnimationState;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.managers.Animation;
import com.itlbv.settl.mobs.managers.Action;
import com.itlbv.settl.mobs.managers.Movement;
import com.itlbv.settl.pathfinding.Path;
import org.slf4j.Logger;

import java.util.Objects;

import static com.itlbv.settl.mobs.util.MobAnimationState.IDLE;
import static com.itlbv.settl.mobs.util.MobAnimationState.WALK;
import static org.slf4j.LoggerFactory.getLogger;


public class Mob extends GameObject {
    private final Movement movement;
    private final Animation animation;
    private final Action action;
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
        this.movement = new Movement(speed, this);
        this.animation = new Animation(this);
        this.action = new Action(this);
        this.selectingRectangle = new Rectangle();
        setBhvTree(bhvTree);
    }

    public void update() {
        bhvTree.step();
        animation.update();
        updateRenderPosition();
    }

    public void chooseEnemy() {
        action.chooseEnemy();
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
        action.startFighting();
    }

    public void fight() {
        action.update();
    }

    public void defend() {
        action.defend();
    }

    public void setFightingTimeCountToZero() {
        action.fightingTimeCount = 0;
    }

    public void die() {
        setState(MobAnimationState.DEAD);
        animation.update();
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
        return movement.pathMovement.path; // TODO for path drawing in Game class
    }

    public Rectangle getSelectingRectangle() {
        selectingRectangle.set(getPosition().x - .5f,
                getPosition().y,
                1,1.5f);
        return selectingRectangle;
    }
}
