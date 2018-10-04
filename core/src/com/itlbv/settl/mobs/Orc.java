package com.itlbv.settl.mobs;

import com.itlbv.settl.MobConstants;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public abstract class Orc extends Mob{
    public Orc(float x, float y, MobObjectType type, String bhvTree) {
        super(x, y, type,
                MobConstants.ORC_WIDTH,
                MobConstants.ORC_HEIGHT,
                MobConstants.ORC_BODY_WIDTH,
                MobConstants.ORC_BODY_HEIGHT,
                MobConstants.ORC_SPEED,
                bhvTree);
    }
}
