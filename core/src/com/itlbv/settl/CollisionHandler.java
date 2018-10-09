package com.itlbv.settl;

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
        //printCollision(contact);
        if (o1 == o2) {
            return;
        }
        if (!collisionOfMobs()){
            return;
        }
        if (contact.getFixtureA().isSensor()) {
            solveCollisionForSensor(o1, o2);
        } else if (contact.getFixtureB().isSensor()) {
            solveCollisionForSensor(o2, o1);
        }
    }

    private boolean collisionOfMobs() {
        return (o1 instanceof Mob) || (o2 instanceof Mob);
    }

    private void solveCollisionForSensor(Object sensorOwnerObj, Object mobOfCollisionObj) {
        Mob sensorOwner = (Mob) sensorOwnerObj;
        Mob mobOfCollision = (Mob) mobOfCollisionObj;
        if (sensorOwner.getTarget() == mobOfCollision) {
            sensorOwner.setTargetWithinReach(true);
            System.out.println(sensorOwner.getClass().getSimpleName()
                    + " reached target "
                    + mobOfCollision.getClass().getSimpleName());
        }
    }

    private void printCollision(Contact contact) {
        System.out.println(o1.getClass().getSimpleName()
                + (contact.getFixtureA().isSensor() ? "SENSOR" : "BODY")
                + "  ---contacting--- "
                + o2.getClass().getSimpleName()
                + ((contact.getFixtureB().isSensor() ? "SENSOR" : "BODY"))
        );
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
