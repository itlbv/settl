package com.itlbv.settl.mobs;

import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.utils.MobConstants;

public class OrcShaman extends Orc {
    private static final MobObjectType MOB_TYPE = MobObjectType.ORC_SHAMAN;

    public OrcShaman(float x, float y) {
        super(x, y, MOB_TYPE, "bhvTrees/humanKnight.btree");
        createSensor(MobConstants.ORC_SENSOR_WIDTH, MobConstants.ORC_SENSOR_HEIGHT);
    }
}
