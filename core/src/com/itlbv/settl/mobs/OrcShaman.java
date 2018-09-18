package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public class OrcShaman extends Orc {
    private static final MobObjectType MOB_TYPE = MobObjectType.ORC_SHAMAN;


    public OrcShaman(float x, float y, TextureRegion texture) {
        super(x, y, MOB_TYPE, texture, "bhvTrees/humanPeasant.btree");
    }
}
