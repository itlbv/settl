package com.itlbv.settl.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.enumsObjectType.MapObjectType;


public class Map implements IndexedGraph<Tile> {
    private static Map instance = new Map();
    public static Map getInstance() {return instance;}
    private Array<Tile> nodes;
    private Array<Array<Tile>> tiles;

    private static final int MAP_SIZE = 60;

    private Map(){initialize();}

    private void initialize() {
        nodes = new Array<Tile>();
        tiles = new Array<Array<Tile>>();
        for (int i = 0; i < MAP_SIZE; i++) {
            tiles.add(new Array<Tile>());
        }
    }

    public void assignCodes() {
        for (int row = 0; row < MAP_SIZE; row++) {
            for (int column = 0; column < MAP_SIZE; column++) {
                Tile tile = tiles.get(row).get(column);
                if (tile.isCollidable()) {
                    continue;
                }
                String code = calculateCode(row, column);
                tile.setCode(code);
            }
        }
    }

    private String calculateCode(int row, int column) {
        StringBuilder sb = new StringBuilder();
        sb.append(getCharFromTile(row, column, -1, -1));
        sb.append(getCharFromTile(row, column, -1, 0));
        sb.append(getCharFromTile(row, column, -1, +1));
        sb.append(getCharFromTile(row, column, 0, +1));
        sb.append(getCharFromTile(row, column, +1, +1));
        sb.append(getCharFromTile(row, column, +1, 0));
        sb.append(getCharFromTile(row, column, +1, -1));
        sb.append(getCharFromTile(row, column, 0, -1));
        return sb.toString();
    }

    private char getCharFromTile(int row, int column, int xOffset, int yOffset) {
        row += xOffset;
        column += yOffset;
        if (row < 0 || column < 0 || row == MAP_SIZE || column == MAP_SIZE) {
            return '0';
        }

        if (tiles.get(row).get(column).isCollidable()) {
            return '1';
        } else {
            return '0';
        }
    }

    public void addTileToRow(MapObjectType type, int row) {
        TextureRegion texture = MapTextureHelper.getTileTexture(type);
        int x = row;
        int y = tiles.get(row).size;
        Tile tile = new Tile(x, y, type, texture);
        createBodyIfNotPassable(tile);
        tiles.get(row).add(tile);
        nodes.add(tile);
    }

    private void createBodyIfNotPassable(Tile tile) {
        if (tile.getType().passable) {
            return;
        }
        tile.createBody(BodyType.StaticBody, 1, 1); //TODO magical numbers
        tile.setCollidable(true);
    }

    /*
    **Graph implementation
     */
    public void initGraph() {
        //TODO wtf is going on here?
        int width = MAP_SIZE;
        int height = MAP_SIZE;
        for (int x = 0; x < width; x++) {
            int idx = x * height;
            for (int y = 0; y < height; y++) {
                Tile node = nodes.get(idx + y);
                if (x > 0) addConnection(node, -1, 0);
                if (y > 0) addConnection(node, 0, -1);
                if (x < width - 1) addConnection(node, 1, 0);
                if (y < height - 1) addConnection(node, 0, 1);
            }
        }
    }

    private void addConnection (Tile node, int xOffset, int yOffset) {
        Tile target = getNode(node.getX() + xOffset, node.getY() + yOffset);
        if (!target.isCollidable()) {
            getConnections(node).add(new DefaultConnection<Tile>(node, target));
        }
    }

    private Tile getNode(float xf, float yf) {
        int x = (int)xf;
        int y = (int)yf;
        return tiles.get(x).get(y);
    }

    public Array<Connection<Tile>> getConnections(Tile node) {
        return node.getConnections();
    }

    @Override
    public int getIndex(Tile node) {
        return (int) (node.getX() * MAP_SIZE + node.getY());
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    public Tile getTileFromPosition(Vector2 position) { //TODO refactoring
        float posX = position.x;
        float posY = position.y;
        int nodeX = (int)posX;
        int nodeY = (int)posY;
        return tiles.get(nodeX).get(nodeY);
    }

    //***Getters & setters***
    public Array<Array<Tile>> getTiles() {
        return tiles;
    }
}
