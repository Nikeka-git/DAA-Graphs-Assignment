package org.tsybus.struct;

public class Edge {
    private Vertex from;
    private Vertex to;
    private int weight;

    public Edge(Vertex from, Vertex to, int weight){
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Vertex getFrom(){
        return from;
    }
    public Vertex getTo(){
        return to;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public String toString(){
        return from + "->" + to + "(" +  weight + ")";
    }
}
