package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.SteerableBody;
import com.itlbv.settl.TestObject;
import com.itlbv.settl.enumsObjectType.MapObjectType;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.pathfinding.Path;
import com.itlbv.settl.pathfinding.PathHelper;

public abstract class Mob extends GameObject {
    private MobObjectType type;
    private MoveManager moveManager;
    private BehaviorTree<Mob> bhvTree;
    private GameObject target;
    private float speed;

    public Mob(float x, float y, MobObjectType type, TextureRegion texture, float width, float height,
               float bodyWidth, float bodyHeight, float speed, String bhvTree) {
        super(x, y, type, texture, width, height);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
        this.type = type;
        this.speed = speed;
        this.moveManager = new MoveManager(speed, this);
        this.bhvTree = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(bhvTree, this);
    }


    public void update() {
        bhvTree.step();
        moveManager.update();
        //updateSteering();
        updatePosition();
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

    public MoveManager getMoveManager() {
        return moveManager;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    /*
     **Steering behavior
     */
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
}
