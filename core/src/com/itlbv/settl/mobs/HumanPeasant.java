package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public class HumanPeasant extends Human {
    private static final MobObjectType MOB_TYPE = MobObjectType.HUMAN_PEASANT;
    public HumanPeasant(float x, float y, TextureRegion texture) {
        super(x, y, MOB_TYPE, texture, "bhvTrees/humanPeasant.btree");
    }
}
