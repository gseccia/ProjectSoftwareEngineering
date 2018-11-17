package main.java;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.lwjgl.Sys;

import java.util.*;

public class Map {
    private DefaultUndirectedGraph<Vertex, DefaultEdge> graph;

    public Map(DefaultUndirectedGraph<Vertex, DefaultEdge> graph){

        this.graph = graph;
    }

    public List<String> mapSubSet(){
       String [] tiledMaps = {"parcheggio","bagno","giardino","cucina","salotto","corridoio","negozio"};
       int i=0;
       Random random = new Random();
       int numeroCasuale = random.nextInt(tiledMaps.length)+2; // seleziona un sottoinsieme casuale di tiledMaps contenente almeno 2 elemento
       List<String> listTiledMaps = new ArrayList<>();
        while (i < numeroCasuale && listTiledMaps.size() != tiledMaps.length) {
            int rand = random.nextInt(tiledMaps.length); // seleziona un elemento casuale tra tutte le tiledMaps
            while (listTiledMaps.contains(tiledMaps[rand])) {
                rand = random.nextInt(tiledMaps.length);  //se l'elemento esiste già genera un altro numero casuale fin quando non si inserisce correttamente l'elemento
            }
            listTiledMaps.add(tiledMaps[rand]);
            i++;
        }
        return listTiledMaps;
    }

    public List<Vertex> generateVertex(DefaultUndirectedGraph graph){
        boolean condition = false;
        int min = 2;
        int max;
        int doorNumber;             //numero di porta per ogni tiledMap
        int totalDoor;             //numero totale di porte
        Vertex v;
        List<Vertex> listVertex= new ArrayList<>();
        List<String> tiledMaps = new ArrayList<>();
        Random random = new Random();
        int vertexNumber;
        while (!condition){
            totalDoor=0;    //reset ogni ciclo del numero totale di porte
            tiledMaps = mapSubSet();  //genera una sottoinsieme casuale di tiledMaps
            max = tiledMaps.size()-1;
            vertexNumber = random.nextInt(max)+min;  //genera un numero di vertici compreso tra min e max
            for (int i = 0; i < vertexNumber; i++) {
                doorNumber = random.nextInt(3) + 1;  //genera un numero di porte compreso tra 1 e 3
                v = new Vertex(tiledMaps.get(i), false, doorNumber);  //crea un nuovo vertice e aggiungilo al grafico e alla lista dei vertici
                listVertex.add(v);
                graph.addVertex(v);
                totalDoor += doorNumber;
            }
            if (totalDoor %2 ==0 && vertexNumber==(totalDoor/2+1))   //verifica constrain
                condition = true;
            else {
                graph.removeAllVertices(listVertex);
                listVertex.clear(); //pulisci la lista e grafico se i requisiti non sono rispettati
                }
            }
            return listVertex;
        }

    public void generateGraph(DefaultUndirectedGraph graph, List listVertex){
        listVertex.sort(null);
        int i = 0;
        int j = 1;
        int appoggioj;
        int totalDoor = 0;
        int viNumeroPorte, vjNumeroPorte;
        /*List<Vertex> listVertex = new ArrayList<>();
        Vertex v1 = new Vertex("corridoio", false , 3); graph.addVertex(v1);
        Vertex v2 = new Vertex("salotto",false, 3); graph.addVertex(v2);
        Vertex v3 = new Vertex("bagno", false ,2); graph.addVertex(v3);
        Vertex v4 = new Vertex("cucina", false ,2); graph.addVertex(v4);
        Vertex v5 = new Vertex("stanza", false ,2); graph.addVertex(v5);

        listVertex.add(v1); listVertex.add(v2); listVertex.add(v3); listVertex.add(v4); listVertex.add(v5);*/


        appoggioj = i+1;

        Vertex v,vi,vj;
        for (int k=0; k<listVertex.size();k++) {
            v = (Vertex) listVertex.get(k);
            totalDoor += v.getDoorNumber();
        }
        while(totalDoor > 0){
            vi = (Vertex) listVertex.get(i);
            viNumeroPorte = vi.getDoorNumber();
            while (viNumeroPorte >0 && i < listVertex.size()-1 && appoggioj < listVertex.size()){
                vj = (Vertex) listVertex.get(appoggioj);
                vjNumeroPorte = vj.getDoorNumber();
                if (vjNumeroPorte > 0 ){
                    graph.addEdge(vi,vj);
                    viNumeroPorte--;
                    vjNumeroPorte--;
                    totalDoor -= 2;
                }
                appoggioj++;
            }
            i++;
            if (appoggioj >= listVertex.size())
                appoggioj = i;
        }
    }

    public Set<Vertex> vertex(){
        return graph.vertexSet();
    }
}
