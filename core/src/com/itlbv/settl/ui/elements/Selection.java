package com.itlbv.settl.ui.elements;

import com.badlogic.gdx.math.Rectangle;
import com.itlbv.settl.mob.Mob;

public class Selection extends Rectangle {
    public void setToMob(Mob mob) {
        set(mob.getPosition().x - 0.5f,
                mob.getPosition().y,
                1f, 1.5f);
    }
}
