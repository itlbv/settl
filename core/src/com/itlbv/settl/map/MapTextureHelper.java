package com.itlbv.settl.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.itlbv.settl.enumsObjectType.MapObjectType;

public class MapTextureHelper {
    private static TextureRegion grass01 = new TextureRegion(new Texture("textures/mapTiles/grass/grass01.png"));
    private static TextureRegion grass02 = new TextureRegion(new Texture("textures/mapTiles/grass/grass02.png"));
    private static TextureRegion grass03 = new TextureRegion(new Texture("textures/mapTiles/grass/grass03.png"));
    private static TextureRegion grass04 = new TextureRegion(new Texture("textures/mapTiles/grass/grass04.png"));

    public static TextureRegion getTileTexture(MapObjectType type) {
        switch (type) {
            case GRASS:
                return getGrassTexture();
            case WATER:
                return getWaterTexture();
            default:
                return getGrassTexture();
        }
    }

    private static TextureRegion getGrassTexture() {
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

    private static TextureRegion getWaterTexture() {
        return new TextureRegion(new Texture("water01.png"));
    }

}
