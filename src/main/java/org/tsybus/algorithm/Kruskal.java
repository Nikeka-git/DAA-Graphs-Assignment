package org.tsybus.algorithm;

import org.tsybus.struct.Edge;
import org.tsybus.struct.Graph;
import org.tsybus.struct.MSTResult;
import org.tsybus.struct.OperationCounter;

import java.util.*;

public class Kruskal implements Algorithm {

    @Override
    public MSTResult computeMST(Graph graph) {
        long start = System.nanoTime();
        OperationCounter ops = new OperationCounter();
        List<Edge> allEdges = new ArrayList<>(graph.getAllEdges());
        allEdges.sort(Comparator.comparingInt(Edge::getWeight));

        Map<String, String> parent = new HashMap<>();
        for (String v : graph.getVertices()) parent.put(v, v);

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        for (Edge e : allEdges) {
            ops.incComparisons();
            String a = find(parent, e.getFrom());
            String b = find(parent, e.getTo());
            if (!a.equals(b)) {
                ops.incUnions();
                parent.put(a, b);
                mstEdges.add(e);
                totalCost += e.getWeight();
            } else {
                ops.incOther();
            }
        }

        long end = System.nanoTime();

        MSTResult result = new MSTResult();
        result.setOps(ops);
        result.setAlgorithm("Kruskal");
        result.setEdges(mstEdges);
        result.setTotalCost(totalCost);
        result.setTimeMs((end - start) / 1_000_000.0);
        result.setVertexCount(graph.getVertices().size());
        result.setEdgeCount(graph.getAllEdges().size());
        return result;
    }

    private String find(Map<String, String> parent, String v) {
        if (!parent.get(v).equals(v))
            parent.put(v, find(parent, parent.get(v)));
        return parent.get(v);
    }

    @Override
    public String name() {
        return "Kruskal";
    }
}
