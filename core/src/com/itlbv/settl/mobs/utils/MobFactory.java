package com.itlbv.settl.mobs.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.itlbv.settl.Game;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.util.BodyFactory;

import static com.itlbv.settl.mobs.utils.MobConstants.MOB_RENDER_HEIGHT;
import static com.itlbv.settl.mobs.utils.MobConstants.MOB_RENDER_WIDTH;
import static com.itlbv.settl.mobs.utils.MobConstants.MOB_SPEED;

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
        return !Game.map.getNode(x, y).isPassable();
    }

    private static Mob createMob(int x, int y, MobObjectType type) {
        Mob mob = new Mob(type, "bhvTrees/humanKnight.btree", MOB_SPEED, MOB_RENDER_WIDTH, MOB_RENDER_HEIGHT);
        Body mobBody = BodyFactory.createAndGetMobBody(x, y, mob, false);
        Body mobSensor = BodyFactory.createAndGetMobBody(x, y, mob, true);
        mob.setBody(mobBody);
        mob.setSensor(mobSensor);
        mob.updateRenderPosition();

        switch (type) {
            case HUMAN_KNIGHT:
                Game.humans.add(mob);
                mob.setStringId(Game.humans.size());
                break;
            case ORC_SHAMAN:
                Game.orcs.add(mob);
                mob.setStringId(Game.orcs.size());
                break;
            default:
                break;
        }
        return mob;
    }
}
