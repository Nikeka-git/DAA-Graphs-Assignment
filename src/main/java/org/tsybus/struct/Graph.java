package org.tsybus.struct;

import java.util.*;

public class Graph {
    private Map<String, List<Edge>> adjList = new HashMap<>();


    public void addVertex(String v){
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(String from, String to, int weight){
        addVertex(from);
        addVertex(to);
        adjList.get(from).add(new Edge(from, to, weight));
        adjList.get(to).add(new Edge(to, from, weight));
    }

    public List<Edge> getEdges(String v){
        return adjList.getOrDefault(v, new ArrayList<>());
    }

    public Set<String> getVertices(){
        return adjList.keySet();
    }

    public List<String> getNeighbors(String v) {
        List<String> neighbors = new ArrayList<>();
        for (Edge e : adjList.getOrDefault(v, new ArrayList<>())) {
            neighbors.add(e.getTo());
        }
        return neighbors;
    }

    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> list : adjList.values()) {
            edges.addAll(list);
        }
        return edges;
    }
}
