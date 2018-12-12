package com.itlbv.settl.mobs.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.itlbv.settl.Game;
import com.itlbv.settl.enumsObjectType.MobObjectType;
import com.itlbv.settl.mobs.Mob;
import com.itlbv.settl.util.BodyFactory;
import org.slf4j.Logger;

import static com.itlbv.settl.mobs.utils.MobConstants.MOB_RENDER_HEIGHT;
import static com.itlbv.settl.mobs.utils.MobConstants.MOB_RENDER_WIDTH;
import static com.itlbv.settl.mobs.utils.MobConstants.MOB_SPEED;
import static org.slf4j.LoggerFactory.getLogger;

public class MobFactory {

    private static final Logger log = getLogger(MobFactory.class);

    public static void createMobAtRandomPosition(boolean rightMapSide, MobObjectType type) {
        int mapWidth = Game.map.getWidth();
        int mapHeight = Game.map.getHeight();
        int randX, randY;
        do {
            randX = MathUtils.random(mapWidth/2 - 1);
            randY = MathUtils.random(mapHeight - 1);
            if (rightMapSide) randX += mapWidth/2;
        } while (mapTileIsNotPassable(randX, randY));
        createMob(randX, randY, type);
    }

    private static boolean mapTileIsNotPassable(int x, int y) {
        return !Game.map.getNode(x, y).isPassable();
    }

    private static void createMob(int x, int y, MobObjectType type) {
        Mob mob = new Mob(type, "bhvTrees/humanKnight.btree", MOB_SPEED, MOB_RENDER_WIDTH, MOB_RENDER_HEIGHT);
        Body mobBody = BodyFactory.createAndGetMobBody(x, y, mob, false);
        Body mobSensor = BodyFactory.createAndGetMobBody(x, y, mob, true);
        mob.setBody(mobBody);
        mob.setSensor(mobSensor);
        mob.updateRenderPosition();
        Game.mobs.add(mob);
        mob.setId(Game.mobs.size());
        log.info(mob + " created");
    }
}
