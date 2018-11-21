package map;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class Edge extends DefaultEdge{
	private int portSource;
	private int portDestination;
	
	public Edge(int portSource,int portDestination) {
		super();
		this.portSource = portSource;
		this.portDestination = portDestination;
	}
	
	/**
	 * @param v Vertex of the edge
	 * @return the source door for Vertex v, -1 if v is not in this edge
	 */
	public int getPortSource(Vertex v) {
		if((Vertex)getSource()==v) return portSource;
		else if((Vertex)getTarget()==v) return portDestination;
		else return -1;
	}

	/**
	 * @param v Vertex of the edge
	 * @return the destination door for Vertex v, -1 if v is not in this edge
	 */
	public int getPortDestination(Vertex v) {
		if((Vertex)getSource()==v) return portDestination;
		else if((Vertex)getTarget()==v) return portSource;
		else return -1;
	}
	
	/**
	 * 
	 * @param v Vertex of the edge
	 * @return The opposite Vertex of thi edges, null if v is not in this edge
	 */
	public Vertex opposite(Vertex v) {
		if((Vertex)getSource()==v) return (Vertex) getTarget();
		else if((Vertex)getTarget()==v) return (Vertex) getSource();
		else return null;
	}
	
	
	public String toString() {
		return portSource+" "+portDestination;
	}
}
