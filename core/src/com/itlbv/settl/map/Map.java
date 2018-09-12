package com.itlbv.settl.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.enumsObjectType.MapObjectType;
import com.itlbv.settl.pathfinding.NodeConnection;


public class Map{
    private static Map instance = new Map();
    public static Map getInstance() {return instance;}
    private Map(){initialize();}

    private Array<Tile> nodes;
    private Array<Array<Tile>> tiles;
    private static final int TILE_SIZE_PXL = 8;
    private static final int MAP_SIZE = 60;

    private void initialize() {
        nodes = new Array<Tile>();
        tiles = new Array<Array<Tile>>();
        for (int i = 0; i < MAP_SIZE; i++) {
            tiles.add(new Array<Tile>());
        }
    }

    public void addTileToRow(MapObjectType type, int row) {
        //TODO refactoring
        TextureRegion texture = MapTextureHelper.getTileTexture(type);
        float x = TILE_SIZE_PXL * row;
        float y = TILE_SIZE_PXL * tiles.get(row).size;
        Tile tile = new Tile(x, y, texture, type);
        createBodyIfNotPassable(tile);
        tile.setNodeX(row);
        tile.setNodeY(tiles.get(row).size);
        tiles.get(row).add(tile);
        nodes.add(tile);
    }

    private void createBodyIfNotPassable(Tile tile) {
        if (tile.getType().passable) {
            return;
        }
        tile.createBody(BodyType.StaticBody, TILE_SIZE_PXL, TILE_SIZE_PXL);
        tile.setCollidable(true);
    }

    /*
    **Graph implementation
     */
    public void initGraph() {
        //TODO wtf is going on here?
        int width = MAP_SIZE;
        int height = MAP_SIZE;
        for (int x = 0; x < width; x++)
        {
            int idx = x * height;

            for (int y = 0; y < height; y++)
            {
                Tile node = nodes.get(idx + y);

                if (x > 0) addConnection(node, -1, 0);
                if (y > 0) addConnection(node, 0, -1);
                if (x < width - 1) addConnection(node, 1, 0);
                if (y < height - 1) addConnection(node, 0, 1);
            }
        }
    }

    private void addConnection (Tile node, int xOffset, int yOffset) {
        Tile target = getNode(node.getNodeX() + xOffset, node.getNodeY() + yOffset);
        if (!target.isCollidable()) {
            getConnections(node).add(new NodeConnection(node, target));
        }
    }

    private Tile getNode(int x, int y) {
        return tiles.get(x).get(y);
    }

    public Array<NodeConnection> getConnections(Tile node) {
        return node.getConnections();
    }

    public int getNodeIndex(Tile node) {
        return node.getNodeX() * MAP_SIZE + node.getNodeY(); //TODO wtf this code is doing ?
    }

    public Tile getTileFromPosition(Vector2 position) {
        float posX = position.x;
        float posY = position.y;
        int nodeX = (int)posX/TILE_SIZE_PXL + 1;
        int nodeY = (int)posY/TILE_SIZE_PXL + 1;
        return tiles.get(nodeX).get(nodeY);
    }

    //***Getters & setters***
    public Array<Tile> getNodes() {
        return nodes;
    }

    public Array<Array<Tile>> getTiles() {
        return tiles;
    }
}
