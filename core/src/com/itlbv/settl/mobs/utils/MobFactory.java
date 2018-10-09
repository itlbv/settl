package com.itlbv.settl.mobs.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.mobs.HumanKnight;
import com.itlbv.settl.mobs.HumanPeasant;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.OrcShaman;

public class MobFactory {
    public static Mob createMob(float x, float y, MobObjectType type) {
        switch (type) {
            case HUMAN_PEASANT:
                return new HumanPeasant(x, y);
            case HUMAN_KNIGHT:
                return new HumanKnight(x, y);
            case ORC_SHAMAN:
                return new OrcShaman(x, y);
            default:
                return new HumanPeasant(x, y);
        }
    }

    public static Mob createMobAtRandomPosition(boolean upperMapSide, MobObjectType type) {
        float randX = MathUtils.random(25);
        float randY = MathUtils.random(55);
        if (upperMapSide) {
            randX += 25;
        }
        while (Map.getInstance().getTileFromPosition(new Vector2(randX, randY)).isCollidable()) {
            randX = MathUtils.random(25);
            randY = MathUtils.random(55);
            if (upperMapSide) {
                randX += 25;
            }
        }

        return createMob(randX, randY, type);
    }
}
