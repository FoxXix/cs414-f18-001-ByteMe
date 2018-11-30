package main.edu.colostate.cs.cs414.ByteMe.banqi.routing;

import java.util.ArrayList;

public class RoutingTable {
	
	ArrayList<Route> routes = new ArrayList<>();
	
	public void addRoute(Route route) {
		routes.add(route);
	}

	public String toString() {
		for (Route r : routes) {
			System.out.print(r.getID());
			System.out.print(" " + r.getIP());
			System.out.print(" " + r.getPort());
			System.out.println();
		}
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		return "";
	}
}