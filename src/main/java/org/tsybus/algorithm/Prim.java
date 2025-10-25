package org.tsybus.algorithm;

import org.tsybus.struct.*;
import java.util.*;

public class Prim implements Algorithm{

    @Override
    public MSTResult computeMST(Graph graph) {
        long start = System.currentTimeMillis();

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        List<Edge> mstEdges = new ArrayList<>();
        OperationCounter ops = new OperationCounter();
        int totalCost = 0;
        String startVertex = graph.getVertices().iterator().next();
        visited.add(startVertex);
        pq.addAll(graph.getEdges(startVertex));

        while (!pq.isEmpty() && mstEdges.size() < graph.getVertices().size() - 1) {
            Edge e = pq.poll();
            ops.incComparisons();
            String to = e.getTo();
            if (visited.contains(to)) {
                ops.incOther();
                continue;
            }

            visited.add(to);
            mstEdges.add(e);
            totalCost += e.getWeight();

            for (Edge next : graph.getEdges(to)) {
                ops.incOther();
                if (!visited.contains(next.getTo())) pq.add(next);
            }
        }
        long end = System.currentTimeMillis();

        MSTResult result = new MSTResult();
        result.setOps(ops);
        result.setAlgorithm("Prim");
        result.setEdges(mstEdges);
        result.setTotalCost(totalCost);
        result.setTimeMs(end - start);
        result.setVertexCount(graph.getVertices().size());
        result.setEdgeCount(graph.getAllEdges().size());
        return result;
    }
    @Override
    public String name(){
        return "Prim";
        }
}
