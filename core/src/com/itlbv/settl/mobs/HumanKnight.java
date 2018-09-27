package com.itlbv.settl.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.itlbv.settl.enumsObjectType.MobObjectType;

public class HumanKnight extends Human {
    private static final MobObjectType MOB_TYPE = MobObjectType.HUMAN_KNIGHT;
    private static final float SENSOR_WIDTH = 2f;
    private static final float SENSOR_HEIGHT = 2f;

    public HumanKnight(float x, float y) {
        super(x, y,  MOB_TYPE,"bhvTrees/humanKnight.btree");
        createSensor(SENSOR_WIDTH,SENSOR_HEIGHT);
    }
}
