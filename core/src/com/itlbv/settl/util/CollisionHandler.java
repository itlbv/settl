package com.itlbv.settl.util;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.itlbv.settl.mob.Mob;

public class CollisionHandler implements ContactListener {

    private Object o1;
    private Object o2;

    private boolean notMobsColliding() {
        return (!(o1 instanceof Mob)) || (!(o2 instanceof Mob));
    }

    @Override
    public void beginContact(Contact contact) {
        o1 = contact.getFixtureA().getBody().getUserData();
        o2 = contact.getFixtureB().getBody().getUserData();
        if (o1 == o2) return;
        if (notMobsColliding()) return;
        if (contact.getFixtureA().isSensor()) processContactForSensor(o1, o2, true);
        else if (contact.getFixtureB().isSensor()) processContactForSensor(o2, o1, true);
    }

    @Override
    public void endContact(Contact contact) {
        o1 = contact.getFixtureA().getBody().getUserData();
        o2 = contact.getFixtureB().getBody().getUserData();
        if (o1 == o2) return;
        if (notMobsColliding()) return;
        if (contact.getFixtureA().isSensor()) processContactForSensor(o1, o2, false);
        else if (contact.getFixtureB().isSensor()) processContactForSensor(o2, o1, false);
    }

    private void processContactForSensor(Object sensorOwnerObj, Object mobOfCollisionObj, boolean beginContact) {
        Mob sensorOwner = (Mob) sensorOwnerObj;
        Mob mobOfCollision = (Mob) mobOfCollisionObj;
        if (sensorOwner.getTarget() != mobOfCollision) return;
        sensorOwner.setTargetReached(beginContact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
