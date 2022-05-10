import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class project4main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		//Reading input, computing the number minimum undistributed gifts and printing it.
		new PrintStream(new File(args[1])).append(String.valueOf(readInput(args[0]).maxFlow())).close();
		
	}

	static Network readInput(String path) throws FileNotFoundException {

		Network net = new Network();
		
		Scanner scanner = new Scanner(new File(path));

		Vertex w = null;

		Vertex source = new Vertex("s");
		Vertex target = new Vertex("t");
		
		net.addVertex(source);
		net.addVertex(target);

		//Creating arrays for every kind of vehicles.
		
		String[] greenTrains;
		String[] redTrains;
		String[] greenDeers;
		String[] redDeers;

		if (scanner.nextLine().trim().equals("0")) {
			greenTrains = new String[0];
			scanner.nextLine();
		} else
			greenTrains = scanner.nextLine().trim().split(" ");

		for (int i = 0; i < greenTrains.length; i++) {

			if (Integer.valueOf(greenTrains[i]) == 0)
				continue;
			w = new Vertex("gt" + i);
			w.addEdge(target.getID(), Integer.valueOf(greenTrains[i]));
			net.vehicleNum++;

			net.addVertex(w);
		}

		if (scanner.nextLine().trim().equals("0")) {
			redTrains = new String[0];
			scanner.nextLine();
		} else
			redTrains = scanner.nextLine().trim().split(" ");

		for (int i = 0; i < redTrains.length; i++) {
			if (Integer.valueOf(redTrains[i]) == 0)
				continue;
			w = new Vertex("rt" + i);
			w.addEdge(target.getID(), Integer.valueOf(redTrains[i]));
			net.vehicleNum++;

			net.addVertex(w);
		}

		if (scanner.nextLine().trim().equals("0")) {
			greenDeers = new String[0];
			scanner.nextLine();
		} else
			greenDeers = scanner.nextLine().trim().split(" ");

		for (int i = 0; i < greenDeers.length; i++) {
			if (Integer.valueOf(greenDeers[i]) == 0)
				continue;
			w = new Vertex("gd" + i);
			w.addEdge(target.getID(), Integer.valueOf(greenDeers[i]));
			net.vehicleNum++;

			net.addVertex(w);
		}

		if (scanner.nextLine().trim().equals("0")) {
			redDeers = new String[0];
			scanner.nextLine();
		} else
			redDeers = scanner.nextLine().trim().split(" ");

		for (int i = 0; i < redDeers.length; i++) {
			if (Integer.valueOf(redDeers[i]) == 0)
				continue;
			w = new Vertex("rd" + i);
			w.addEdge(target.getID(), Integer.valueOf(redDeers[i]));
			net.vehicleNum++;

			net.addVertex(w);
		}

		int count = 2 * Integer.valueOf(scanner.nextLine());
		if (count == 0) {
			return net;
		}
		
		String[] bags = scanner.nextLine().trim().split(" ");

		String prop = "";
		int capacity = 0;

		Vertex v = null;

		// Reading bags and constructing edges between the bag and the relevant vehicles.

		for (int i = 0; i < count; i += 2) {

			if (bags[i + 1].equals("0")) {
				continue;
			}
			
			prop = bags[i];
			capacity = Integer.valueOf(bags[i + 1]);

			if (prop.contains("a")) {

				v = new Vertex("a" + i);
				v.setHeight(1);
				v.isVehicle = false;
				net.addVertex(v);
				source.addEdge(v.getID(), capacity);
				
			} else {
				
				//The bags which doesn't have the 'a' property will be merged
				
				v = net.vertices.get(prop);

				if (v == null) {

					v = new Vertex(prop);
					v.setHeight(1);
					v.isVehicle = false;
					net.addVertex(v);
					source.addEdge(prop, capacity);
					
				} else
					source.addEdge(prop, source.edges.get(prop) + capacity);

			}

			source.addExcess(capacity);

			if (prop.contains("b")) {

				if (!prop.contains("e")) {
					// This bag will have edges to green trains

					for (int j = 0; j < greenTrains.length; j++) {

						if (greenTrains[j].equals("0"))
							continue;

						capacity = prop.contains("a") ? 1 : Integer.valueOf(greenTrains[j]);

						v.addEdge("gt" + j, capacity);
					}

				}
				if (!prop.contains("d")) {
					// This bag will have edges to green deers
					for (int j = 0; j < greenDeers.length; j++) {
						if (greenDeers[j].equals("0"))
							continue;
						capacity = prop.contains("a") ? 1 : Integer.valueOf(greenDeers[j]);

						v.addEdge("gd" + j, capacity);
					}
				}

			} else if (prop.contains("c")) {

				if (!prop.contains("e")) {
					// This bag will have edges to red trains

					for (int j = 0; j < redTrains.length; j++) {
						if (redTrains[j].equals("0"))
							continue;
						capacity = prop.contains("a") ? 1 : Integer.valueOf(redTrains[j]);

						v.addEdge("rt" + j, capacity);
					}

				}
				if (!prop.contains("d")) {
					// This bag will have edges to red deers
					for (int j = 0; j < redDeers.length; j++) {
						if (redDeers[j].equals("0"))
							continue;
						capacity = prop.contains("a") ? 1 : Integer.valueOf(redDeers[j]);

						v.addEdge("rd" + j, capacity);
					}
				}

			} else if (prop.contains("d")) {
				// This bag will have edges to green and red trains 
				
				for (int j = 0; j < greenTrains.length; j++) {
					if (greenTrains[j].equals("0"))
						continue;
					capacity = prop.contains("a") ? 1 : Integer.valueOf(greenTrains[j]);

					v.addEdge("gt" + j, capacity);
				}

				for (int j = 0; j < redTrains.length; j++) {
					if (redTrains[j].equals("0"))
						continue;
					capacity = prop.contains("a") ? 1 : Integer.valueOf(redTrains[j]);

					v.addEdge("rt" + j, capacity);
				}
			} else if (prop.contains("e")) {
				// This bag will have edges to green and red deers

				for (int j = 0; j < greenDeers.length; j++) {
					if (greenDeers[j].equals("0"))
						continue;
					capacity = prop.contains("a") ? 1 : Integer.valueOf(greenDeers[j]);

					v.addEdge("gd" + j, capacity);
				}

				for (int j = 0; j < redDeers.length; j++) {
					if (redDeers[j].equals("0"))
						continue;
					capacity = prop.contains("a") ? 1 : Integer.valueOf(redDeers[j]);

					v.addEdge("rd" + j, capacity);
				}

			} else if (prop.contains("a")) {

				//This bag will have edges to any kind of vehicle
				
				for (int j = 0; j < greenTrains.length; j++) {

					if (greenTrains[j].equals("0"))
						continue;

					v.addEdge("gt" + j, 1);
				}

				for (int j = 0; j < redTrains.length; j++) {

					if (redTrains[j].equals("0"))
						continue;

					v.addEdge("rt" + j, 1);
				}
				for (int j = 0; j < greenDeers.length; j++) {
					if (greenDeers[j].equals("0"))
						continue;

					v.addEdge("gd" + j, 1);
				}

				for (int j = 0; j < redDeers.length; j++) {
					if (redDeers[j].equals("0"))
						continue;

					v.addEdge("rd" + j, 1);
				}
			}
		}

		scanner.close();

		return net;

	}

}
