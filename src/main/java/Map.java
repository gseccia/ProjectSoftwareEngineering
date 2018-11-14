package main.java;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Set;

public class Map {
    private SimpleGraph<Vertex, DefaultEdge> graph;

    public Map(SimpleGraph<Vertex, DefaultEdge> graph){

        this.graph = graph;
    }

    public void buildGraph(){
        Vertex v1 = new Vertex(1,true);
        Vertex v2 = new Vertex(2, false);
        Vertex v3= new Vertex(3, false);
        Vertex v4 = new Vertex(4, false);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addEdge(v1,v2);
        graph.addEdge(v1,v3);
        graph.addEdge(v1,v4);
        graph.addEdge(v2,v3);
        graph.addEdge(v3,v4);
    }

    public Set<Vertex> vertex(){
        return graph.vertexSet();
    }
}
