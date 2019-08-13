package com.itlbv.settl.mob.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.itlbv.settl.Game;
import com.itlbv.settl.mob.Mob;
import com.itlbv.settl.util.BodyFactory;
import org.slf4j.Logger;

import static com.itlbv.settl.mob.util.MobConstants.MOB_RENDER_HEIGHT;
import static com.itlbv.settl.mob.util.MobConstants.MOB_RENDER_WIDTH;
import static org.slf4j.LoggerFactory.getLogger;

public class MobFactory {

    private static final Logger log = getLogger(MobFactory.class);

    public static void createMobAtRandomPosition(boolean rightMapSide, MobType type) {
        Vector2 randomPosition = getRandomPosition(rightMapSide);
        createMob(randomPosition, type);
    }

    private static void createMob(Vector2 position, MobType type) {
        Mob mob = new Mob(type, "bhvTrees/warrior.btree");
        BodyFactory.createBodyAndSensorForMob(position, mob);
        mob.getSprite().setSize(MOB_RENDER_WIDTH, MOB_RENDER_HEIGHT);
        Game.mobs.add(mob);
        mob.setId(Game.mobs.size());
        mob.update();
        log.info(mob + " created");
    }

    private static Vector2 getRandomPosition(boolean rightMapSide) {
        int mapWidth = Game.map.getWidth();
        int mapHeight = Game.map.getHeight();
        int x, y;

        do {
            x = MathUtils.random(mapWidth/2 - 1);
            y = MathUtils.random(mapHeight - 1);
            if (rightMapSide){
                x += mapWidth/2;
            }
        } while (mapTileIsNotPassable(x, y));

        return new Vector2(x, y);
    }

    private static boolean mapTileIsNotPassable(int x, int y) {
        return !Game.map.getNodeFromCoord(x, y).isPassable();
    }
}
