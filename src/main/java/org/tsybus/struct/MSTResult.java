package org.tsybus.struct;

import java.util.List;
import org.tsybus.struct.Edge;

public class MSTResult {
    private List<Edge> edges;
    private long totalCost;
    private OperationCounter ops;
    private long timeMs;

    public MSTResult() {}

    public List<Edge> getEdges() { return edges; }
    public void setEdges(List<Edge> edges) { this.edges = edges; }

    public long getTotalCost() { return totalCost; }
    public void setTotalCost(long totalCost) { this.totalCost = totalCost; }

    public OperationCounter getOps() { return ops; }
    public void setOps(OperationCounter ops) { this.ops = ops; }

    public long getTimeMs() { return timeMs; }
    public void setTimeMs(long timeMs) { this.timeMs = timeMs; }

}
