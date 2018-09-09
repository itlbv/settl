package com.itlbv.settl.map;

import com.badlogic.gdx.graphics.Texture;
import com.itlbv.settl.enums.MapObjectType;

import java.util.ArrayList;


public class Map {
    private static Map instance = new Map();
    public static Map getInstance() {return instance;}
    private ArrayList<ArrayList<Tile>> tiles;

    private Map(){initialize();}
    private static final int TILE_SIZE_PXL = 8;
    private static final int MAP_SIZE = 60;

    private void initialize() {
        tiles = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < MAP_SIZE; i++) {
            tiles.add(new ArrayList<Tile>());
        }
    }

    public void addTileToRow(MapObjectType type, int row) {
        //TODO refactoring
        Texture texture = MapTextureHelper.getTileTexture(type);
        float x = TILE_SIZE_PXL * row;
        float y = TILE_SIZE_PXL * tiles.get(row).size();
        Tile tile = new Tile(x, y, texture, type);
        tiles.get(row).add(tile);
    }

    //***Getters & setters***
    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }
}
