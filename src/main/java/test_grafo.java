package main.java;


import org.jgrapht.GraphTests;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import java.util.LinkedHashSet;
import java.util.Set;


public class test_grafo {
    public static void main(String args[]) {
        DefaultUndirectedGraph<Vertex, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        Map map = new Map (graph);
        Set<String> subSet= new LinkedHashSet<>();
        Set<Vertex> set = new LinkedHashSet<>();
        map.generateVertex(graph,subSet);
        set = map.vertex();
        for (Vertex vertex: set){
            System.out.println(vertex.toString());
        }

    }
}