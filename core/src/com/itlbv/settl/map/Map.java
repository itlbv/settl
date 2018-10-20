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
    private Array<Node> nodes;

    public Map(String path) {
        map = new TmxMapLoader().load(path);
        tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        tileLayer.setOffsetX(-GameConstants.TILE_SIZE_PXL/2);
        tileLayer.setOffsetY(GameConstants.TILE_SIZE_PXL/2);
        nodes = new Array<>();
    }

    public Body mapSensor;
    public void init() {
        mapSensor = BodyFactory.createSensorForMap();
        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                Cell cell = tileLayer.getCell(x, y);
                boolean passable = (boolean) cell.getTile().getProperties().get("passable");
                if (!passable) BodyFactory.createBodyForMap(x, y, cell.getTile());
                createNode(x, y, passable);
            }
        }
        initPathfindingGraph();
    }

    private void initPathfindingGraph() {
        int width = tileLayer.getWidth();
        int height = tileLayer.getHeight();
        for (int x = 0; x < width; x++) {
            int columnX = x * height;
            for (int y = 0; y < height; y++) {
                Node node = nodes.get(columnX + y);
                if (x > 0) addConnection(node, -1, 0);
                if (y > 0) addConnection(node, 0, -1);
                if (x < width - 1) addConnection(node, 1, 0);
                if (y < height - 1) addConnection(node, 0, 1);
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

    private void createNode(int x, int y, boolean passable) {
        nodes.add(new Node(x, y, passable));
    }

    private Node getNode(int x, int y) {
        return nodes.get(x * tileLayer.getHeight() + y);
    }

    public Node getNodeFromPosition(Vector2 position) {
        return getNode((int)position.x, (int)position.y);
    }

    public Vector2 getNodePosition(int index) {
        return nodes.get(index).getPosition();
    }

    @Override
    public int getIndex(Node node) {
        return node.getX() * tileLayer.getHeight() + node.getY();
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
        return tileLayer.getWidth();
    }

    public int getHeight() {
        return tileLayer.getHeight();
    }
}
