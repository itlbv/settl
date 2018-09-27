package com.itlbv.settl;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.itlbv.settl.mobs.Mob;

public class CollisionHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Mob m1 = (Mob) contact.getFixtureA().getBody().getUserData();
        Mob m2 = (Mob) contact.getFixtureB().getBody().getUserData();
        System.out.println(m1 + " CONTACTING " + m2);

        if (m1.getTarget() == m2) {
            m1.setTargetWithinReach(true);
            System.out.println("Target in reach");
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        GameObject o1 = (GameObject) contact.getFixtureA().getBody().getUserData();
        GameObject o2 = (GameObject) contact.getFixtureB().getBody().getUserData();
        if (o1 == o2) {
            //contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
