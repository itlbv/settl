package com.itlbv.settl.mob.action;

import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;

import static com.itlbv.settl.mob.action.ActionUtil.removeCurrentAction;
import static com.itlbv.settl.mob.animation.util.MobAnimationType.GET_HIT;

public class Defend extends Action {

    private float holdTimer;

    Defend(Mob owner, Mob attacker) {
        super(owner, ActionType.DEFEND, attacker);
    }

    @Override
    public void run() {
        holdTimer += Game.DELTA_TIME;
        if (holdTimer < 0.3f) {
            return;
        }
        holdTimer = 0;

        if (MathUtils.randomBoolean(.5f)) {
            //System.out.println(owner.toString() + " GET HIT");
            owner.getAnimation().setIncoming(GET_HIT);
            removeCurrentAction(owner);
            minusHitpoint();
        } else {
            //System.out.println(owner.toString() + " DEFEND");
            removeCurrentAction(owner);
        }
    }

    private void minusHitpoint() {
        owner.setHitpoints(owner.getHitpoints() - 1);
        System.out.println(owner.toString() + " hp:" + owner.getHitpoints());
        checkHealthAndDie();
    }

    private void checkHealthAndDie() {
        if (owner.getHitpoints() > 0) {
            return;
        }
        owner.setAlive(false);
        owner.getBody().destroyFixture(owner.getBody().getFixtureList().get(0));
        owner.getSensor().destroyFixture(owner.getSensor().getFixtureList().get(0));
    }
}
