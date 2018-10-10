package com.itlbv.settl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.itlbv.settl.mobs.Mob;

public class CollisionHandler implements ContactListener {

    private Object o1;
    private Object o2;

    @Override
    public void beginContact(Contact contact) {
        o1 = contact.getFixtureA().getBody().getUserData();
        o2 = contact.getFixtureB().getBody().getUserData();
        printCollision(contact, true);
        if (o1 == o2) {
            return;
        }
        if (!collisionOfMobs()){
            return;
        }
        if (contact.getFixtureA().isSensor()) {
            beginContactForSensor(o1, o2);
        } else if (contact.getFixtureB().isSensor()) {
            beginContactForSensor(o2, o1);
        }
    }

    private boolean collisionOfMobs() {
        return (o1 instanceof Mob) || (o2 instanceof Mob);
    }

    private void beginContactForSensor(Object sensorOwnerObj, Object mobOfCollisionObj) {
        Mob sensorOwner = (Mob) sensorOwnerObj;
        Mob mobOfCollision = (Mob) mobOfCollisionObj;
        if (sensorOwner.getTarget() == mobOfCollision) {
            sensorOwner.setTargetWithinReach(true);
            String logStr = sensorOwner.getClass().getSimpleName()
                    + " reached target "
                    + mobOfCollision.getClass().getSimpleName();
            Gdx.app.log(sensorOwner.toString(), logStr);
        }
    }

    private void printCollision(Contact contact, boolean begin) {
        String logStr = o1.getClass().getSimpleName()
                + (contact.getFixtureA().isSensor() ? "SENSOR" : "BODY")
                + (begin ? "  ---begin--- " : "  ---end--- ")
                + o2.getClass().getSimpleName()
                + ((contact.getFixtureB().isSensor() ? "SENSOR" : "BODY"))
                + " " + Game.RENDER_ITERATION;
        Gdx.app.log("Contact", logStr);
    }

    @Override
    public void endContact(Contact contact) {
        o1 = contact.getFixtureA().getBody().getUserData();
        o2 = contact.getFixtureB().getBody().getUserData();
        printCollision(contact, false);
        if (o1 == o2) {
            return;
        }
        if (!collisionOfMobs()){
            return;
        }
        if (contact.getFixtureA().isSensor()) {
            endContactForSensor(o1, o2);
        } else if (contact.getFixtureB().isSensor()) {
            endContactForSensor(o2, o1);
        }
    }

    private void endContactForSensor(Object sensorOwnerObj, Object mobOfCollisionObj) {
        Mob sensorOwner = (Mob) sensorOwnerObj;
        Mob mobOfCollision = (Mob) mobOfCollisionObj;
        if (sensorOwner.getTarget() == mobOfCollision) {
            sensorOwner.setTargetWithinReach(false);
            String logStr = sensorOwner.getClass().getSimpleName()
                    + " lost target "
                    + mobOfCollision.getClass().getSimpleName();
            Gdx.app.log(sensorOwner.toString(), logStr);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
