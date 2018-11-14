package main.java;


import org.jgrapht.GraphTests;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.LinkedHashSet;
import java.util.Set;


public class test_grafo {
    public static void main(String args[]) {
        SimpleGraph<Vertex, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        Map map = new Map (graph);
        map.buildGraph();
        Set<Vertex> set = new LinkedHashSet<>();
        set = map.vertex();
        for (Vertex vertex: set){
            System.out.println(vertex.toString());
        }
        System.out.println(GraphTests.isConnected(graph));
    }
}