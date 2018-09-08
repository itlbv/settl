package com.itlbv.settl.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.enums.MapObjectType;

public class MapTextureHelper {
    private static Texture grass01 = new Texture("textures/mapTiles/grass/grass01.png");
    private static Texture grass02 = new Texture("textures/mapTiles/grass/grass02.png");
    private static Texture grass03 = new Texture("textures/mapTiles/grass/grass03.png");
    private static Texture grass04 = new Texture("textures/mapTiles/grass/grass04.png");

    public static Texture getTileTexture(MapObjectType type) {
        switch (type) {
            case GRASS:
                return getGrassTexture();
            case WATER:
                return getWaterTexture();
            default:
                return getGrassTexture();
        }
    }

    private static Texture getGrassTexture() {
        //TODO explaining
        int rnd = MathUtils.random(20);
        switch (rnd) {
            case 1:
                return grass01;
            case 2:
                return grass02;
            case 3:
                return grass03;
            case 4:
                return grass04;
            default:
                return grass01;
        }
    }

    private static Texture getWaterTexture() {
        return null;
    }

}
