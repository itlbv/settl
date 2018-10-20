package com.itlbv.settl.mobs;

import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.utils.MobConstants;

public abstract class Human extends Mob {
    public Human(float x, float y, MobObjectType type) {
        super(x, y, type,
                MobConstants.HUMAN_WIDTH,
                MobConstants.HUMAN_HEIGHT,
                MobConstants.HUMAN_BODY_WIDTH,
                MobConstants.HUMAN_BODY_HEIGHT,
                MobConstants.HUMAN_SPEED);
    }
}
