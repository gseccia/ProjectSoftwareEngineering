package main.java;

import configuration.DoorsConfiguration;
import configuration.NoSuchElementInConfigurationException;
import map.Block;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.lwjgl.Sys;

import java.util.*;

public class Map {
    private DefaultUndirectedGraph<Vertex, DefaultEdge> graph;

    public Map(DefaultUndirectedGraph<Vertex, DefaultEdge> graph){

        this.graph = graph;
    }

    public List<String> mapSubSet(){   //metodo che genera un sottoinsieme di mappe tra tutte quelle disponibili
       DoorsConfiguration conf = new DoorsConfiguration();
       Set<String> mapNames = conf.getMapNames();
       String[] tiledMaps = mapNames.toArray(new String[0]);
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

    public List<Vertex> generateVertex(DefaultUndirectedGraph graph) throws NoSuchElementInConfigurationException {  //metodo che genera un numero di archi tali da rispettare i constrain detti in classe e li aggiunge al grafo
        boolean condition = false;
        int min = 2;
        int max;
        int doorNumber;             //numero di porta per ogni tiledMap
        int totalDoor;             //numero totale di porte
        Vertex v;
        DoorsConfiguration conf = new DoorsConfiguration();
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
                //doorNumber = random.nextInt(3) + 1;  //genera un numero di porte compreso tra 1 e 3
                doorNumber = conf.getDoors(tiledMaps.get(i));
                v = new Vertex(i, tiledMaps.get(i), false, doorNumber);  //crea un nuovo vertice e aggiungilo al grafico e alla lista dei vertici
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

    public void generateGraph(DefaultUndirectedGraph graph, List listVertex){          //metodo che genera casualmente un grafo connesso
        listVertex.sort(null);
        int i = 0;
        int j;
        int totalDoor = 0;
        j = i+1;
        Vertex v,vi,vj;
        for (int k=0; k<listVertex.size();k++) {
            v = (Vertex) listVertex.get(k);
            totalDoor += v.getDoorNumber();
        }
        while(totalDoor > 0){
            vi = (Vertex) listVertex.get(i);
            while (vi.getNumeroPorteRimanenti() > 0 && i < listVertex.size()-1 && j < listVertex.size()){
                vj = (Vertex) listVertex.get(j);
                if (vj.getNumeroPorteRimanenti() > 0 ){
                    graph.addEdge(vi,vj);
                    vi.setNumeroPorteRimanenti(vi.getNumeroPorteRimanenti()-1);
                    vj.setNumeroPorteRimanenti(vj.getNumeroPorteRimanenti()-1);
                    totalDoor -= 2;
                }
                j++;
            }
            i++;
            if (j >= listVertex.size())
                j = i;
        }
    }

    public Set<DefaultEdge> getEdges(DefaultUndirectedGraph graph,Vertex v){  //ritorna tutti gli archi del vertice v appartenenti al grafo graph
        return graph.edgesOf(v);
    }

    public List<Block> generateBlock(){
        Set<Vertex> set = vertex();
        List<Block> block = new ArrayList<>();
        for (Vertex v: set){
            block.add(new Block(v.getId(),v.getEl()));
        }
        return block;
    }

    public Set<Vertex> vertex(){

        return graph.vertexSet();
    }
}
