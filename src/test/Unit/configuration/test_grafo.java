package test.Unit.configuration;


import configuration.NoSuchElementInConfigurationException;
import map.Vertex;

import org.jgrapht.GraphTests;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class test_grafo {
    public static void main(String args[]) throws NoSuchElementInConfigurationException {
        Map map = new Map ();
        Set<String> subSet= new LinkedHashSet<>();
        Set<Vertex> set = new LinkedHashSet<>();
        List<Vertex> listVertex = new ArrayList<>();
        listVertex = map.generateVertex();
        System.out.println("Sottoinsieme di mappe generato: "+listVertex.toString());
        map.generateGraph(listVertex);
        set = map.vertex();
        for (Vertex vertex: set){
            System.out.println(vertex.toString());
        }
        Set<DefaultEdge> setEdge = new LinkedHashSet<>();
        setEdge = map.getEdges(listVertex.get(1));
        for (DefaultEdge edge : setEdge){
            System.out.println(edge.toString());
        }
       // System.out.println("E un grafo connesso ? "+GraphTests.isConnected());
    }
}