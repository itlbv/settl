package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public class OrcShaman extends Orc {
    private static final MobObjectType MOB_TYPE = MobObjectType.ORC_SHAMAN;
    private static final float SENSOR_WIDTH = 2f;
    private static final float SENSOR_HEIGHT = 2f;

    public OrcShaman(float x, float y) {
        super(x, y, MOB_TYPE, "bhvTrees/humanPeasant.btree");
        createSensor(SENSOR_WIDTH, SENSOR_HEIGHT);
    }
}
