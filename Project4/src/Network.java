import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Network {

	//The number of the gifts to be distributed
	private int gifts = 0;

	//A HashMap to store the IDs and the corresponding vertices
	protected HashMap<String, Vertex> vertices = new HashMap<>();
	
	//The number of the vehicles
	protected int vehicleNum = 0;

	/**
	 * Adds the given vertex to the vertices HashMap.
	 * @param v The vertex to be added
	 */
	public void addVertex(Vertex v) {
		vertices.put(v.getID(), v);
	}

	/**
	 * 
	 * Computes the maximum flow and so the minimum number of undistributed gifts based on the Push-Relabel algorithm.
	 * @return the number of undistributed gifts
	 */
	public int maxFlow() {

		gifts = vertices.get("s").getExcess();

		vertices.get("s").setHeight(vertices.size());

		vertices.get("t").setHeight(-1);

		ArrayList<String> queue = new ArrayList<>();

		queue.add("s");

		Vertex x = null;
		Vertex w = null;
		Iterator<String> it;

		int amount = 0;
		int capacity = 0;
		int minHeight = 0;

		int c = 0;

		boolean removed = false;

		//As long as there is a vertex with excess flow, that vertex will be added into the queue. So the loop will be executed until there is no vertex with excess flow
		
		while (c < queue.size()) {

			x = vertices.get(queue.get(c));
			c++;

			//All of the excess flow of the vertex will be distributed
			while (x.getExcess() > 0) {

				minHeight = Integer.MAX_VALUE;
				it = x.edges.keySet().iterator();

				//The excess flow of the vertex will be distributed to all possible edges
				//If there is still excess flow then the height of the vertex will be increased
				while (it.hasNext()) {

					w = vertices.get(it.next());
					if (w == null)
						continue;
					removed = false;

					if (w.getHeight() < x.getHeight()) {

						//Pushing the feasible amount of excess to an adjacent vertex
						//Updating the residual graph

						capacity = x.edges.get(w.getID());

						amount = Math.min(capacity, x.getExcess());

						if (capacity - amount == 0) {
							it.remove();
							removed = true;
						} else {
							x.updateEdge(w.getID(), 0 - amount);
						}

						w.updateEdge(x.getID(), amount);

						x.addExcess(-1 * amount);
						w.addExcess(amount);

						if (!"st".contains(w.getID())) {
							queue.add(w.getID());
						}
					}

					if (x.getExcess() == 0) {
						break;
					}

					if (x.getHeight() <= w.getHeight() && minHeight > w.getHeight() && !removed) {

						minHeight = w.getHeight();

					}

				}

				if (x.getExcess() > 0 && x.getID() != "s" && x.getID() != "t") {

					//Having pushed to all feasible vertices, if there is still some excess flow then the vertex height will be increased to the least greater vertex height plus one

					if (x.isVehicle) {
						vehicleNum--;
						x.isVehicle = false;
					}
					
					//When all the vehicles' height is increased i.e. there is no edge to the target anymore the method terminates and returns.
					if (vehicleNum == 0) {
						return gifts - vertices.get("t").getExcess();
					}

					x.setHeight(minHeight + 1);

				}

			}
		}

		return gifts - vertices.get("t").getExcess();
	}

}