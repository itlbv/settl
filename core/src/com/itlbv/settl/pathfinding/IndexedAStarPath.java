package com.itlbv.settl.pathfinding;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BinaryHeap;
import com.itlbv.settl.map.Map;
import com.itlbv.settl.map.Tile;

public class IndexedAStarPath implements PathFinder<Tile> {
    private final Map graph;
    private final NodeRecord[] nodeRecords;
    private final BinaryHeap<NodeRecord> openList;
    private NodeRecord current;

    private int searchId; //The unique ID for each search run. Used to mark nodes.

    public IndexedAStarPath(Map graph) {
        this.graph = graph;
        nodeRecords = new NodeRecord[graph.getNodes().size];
        openList = new BinaryHeap<>();
    }

    @Override
    public boolean searchNodePath(Tile startNode, Tile targetNode, Heuristic<Tile> heuristic, GraphPath<Tile> outPath) {
        performAStarSearch(startNode, targetNode, heuristic);
        if (goalIsUnreachable(targetNode)) {
            return false;
        }
        else {
            generateNodePath(startNode, outPath);
            return true;
        }
    }

    private void performAStarSearch(Tile startNode, Tile targetNode, Heuristic<Tile> heuristic) {
        initSearch(startNode, targetNode, heuristic);
        do {
            retrieveSmallestCostNode();
            if (goalReached(targetNode)) return;
            visitChildren(targetNode, heuristic);
        }
        while (openList.size > 0);
    }

    private boolean goalIsUnreachable(Tile endNode)
    {
        return current.node != endNode;
    }

    protected void initSearch (Tile startNode, Tile targetNode, Heuristic<Tile> heuristic) {
        incrementSearchId();
        initializeNodeList();
        initializeStartNode(startNode, targetNode, heuristic);
        initCurrentNode();
    }

    private void incrementSearchId()
    {
        if (++searchId < 0) searchId = 1;
    }
    private void initializeNodeList()
    {
        openList.clear();
    }
    private void initializeStartNode(Tile startNode, Tile targetNode, Heuristic<Tile> heuristic)
    {
        NodeRecord startRecord = getNodeRecord(startNode);
        startRecord.node = startNode;
        startRecord.connection = null;
        startRecord.cost = 0;
        addToNodeList(startRecord, heuristic.estimate(startNode, targetNode));
    }

    private NodeRecord getNodeRecord (Tile node) {
        int index = graph.getNodeIndex(node);
        NodeRecord nodeRecord = nodeRecords[index];

        if (nodeRecord != null)
        {
            if (nodeRecord.searchId != searchId)
            {
                nodeRecord.category = NodeRecord.Category.UNVISITED;
                nodeRecord.searchId = searchId;
            }

            return nodeRecord;
        }

        nodeRecord = nodeRecords[index] = new NodeRecord();
        nodeRecord.node = node;
        nodeRecord.searchId = searchId;

        return nodeRecord;
    }

    private void addToNodeList (NodeRecord nodeRecord, float estimatedTotalCost) {
        openList.add(nodeRecord, estimatedTotalCost);
        nodeRecord.category = NodeRecord.Category.OPEN;
    }

    private void initCurrentNode()
    {
        current = null;
    }

    private void retrieveSmallestCostNode() {
        current = openList.pop();
        current.category = NodeRecord.Category.CLOSED;
    }

    private boolean goalReached(Tile endNode) {
        return current.node == endNode;
    }

    private void visitChildren (Tile endNode, Heuristic<Tile> heuristic) {
        for(NodeConnection connection : currentNodeOutgoingConnections())
        {
            Tile node = connection.getTargetNode();
            float cost = current.cost + connection.getCost();
            float nodeHeuristic;
            NodeRecord nodeRecord = getNodeRecord(node);

            if (nodeRecord.category == NodeRecord.Category.CLOSED) {
                if (noShorterRoute(nodeRecord, cost)) continue;
                nodeHeuristic = nodeRecord.heuristic();
            } else if (nodeRecord.category == NodeRecord.Category.OPEN) {
                if (noShorterRoute(nodeRecord, cost)) continue;

                // Remove it from the open list (it will be re-added with the new cost)
                openList.remove(nodeRecord);
                nodeHeuristic = nodeRecord.heuristic();
            } else {// the node is unvisited
                nodeHeuristic = heuristic.estimate(node, endNode);
            }

            // Update node record's cost and connection
            nodeRecord.cost = cost;
            nodeRecord.connection = connection;

            // Add it to the node list with the estimated total cost
            addToNodeList(nodeRecord, cost + nodeHeuristic);
        }
    }

    private boolean noShorterRoute(NodeRecord nodeRecord, float cost)
    {
        return nodeRecord.cost <= cost;
    }

    private Array<NodeConnection> currentNodeOutgoingConnections() {
        return graph.getConnections(current.node);
    }

    private void generateNodePath (Tile start, GraphPath<Tile> outPath) {
        // Work back along the path, accumulating nodes
        while (current.connection != null)
        {
            outPath.add(current.node);
            current = nodeRecords[graph.getNodeIndex(current.connection.getStartNode())];
        }

        outPath.add(start);

        // Reverse the path
        outPath.reverse();
    }

    @Override public boolean searchConnectionPath(Tile node, Tile n1, Heuristic<Tile> heuristic, GraphPath<com.badlogic.gdx.ai.pfa.Connection<Tile>> graphPath) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    @Override public boolean search (PathFinderRequest<Tile> request, long timeToRun) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
