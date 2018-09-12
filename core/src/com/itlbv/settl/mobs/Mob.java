package com.itlbv.settl.mobs;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.GameObject;
import com.itlbv.settl.SteerableBody;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.enumsStateMachine.MobState;
import com.itlbv.settl.map.Tile;
import com.itlbv.settl.pathfinding.PathHelper;

public abstract class Mob extends GameObject {
    private MobObjectType type;

    public Mob(float x, float y, TextureRegion texture, MobObjectType type,
               float bodyWidth, float bodyHeight) {
        super(x, y, texture, type);
        super.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight);
        this.type = type;
    }


    /*
    **Pathfinding
     */



    /*
    **Steering behavior
     */
    public void createSteeringBehavior(float speed, SteeringBehavior<Vector2> steeringBehavior) {
        SteerableBody body = super.getBody(); //TODO make a class variable?
        if (body == null) {
            return; //TODO make it work smhw
        }
        body.initializeSteeringBehavior(speed, steeringBehavior);
    }

    public void updateSteering() {
        SteerableBody body = super.getBody(); //TODO make a class variable?
        body.updateSteering();
    }

    /*
    **State machine implementation
     */
    public StateMachine<Mob, MobState> stateMachine = new DefaultStateMachine<Mob, MobState>(this, MobState.IDLE);

    public void updateState() {
        stateMachine.update();
    }

    public void setTarget(GameObject target) { //TODO delete when state machine is ready

        DefaultGraphPath<Tile> path = calculatePath(target);
        LinePath<Vector2> linePath = parsePath(path);
        FollowPath<Vector2, LinePath.LinePathParam> behavior = new FollowPath<>(this.getBody(), linePath, 5)
                .setArrivalTolerance(0.001f)
                .setDecelerationRadius(10)
                .setPredictionTime(0.0f)
                .setArrivalTolerance(1);




                /*
        Arrive<Vector2> behavior = new Arrive<Vector2>(this.getBody(), target.getBody());
        behavior.setArrivalTolerance(50f);
        behavior.setDecelerationRadius(10f);
        createSteeringBehavior(50f, behavior);
        */
    }

    private LinePath<Vector2> parsePath (DefaultGraphPath path) {
        Array<Vector2> wayPoints = new Array<>();

        for(int x = 0; x < path.nodes.size; x++)
        {
            Tile node = (Tile)path.nodes.get(x);

            float posx = node.getNodeX() * 8;
            float posy = node.getNodeY() * 8;

            wayPoints.add(new Vector2(posx, posy));
        }

        getBody().getPosition().set(new Vector2(wayPoints.first().x, wayPoints.first().y));
        LinePath<Vector2> linePath = new LinePath<>(wayPoints, true);

        return linePath;
    }

    private DefaultGraphPath<Tile> calculatePath(GameObject target) {
        return PathHelper.getPath(this.position, target.position);
    }

    /*
    **Getters & setters
     */
    public MobObjectType getType() {
        return this.type;
    }
}
