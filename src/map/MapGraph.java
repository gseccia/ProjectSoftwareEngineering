package map;

import blocks.BlockFactory;
import configuration.MapConfiguration;
import configuration.NoSuchElementInConfigurationException;
import main.gamestates.GameStates;
import blocks.Block;

import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.*;

public class MapGraph {

    private BlockFactory factory;
    private DefaultUndirectedGraph<Vertex, Edge> graph;
    private MapConfiguration conf;
    private int mapChosen;

    public MapGraph(int mapChosen, MapConfiguration conf, BlockFactory factory){

        this.mapChosen = mapChosen;
        this.conf = conf;
        this.factory = factory;
        this.graph = new DefaultUndirectedGraph<>(Edge.class);
    }

    private List<String> mapSubSet(){   //metodo che genera un sottoinsieme di mappe tra tutte quelle disponibili
       Set<String> mapNames = conf.getMapNames();
       LinkedList<String> tiledMaps = new LinkedList<>(mapNames);
       ArrayList<String> moreMaps = new ArrayList<>(mapNames);
       int i=0;
       int rand = 0;
       Random random = new Random();
       int numeroCasuale = random.nextInt(2)+mapChosen+2;
       List<String> listTiledMaps = new ArrayList<>();
       while (i < numeroCasuale && tiledMaps.size() > 0) {
           rand = random.nextInt(tiledMaps.size()); // seleziona un elemento casuale tra tutte le tiledMaps
           listTiledMaps.add(tiledMaps.remove(rand));
           i++;
       }
       while (i < numeroCasuale) {
           rand = random.nextInt(moreMaps.size()); // seleziona un elemento casuale tra tutte le tiledMaps
           listTiledMaps.add(moreMaps.get(rand));
           i++;
       }
       return listTiledMaps;
    }

    private List<Vertex> generateVertex() throws NoSuchElementInConfigurationException {  //metodo che genera un numero di archi tali da rispettare i constrain detti in classe e li aggiunge al grafo
        boolean condition = false;
        int doorNumber;             //numero di porta per ogni tiledMap
        int totalDoor; //numero totale di porte
        Vertex v;
        MapConfiguration conf = MapConfiguration.getInstance();
        List<Vertex> listVertex= new ArrayList<>();
        List<String> tiledMaps ;
        while (!condition){
            totalDoor=0;    //reset ogni ciclo del numero totale di porte
            tiledMaps = mapSubSet();  //genera una sottoinsieme casuale di tiledMaps
            for (int i = 0; i < tiledMaps.size(); i++) {
                //doorNumber = random.nextInt(3) + 1;  //genera un numero di porte compreso tra 1 e 3
                doorNumber = conf.getDoors(tiledMaps.get(i));
                v = new Vertex(i + GameStates.STARTING_POINT.getState(), tiledMaps.get(i), false, doorNumber);  //crea un nuovo vertice e aggiungilo al grafico e alla lista dei vertici
                listVertex.add(v);
                graph.addVertex(v);
                totalDoor += doorNumber;
            }
            if (totalDoor %2 ==0 && tiledMaps.size()==(totalDoor/2+1))   //verifica constrain
                condition = true;
            else {
                graph.removeAllVertices(listVertex);
                listVertex.clear(); //pulisci la lista e grafico se i requisiti non sono rispettati
                }
            }
            return listVertex;
        }

    public void generateGraph() throws NoSuchElementInConfigurationException {          //metodo che genera casualmente un grafo connesso
        List listVertex = generateVertex();
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
                	graph.addEdge(vi, vj, new Edge(vi.getNumeroPorteRimanenti(),vj.getNumeroPorteRimanenti()));
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
    
    public Vertex getVertex(Block b) {
    	Vertex found_v=null;
    	for(Vertex v:vertex()) {
    		if(v.getId()==b.getID()) {
    			found_v=v;
    			break;
    		}
    	}
    	return found_v;
    }

    public Set<Edge> getEdges(Block b){  //ritorna tutti gli archi del vertice v appartenenti al grafo graph
        return graph.edgesOf(getVertex(b));
    }

    public List<Block> generateBlock(){
        List<Block> block = new ArrayList<>();
        Block tmp;
        for (Vertex v: vertex()){
        	tmp = factory.generateBlock(v.getId(),v.getEl());
            block.add(tmp);
            v.setBlock(tmp);
        }
        return block;
    }

    public Set<Vertex> vertex(){
        return graph.vertexSet();
    }

    public DefaultUndirectedGraph<Vertex, Edge> getGraph() {
        return graph;
    }
}
