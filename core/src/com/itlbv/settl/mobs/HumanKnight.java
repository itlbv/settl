package com.itlbv.settl.mobs;

import com.itlbv.settl.MobConstants;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public class HumanKnight extends Human {
    private static final MobObjectType MOB_TYPE = MobObjectType.HUMAN_KNIGHT;

    public HumanKnight(float x, float y) {
        super(x, y,  MOB_TYPE,"bhvTrees/humanKnight.btree");
        createSensor(MobConstants.HUMAN_SENSOR_WIDTH, MobConstants.HUMAN_SENSOR_HEIGHT);
    }
}
