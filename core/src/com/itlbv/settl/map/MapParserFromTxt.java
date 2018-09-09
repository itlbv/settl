package com.itlbv.settl.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.itlbv.settl.enums.MapObjectType;

public class MapParserFromTxt {
    private static Map map = Map.getInstance();

    public static void createMap(){ //TODO awful code, needs refactoring
        try {
            FileHandle fileHandler = Gdx.files.internal("levels/level01.txt");
            String line = fileHandler.readString();
            int rowNumber = 0;
            for (char c : line.toCharArray()) {
                if (c == '\n') {
                    rowNumber++;
                    continue;
                }
                if (c == '\r') {
                    continue;
                }
                map.addTileToRow(getTypeOfTile(c), rowNumber);
            }
        } catch (Exception e) {

        }
    }

    private static MapObjectType getTypeOfTile(char c) {
        switch (c) {
            case 'g':
                return MapObjectType.GRASS;
            case 'w':
                return MapObjectType.WATER;
            case 't':
                return MapObjectType.TREE;
            default:
                return MapObjectType.GRASS;
        }
    }
}
