package com.itlbv.settl.mob.action.actions;

import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.mob.action.Action;
import com.itlbv.settl.mob.movement.util.Target;

import static com.itlbv.settl.mob.action.util.ActionUtil.removeCurrentAction;
import static com.itlbv.settl.mob.animation.util.MobAnimationType.GET_HIT;

public class Defend extends Action {

    private float holdTimer;

    public Defend(Mob owner, Target target) {
        super(owner, ActionType.DEFEND, target);
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
            owner.animation.setIncoming(GET_HIT);
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
