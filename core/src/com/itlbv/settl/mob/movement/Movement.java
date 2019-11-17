package com.itlbv.settl.mob.movement;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.movement.util.Destination;
import com.itlbv.settl.mob.movement.util.NoTargetException;
import com.itlbv.settl.mob.util.MobTargetUtil;

import static com.itlbv.settl.mob.action.util.ActionUtil.removeCurrentAction;

public class Movement {
    private final Mob owner;

    private PathMovement pathMovement;
    private SteeringMovement steeringMovement;
    private boolean useSteering;

    public Movement(Mob owner) {
        this.owner = owner;
        int speed = owner.getType().getSpeed();
        this.pathMovement = new PathMovement(owner, speed);
        this.steeringMovement = new SteeringMovement(owner, speed);

    }

    /*
    private void initTimedExecutor() {
        ScheduledExecutorService  executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new TimerTask(), 0, 2, TimeUnit.SECONDS);
    }

    private class TimerTask implements Runnable {
        @Override
        public void run() {
            if (MobUtil.isTargetCloseAndVisible(owner)) {
                switchToSteering();
            } else {
                switchToPath();
            }
            System.out.println("recalc");
        }
    }
    */


    public void move() {
        /**
         * TODO
         * Check if destination is different and change movement straight away
         * or else owner change movement only every 2 seconds
         */
        if (owner.getTarget() == null)
            throw new NoTargetException(owner.toString() + " has no target while trying to move!");


        checkIfTargetReached();
        if (owner.isTargetReached()){
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
        if (owner.getTarget() instanceof Destination) {
            Vector2 ownerPos = owner.getPosition();
            Vector2 destPos = ((Destination) owner.getTarget()).getPosition();
            if (ownerPos.epsilonEquals(destPos, 0.1f)) {
                owner.setTargetReached(true);
                System.out.println("dest reached");
            }
        }
    }

    /**
     * Uses ScheduledExecutorService which recalculates movement every few seconds.
     */
    private float timer = 2f;
    private void calculateMovement() {
        if (timer < 2) {
            timer += Game.DELTA_TIME;
            return;
        }

        if (MobTargetUtil.isTargetCloseAndVisible(owner)) {
            switchToSteering();
            System.out.println(owner.toString() + " steering");
        } else {
            switchToPath();
            System.out.println(owner.toString() + " path");
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
        Vector2 bodyPosition = owner.getBody().getPosition();
        Vector2 sensorPosition = owner.getSensor().getPosition();
        if (bodyPosition.epsilonEquals(sensorPosition, MathUtils.FLOAT_ROUNDING_ERROR)) {
            return;
        }
        Vector2 vectorToBody = bodyPosition.sub(sensorPosition);
        owner.getSensor().setLinearVelocity(owner.getBody().getLinearVelocity().cpy().mulAdd(vectorToBody,10));
    }

    private void stop() {
        steeringMovement.disable();
        pathMovement.clearPath();
        setLinearVelocity(new Vector2(0,0));
        removeCurrentAction(owner);

        timer = 2;
    }

    public PathMovement getPathMovement() {
        //TODO only for path drawing in DebugRenderer
        return pathMovement;
    }
}