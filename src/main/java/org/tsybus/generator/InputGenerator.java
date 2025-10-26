package org.tsybus.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.*;

public class InputGenerator {

    static class Edge {
        public String from;
        public String to;
        public int weight;

        public Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    static class GraphData {
        public int id;
        public List<String> nodes;
        public List<Edge> edges;

        public GraphData(int id, List<String> nodes, List<Edge> edges) {
            this.id = id;
            this.nodes = nodes;
            this.edges = edges;
        }
    }

    static class GraphSet {
        public List<GraphData> graphs;

        public GraphSet(List<GraphData> graphs) {
            this.graphs = graphs;
        }
    }

    public static void main(String[] args) throws Exception {
        List<GraphData> graphs = new ArrayList<>();
        Random rand = new Random();

        int[] small = {5, 10, 20, 25, 30};
        int[] medium = {50, 75, 100, 125, 150, 175, 200, 225, 250, 300};
        int[] large = {350, 400, 500, 600, 650, 700, 750, 800, 900, 1000};
        int[] extraLarge = {1200, 1600, 2000};

        int id = 1;

        for (int n : concat(small, medium, large, extraLarge)) {
            double density = getDensity(n);
            List<String> nodes = generateNodes(n);
            List<Edge> edges = generateConnectedEdges(nodes, rand, density);
            graphs.add(new GraphData(id++, nodes, edges));
            System.out.printf("Graph %d: %d vertices, %d edges (density %.2f%%)%n",
                    id - 1, n, edges.size(), density * 100);
        }

        GraphSet graphSet = new GraphSet(graphs);
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("input.json"), graphSet);

        System.out.println("input.json generated successfully");
    }

    private static double getDensity(int n) {
        if (n <= 30) {
            return Math.random() * 0.5;
        }
        if (n <= 300) {
            return Math.random() * 0.2;
        }
        if (n <= 1000) {
            return Math.random() * 0.11;
        }
        return Math.random() * 0.05;
    }

    private static List<String> generateNodes(int count) {
        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < count; i++) nodes.add("N" + i);
        return nodes;
    }

    private static List<Edge> generateConnectedEdges(List<String> nodes, Random rand, double density) {
        List<Edge> edges = new ArrayList<>();

        for (int i = 1; i < nodes.size(); i++) {
            String from = nodes.get(rand.nextInt(i));
            String to = nodes.get(i);
            edges.add(new Edge(from, to, 1 + rand.nextInt(50)));
        }

        int n = nodes.size();
        int maxPossibleEdges = n * (n - 1) / 2;
        int targetEdges = (int) Math.min(maxPossibleEdges, n - 1 + (maxPossibleEdges * density));
        while (edges.size() < targetEdges) {
            String from = nodes.get(rand.nextInt(n));
            String to = nodes.get(rand.nextInt(n));
            if (!from.equals(to) && !edgeExists(edges, from, to)) {
                edges.add(new Edge(from, to, 1 + rand.nextInt(50)));
            }
        }

        return edges;
    }

    private static boolean edgeExists(List<Edge> edges, String a, String b) {
        for (Edge e : edges) {
            if ((e.from.equals(a) && e.to.equals(b)) || (e.from.equals(b) && e.to.equals(a)))
                return true;
        }
        return false;
    }

    private static int[] concat(int[]... arrays) {
        return Arrays.stream(arrays).flatMapToInt(Arrays::stream).toArray();
    }
}
