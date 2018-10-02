package com.itlbv.settl.mobs;

import com.itlbv.settl.enumsObjectType.MobObjectType;

public class HumanPeasant extends Human {
    private static final MobObjectType MOB_TYPE = MobObjectType.HUMAN_PEASANT;
    public HumanPeasant(float x, float y) {
        super(x, y, MOB_TYPE, "bhvTrees/humanPeasant.btree");
    }
}
