package com.itlbv.settl.map;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.itlbv.settl.util.BodyFactory;
import com.itlbv.settl.GameConstants;

public class Map implements IndexedGraph<Node> {
    private TiledMap map;
    private TiledMapTileLayer tileLayer;
    private int mapWidth, mapHeight;
    private Array<Node> nodes;
    public Body mapSensor;

    public Map(String path) {
        map = new TmxMapLoader().load(path);
        tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileLayer.setOffsetX(-GameConstants.TILE_TEXTURE_SIZE_PXL /2);
        tileLayer.setOffsetY(GameConstants.TILE_TEXTURE_SIZE_PXL /2);
        mapWidth = tileLayer.getWidth();
        mapHeight = tileLayer.getHeight();
        nodes = new Array<>();
    }

    public void init() {
        mapSensor = BodyFactory.createAndGetMapSensorForObjectsFrictionJoints();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Cell cell = tileLayer.getCell(x, y);
                boolean passable = (boolean) cell.getTile().getProperties().get("passable");
                if (!passable) BodyFactory.createMapTileBody(x, y, cell.getTile());
                createNode(x, y, passable);
            }
        }
        initPathfindingGraph();
    }

    private void initPathfindingGraph() {
        for (int x = 0; x < mapWidth; x++) {
            int columnX = x * mapHeight;
            for (int y = 0; y < mapHeight; y++) {
                Node node = nodes.get(columnX + y);
                if (x > 0) addConnection(node, -1, 0);
                if (y > 0) addConnection(node, 0, -1);
                if (x < mapWidth - 1) addConnection(node, 1, 0);
                if (y < mapHeight - 1) addConnection(node, 0, 1);
                calculateAndSetCode(x, y, node);
            }
        }
    }

    private void addConnection (Node node, int xOffset, int yOffset) {
        Node targetNode = getNode(node.getX() + xOffset, node.getY() + yOffset);
        boolean targetNodePassable = (boolean) tileLayer.getCell(targetNode.getX(), targetNode.getY())
                                                .getTile()
                                                .getProperties().get("passable");
        if (targetNodePassable) getConnections(node).add(new DefaultConnection<>(node, targetNode));
    }

    private void calculateAndSetCode(int row, int column, Node node) {
        StringBuilder sb = new StringBuilder();
        sb.append(getCharFromTile(row, column, -1, -1));
        sb.append(getCharFromTile(row, column, -1, 0));
        sb.append(getCharFromTile(row, column, -1, +1));
        sb.append(getCharFromTile(row, column, 0, +1));
        sb.append(getCharFromTile(row, column, +1, +1));
        sb.append(getCharFromTile(row, column, +1, 0));
        sb.append(getCharFromTile(row, column, +1, -1));
        sb.append(getCharFromTile(row, column, 0, -1));
        node.setCode(sb.toString());
    }

    private char getCharFromTile(int x, int y, int xOffset, int yOffset) {
        x += xOffset;
        y += yOffset;
        if (x < 0 || y < 0 || x == mapWidth || y == mapHeight) {
            return '0';
        }
        if (getNode(x, y).isPassable()) {
            return '0';
        } else {
            return '1';
        }
    }

    private void createNode(int x, int y, boolean passable) {
        nodes.add(new Node(x, y, passable));
    }

    public Node getNode(int x, int y) {
        return nodes.get(x * mapHeight + y);
    }

    public Node getNodeFromPosition(Vector2 position) {
        return getNode((int)position.x, (int)position.y);
    }

    public Vector2 getNodePosition(int index) {
        return nodes.get(index).getPosition();
    }

    @Override
    public int getIndex(Node node) {
        return node.getX() * mapHeight + node.getY();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }

    public TiledMap getMap() {
        return map;
    }

    public int getWidth() {
        return mapWidth;
    }

    public int getHeight() {
        return mapHeight;
    }
}
