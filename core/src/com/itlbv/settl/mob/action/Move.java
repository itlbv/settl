package com.itlbv.settl.mob.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.Target;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.movement.PathMovement;
import com.itlbv.settl.mob.action.movement.SteeringMovement;
import com.itlbv.settl.mob.movement.util.Destination;

import static com.itlbv.settl.mob.action.ActionUtil.removeCurrentAction;
import static com.itlbv.settl.mob.util.MobTargetUtil.isTargetCloseAndVisible;

public class Move extends Action {

    private PathMovement pathMovement;
    private SteeringMovement steeringMovement;

    private boolean useSteering;
    private float timer = 2f;

    Move(Mob owner, Target target) {
        super(owner, ActionType.MOVE, target);
        int speed = owner.getType().getSpeed();
        this.pathMovement = new PathMovement(owner, speed);
        this.steeringMovement = new SteeringMovement(owner, speed);
    }

    @Override
    public void run() {
        checkIfTargetReached();
        if (owner.isTargetReached()) {
            stop();
            return;
        }
        calculateMovement();
        setLinearVelocity(useSteering ?
                steeringMovement.getVelocity() :
                pathMovement.getVelocity());
        checkSensorToBodyAlignment();
    }

    private void checkIfTargetReached() {
        // TODO review this mess
        if (target instanceof Destination) {
            Vector2 ownerPos = owner.getPosition();
            Vector2 destPos = target.getPosition();
            if (ownerPos.epsilonEquals(destPos, 0.1f)) {
                owner.setTargetReached(true);
            } else {
                owner.setTargetReached(false);
            }
        }
    }

    private void calculateMovement() {
        if (timer < 2) {
            timer += Game.DELTA_TIME;
            return;
        }
        if (isTargetCloseAndVisible(owner)) {
            switchToSteering();
        } else {
            switchToPath();
        }
        timer = 0;
    }

    private void switchToSteering() {
        useSteering = true;
        pathMovement.clearPath();
        steeringMovement.init();
    }

    private void switchToPath() {
        useSteering = false;
        steeringMovement.disable();
        pathMovement.init();
    }

    private void setLinearVelocity(Vector2 linearVelocity) {
        owner.getBody().setLinearVelocity(linearVelocity);
        owner.getSensor().setLinearVelocity(linearVelocity);
    }

    /**
     * When owner's body is being pushed by another body, owner's sensor stays in place
     * which causes misalignment. This method sets additional linear velocity to owner's
     * sensor to center it with owner's body.
     */
    private void checkSensorToBodyAlignment() {
        Vector2 bodyPosition = owner.getPosition();
        Vector2 sensorPosition = owner.getSensorPosition();
        if (bodyPosition.epsilonEquals(sensorPosition, MathUtils.FLOAT_ROUNDING_ERROR)) {
            return;
        }
        Vector2 vectorToBody = bodyPosition.sub(sensorPosition);
        owner.getSensor().setLinearVelocity(owner.getBody().getLinearVelocity().cpy().mulAdd(vectorToBody, 10));
    }

    private void stop() {
        steeringMovement.disable();
        pathMovement.clearPath();
        setLinearVelocity(new Vector2(0, 0));
        removeCurrentAction(owner);
        timer = 2;
    }

    public PathMovement getPathMovement() {
        //TODO only for path drawing in DebugRenderer
        return pathMovement;
    }

    public boolean isUnderSteering() {
        return useSteering;
    }
}
