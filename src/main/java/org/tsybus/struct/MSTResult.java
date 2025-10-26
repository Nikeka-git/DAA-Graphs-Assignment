package org.tsybus.struct;

import java.util.List;
import org.tsybus.struct.Edge;

public class MSTResult {
    private String algorithm;
    private List<Edge> edges;
    private int vertexCount;
    private int edgeCount;
    private long totalCost;
    private OperationCounter ops;
    private double timeMs;

    public MSTResult() {}

    public List<Edge> getEdges() { return edges; }
    public void setEdges(List<Edge> edges) { this.edges = edges; }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void setEdgeCount(int edgeCount) {
        this.edgeCount = edgeCount;
    }

    public long getTotalCost() { return totalCost; }
    public void setTotalCost(long totalCost) { this.totalCost = totalCost; }

    public OperationCounter getOps() { return ops; }
    public void setOps(OperationCounter ops) { this.ops = ops; }

    public double getTimeMs() { return timeMs; }
    public void setTimeMs(double timeMs) { this.timeMs = timeMs; }

}
