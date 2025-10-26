package org.tsybus.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.tsybus.algorithm.*;
import org.tsybus.struct.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public class OutputGenerator {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Map<?, ?> input = mapper.readValue(new File("input.json"), Map.class);
        List<Map<String, Object>> graphs = (List<Map<String, Object>>) input.get("graphs");

        ArrayNode resultsArray = mapper.createArrayNode();
        int processed = 0;

        for (Map<String, Object> gData : graphs) {
            processed++;
            int id = (int) gData.get("id");
            List<String> nodes = (List<String>) gData.get("nodes");
            List<Map<String, Object>> edges = (List<Map<String, Object>>) gData.get("edges");

            Graph g = new Graph();
            for (String n : nodes) g.addVertex(n);
            for (Map<String, Object> e : edges)
                g.addEdge((String) e.get("from"), (String) e.get("to"), (int) e.get("weight"));

            Prim prim = new Prim();
            Kruskal kruskal = new Kruskal();
            MSTResult rPrim = prim.computeMST(g);
            MSTResult rKruskal = kruskal.computeMST(g);

            ObjectNode resultNode = mapper.createObjectNode();
            resultNode.put("graph_id", id);

            ObjectNode inputStats = mapper.createObjectNode();
            inputStats.put("vertices", g.getVertices().size());
            inputStats.put("edges", g.getAllEdges().size());
            resultNode.set("input_stats", inputStats);

            resultNode.set("prim", makeAlgoNode(mapper, rPrim));
            resultNode.set("kruskal", makeAlgoNode(mapper, rKruskal));

            resultsArray.add(resultNode);
            System.out.println("Processed graph " + processed + " (" + g.getVertices().size() + " vertices)");
        }

        ObjectNode root = mapper.createObjectNode();
        root.set("results", resultsArray);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), root);

        System.out.println("output.json successfully created.");
    }

    private static ObjectNode makeAlgoNode(ObjectMapper mapper, MSTResult result) {
        ObjectNode node = mapper.createObjectNode();
        ArrayNode edgesArray = mapper.createArrayNode();
        for (Edge e : result.getEdges()) {
            ObjectNode edgeNode = mapper.createObjectNode();
            edgeNode.put("from", e.getFrom());
            edgeNode.put("to", e.getTo());
            edgeNode.put("weight", e.getWeight());
            edgesArray.add(edgeNode);
        }
        node.set("mst_edges", edgesArray);
        node.put("total_cost", result.getTotalCost());
        node.put("operations_count", result.getOps() != null ? result.getOps().getComparisons() : 0);
        node.put("execution_time_ms", result.getTimeMs());
        return node;
    }
}
