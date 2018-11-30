package map;

import main.Block;

public class Vertex implements Comparable<Vertex>{
    private String el;
    private boolean token;
    private int doorNumber;
    private int numeroPorteRimanenti;
    private final int id;
    private Block block;

    public Vertex(int id, String el, boolean token, int doorNumber) {
        this.id = id;
        this.el = el;
        this.token = token;
        this.doorNumber = doorNumber;
        this.numeroPorteRimanenti = this.doorNumber;
    }

    public int getNumeroPorteRimanenti() {

        return numeroPorteRimanenti;
    }

    public void setNumeroPorteRimanenti(int numeroPorteRimanenti) {

        this.numeroPorteRimanenti = numeroPorteRimanenti;
    }

    public String getEl() {

        return el;
    }

    public void setEl(String el) {

        this.el = el;
    }

    public boolean getToken() {

        return token;
    }

    public int getId(){

        return id;
    }

    public void setToken(boolean token) {

        this.token = token;
    }

    public int getDoorNumber() {

        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {

        this.doorNumber = doorNumber;
    }
    
    public void setBlock(Block block) {
    	this.block= block;
    }
    
    public Block getBlock() {
    	return block;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "ID=" + id +
                ", el=" + el +
                ", token=" + token +
                ", doorNumber=" + doorNumber +
                ", numeroPorteRimanenti="+ numeroPorteRimanenti+
                '}';
    }

    @Override
    public int compareTo(Vertex v) {
        if ( this.getDoorNumber() > v.getDoorNumber() )
            return -1;
        if ( this.getDoorNumber() < v.getDoorNumber() )
            return 1;
        else return 0;
    }
}