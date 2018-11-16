package main.java;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import java.util.*;

public class Map {
    private DefaultUndirectedGraph<Vertex, DefaultEdge> graph;

    public Map(DefaultUndirectedGraph<Vertex, DefaultEdge> graph){

        this.graph = graph;
    }

    public Set<String> mapSubSet(){
       String [] tiledMaps = {"parcheggio","bagno","giardino","cucina","salotto","corridoio","negozio"};
       int i=0;
       Random random = new Random();
       int numeroCasuale = random.nextInt(tiledMaps.length)+2; // seleziona un sottoinsieme casuale di tiledMaps contenente almeno 2 elemento
       Set<String> setTiledMaps = new LinkedHashSet<>();
        while (i < numeroCasuale && setTiledMaps.size() != tiledMaps.length) {
            int rand = random.nextInt(tiledMaps.length); // seleziona un elemento casuale tra tutte le tiledMaps
            while (!setTiledMaps.add(tiledMaps[rand])) {
                rand = random.nextInt(tiledMaps.length);  //se l'elemento esiste già genera un altro numero casuale fin quando non si inserisce correttamente l'elemento
            }
            i++;
        }
        return setTiledMaps;
    }

    public void generateVertex(DefaultUndirectedGraph graph, Set mapSubset){
        boolean condition = false;
        int min = 2;
        int max;
        int doorNumber;             //numero di porta per ogni tiledMap
        int totalDoor;             //numero totale di porte
        Vertex v;
        List<Vertex> listVertex = new ArrayList<>();
        String [] tiledMaps;
        Random random = new Random();
        int vertexNumber;
        while (!condition){
            totalDoor=0;    //reset ogni ciclo del numero totale di porte
            tiledMaps = (String[]) mapSubSet().toArray(new String [mapSubSet().size()]);  //genera una sottoinsieme casuale di tiledMaps
            max = tiledMaps.length-1;
            vertexNumber = random.nextInt(max)+min;  //genera un numero di vertici compreso tra min e max
            for (int i = 0; i < vertexNumber; i++) {
                doorNumber = random.nextInt(3) + 1;  //genera un numero di porte compreso tra 1 e 3
                v = new Vertex(tiledMaps[i], false, doorNumber);
                listVertex.add(v);
                graph.addVertex(v);
                graph.addEdge(v,v);
                totalDoor += doorNumber;
            }
            if (totalDoor %2 ==0 && vertexNumber<=(totalDoor/2+1))
                condition = true;
            else {
                graph.removeAllVertices(listVertex);
                listVertex.clear(); //pulisci la lista e grafico se i requisiti non sono rispettati
                }
            }
        }

    public Set<Vertex> vertex(){
        return graph.vertexSet();
    }
}
