package com.itlbv.settl.mobs.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.itlbv.settl.Game;
import com.itlbv.settl.GameWorld;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.map.Node;
import com.itlbv.settl.mobs.HumanKnight;
import com.itlbv.settl.mobs.HumanPeasant;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.mobs.OrcShaman;
import com.itlbv.settl.util.BodyFactory;

public class MobFactory {
    public static Mob createMobAtRandomPosition(boolean rightMapSide, MobObjectType type) {
        int mapWidth = Game.map.getWidth();
        int mapHeight = Game.map.getHeight();
        int randX, randY;
        do {
            randX = MathUtils.random(mapWidth/2 - 1);
            randY = MathUtils.random(mapHeight - 1);
            if (rightMapSide) randX += mapWidth/2;
        } while (mapTileIsNotPassable(randX, randY));
        return createMob(randX, randY, type);
    }

    private static boolean mapTileIsNotPassable(int x, int y) {
        return !Game.map.getNodeFromPosition(new Vector2(x, y)).isPassable();
    }

    private static Mob createMob(float x, float y, MobObjectType type) {
        Mob mob;
        String bhvTree;
        float bodyWidth, bodyHeight, sensorWidth, sensorHeight;
        switch (type) {
            case HUMAN_PEASANT:
                mob = new HumanPeasant(x, y);
                bhvTree = "bhvTrees/humanKnight.btree";
                bodyWidth = MobConstants.HUMAN_BODY_WIDTH;
                bodyHeight = MobConstants.HUMAN_BODY_HEIGHT;
                sensorWidth = MobConstants.HUMAN_SENSOR_WIDTH;
                sensorHeight = MobConstants.HUMAN_SENSOR_HEIGHT;
                break;
            case HUMAN_KNIGHT:
                mob =  new HumanKnight(x, y);
                bhvTree = "bhvTrees/humanKnight.btree";
                bodyWidth = MobConstants.HUMAN_BODY_WIDTH;
                bodyHeight = MobConstants.HUMAN_BODY_HEIGHT;
                sensorWidth = MobConstants.HUMAN_SENSOR_WIDTH;
                sensorHeight = MobConstants.HUMAN_SENSOR_HEIGHT;
                break;
            case ORC_SHAMAN:
                mob = new OrcShaman(x, y);
                bhvTree = "bhvTrees/humanKnight.btree";
                bodyWidth = MobConstants.ORC_BODY_WIDTH;
                bodyHeight = MobConstants.ORC_BODY_HEIGHT;
                sensorWidth = MobConstants.ORC_SENSOR_WIDTH;
                sensorHeight = MobConstants.ORC_SENSOR_HEIGHT;
                break;
            default:
                mob = new HumanPeasant(x, y);
                bhvTree = "bhvTrees/humanKnight.btree";
                bodyWidth = MobConstants.HUMAN_BODY_WIDTH;
                bodyHeight = MobConstants.HUMAN_BODY_HEIGHT;
                sensorWidth = MobConstants.HUMAN_SENSOR_WIDTH;
                sensorHeight = MobConstants.HUMAN_SENSOR_HEIGHT;
        }
        Body mobBody = BodyFactory.createBody(BodyDef.BodyType.DynamicBody, bodyWidth, bodyHeight, mob, false);
        mob.setBody(mobBody, bodyWidth, bodyHeight);
        Body mobSensor = BodyFactory.createBody(BodyDef.BodyType.DynamicBody, sensorWidth, sensorHeight, mob, true);
        mob.setSensor(mobSensor);
        mob.setBhvTree(bhvTree);
        return mob;
    }
}
