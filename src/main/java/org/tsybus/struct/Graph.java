package org.tsybus.struct;

import java.util.*;

public class Graph {
    private Map<Vertex, List<Edge>> adjList = new HashMap<>();


    public void addVertex(Vertex v){
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(Vertex from, Vertex to, int weight){
        addVertex(from);
        addVertex(to);
        adjList.get(from).add(new Edge(from, to, weight));
    }

    public List<Edge> getEdges(Vertex v){
        return adjList.getOrDefault(v, new ArrayList<>());
    }

    public Set<Vertex> getVertices(){
        return adjList.keySet();
    }

    public List<Vertex> getNeighbors(Vertex v) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge e : adjList.getOrDefault(v, new ArrayList<>())) {
            neighbors.add(e.getTo());
        }
        return neighbors;
    }
}
