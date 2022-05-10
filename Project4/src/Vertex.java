import java.util.HashMap;

public class Vertex {
	
	//ID value of the vertex.
	private final String ID;
	
	//The height value of the vertex.
	private int height = 0;
	
	//The excess flow value of the vertex.
	private int excess = 0;

	//Checks whether the vertex is a vehicle or not.
	protected boolean isVehicle = true;

	//A HashMap to store edges.
	protected HashMap<String, Integer> edges = new HashMap<>();

	public Vertex(String ID) {
		this.ID = ID;
	}

	/**
	 * Builds an edge from this vertex to the given vertex with the given capacity.
	 * @param ID the ID of the vertex to which the edge is connected
	 * @param capacity	the capacity value of the edge
	 */
	public void addEdge(String ID, int capacity) {

		edges.put(ID, capacity);

	}

	/**
	 * Updates the capacity of the edge which is between this vertex and the given vertex.
	 * @param ID the ID of the vertex to which the edge is connected
	 * @param amount the amount to be added
	 */
	
	public void updateEdge(String ID, int amount) {

		edges.put(ID, edges.getOrDefault(ID, 0) + amount);

	}

	/**
	 * Adds the given value to the excess flow of the vertex.
	 * @param excess the amount to be added
	 */
	
	public void addExcess(int excess) {
		this.excess += excess;
	}
	
	
	//Getter and setter methods
	
	public String getID() {
		return ID;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getExcess() {
		return excess;
	}

	

}