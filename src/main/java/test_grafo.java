package main.java;


import configuration.NoSuchElementInConfigurationException;
import org.jgrapht.GraphTests;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class test_grafo {
    public static void main(String args[]) throws NoSuchElementInConfigurationException {
        DefaultUndirectedGraph<Vertex, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        Map map = new Map (graph);
        Set<String> subSet= new LinkedHashSet<>();
        Set<Vertex> set = new LinkedHashSet<>();
        List<Vertex> listVertex = new ArrayList<>();
        listVertex = map.generateVertex(graph);
        System.out.println("Sottoinsieme di mappe generato: "+listVertex.toString());
        map.generateGraph(graph,listVertex);
        set = map.vertex();
        for (Vertex vertex: set){
            System.out.println(vertex.toString());
        }
        Set<DefaultEdge> setEdge = new LinkedHashSet<>();
        setEdge = graph.edgeSet();
        for (DefaultEdge edge : setEdge){
            System.out.println(edge.toString());
        }
        System.out.println("E un grafo connesso ? "+GraphTests.isConnected(graph));
    }
}