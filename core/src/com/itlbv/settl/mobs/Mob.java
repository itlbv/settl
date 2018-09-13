package com.itlbv.settl.mobs;

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
import com.itlbv.settl.enumsStateMachine.MobState;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.pathfinding.Path;
import com.itlbv.settl.pathfinding.PathHelper;

public abstract class Mob extends GameObject {
    private StateMachine<Mob, MobState> stateMachine;
    private MobObjectType type;
    private float speed;
    private Path path;

    public Mob(float x, float y, MobObjectType type, TextureRegion texture, float width, float height,
               float bodyWidth, float bodyHeight, float speed) {
        super(x, y, type, texture, width, height);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
        this.stateMachine = new DefaultStateMachine<>(this, MobState.IDLE);
        this.type = type;
        this.speed = speed;
        this.path = new Path();
    }

    public void update() {
        updateState();
        updateSteering();
        updatePathMovement();
        updatePosition();
    }

    private void updateState() {
        stateMachine.update();
    }

    private void updateSteering() {
        getBody().updateSteering();
    }

    private void updatePathMovement() {
        if (path.size() == 0) {
            return;
        }
        followPath();
    }

    private void followPath() {
        Vector2 nextWaypoint = getNextWaypoint();
        Vector2 vectorToWaypoint = getVectorToWaypoint(nextWaypoint);
        getBody().setLinearVelocity(vectorToWaypoint);
    }

    private Vector2 getNextWaypoint() {
        Vector2 nextPosition = path.getFirstPosition();
        Vector2 currentPosition = getBodyPosition();
        if(nextPosition.cpy().sub(currentPosition).len() < .2f) {
            Game.testObjects.removeIndex(0); //TODO path drawing
            path.nodes.removeIndex(0);
            if (path.size() == 0) {
                return currentPosition;
            }
            nextPosition = path.getFirstPosition();
        }
        return nextPosition;
    }

    private Vector2 getVectorToWaypoint(Vector2 nextWaypoint) {
        return nextWaypoint.cpy().sub(getBodyPosition()).nor().scl(speed);
    }

    public void setTarget(GameObject target) { //TODO delete when state machine is ready
        Vector2 startPosition = getBodyPosition();
        Vector2 targetPosition = target.getBodyPosition();
        path = PathHelper.getPath(startPosition, targetPosition);
        drawPath();
        followPath();
    }



    private void drawPath() {
        for (Tile node : path.nodes) {
            TextureRegion texture = new TextureRegion(new Texture("white_dot.png"));
            TestObject o = new TestObject(node.getX() + .5f,node.getY() + .5f, MapObjectType.TESTOBJECT, texture);
            Game.testObjects.add(o);
        }
    }

    /*
    **Steering behavior
     */
    private void createSteeringBehavior(float speed, SteeringBehavior<Vector2> steeringBehavior) {
        SteerableBody body = getBody(); //TODO make a class variable?
        if (body == null) {
            return; //TODO make it work smhw
        }
        body.initializeSteeringBehavior(speed, steeringBehavior);
    }

    /*
    **Getters & setters
     */
    public MobObjectType getType() {
        return this.type;
    }

    public StateMachine<Mob, MobState> getStateMachine() {
        return stateMachine;
    }

    @Override
    public SteerableBody getBody() {
        return super.getBody();
    }
}
