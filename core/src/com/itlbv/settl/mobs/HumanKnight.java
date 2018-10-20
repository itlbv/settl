package com.itlbv.settl.mobs;

import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.utils.MobConstants;

public class HumanKnight extends Human {
    private static final MobObjectType MOB_TYPE = MobObjectType.HUMAN_KNIGHT;

    public HumanKnight(float x, float y) {
        super(x, y,  MOB_TYPE);
    }
}
