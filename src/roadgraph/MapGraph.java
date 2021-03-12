/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 *         A class which represents a graph of geographic locations Nodes in the
 *         graph are intersections between
 *
 */
public class MapGraph {

	HashMap<GeographicPoint, MapNode> vertices;
	int edgesNum;

	/**
	 * Create a new empty MapGraph
	 */
	public MapGraph() {
		vertices = new HashMap<>();
	}

	/**
	 * Get the number of vertices (road intersections) in the graph
	 * 
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices() {
		return vertices.size();
	}

	/**
	 * Return the intersections, which are the vertices in this graph.
	 * 
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices() {
		return vertices.keySet();
	}

	/**
	 * Get the number of road segments in the graph
	 * 
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges() {
		return edgesNum;
	}

	/**
	 * Add a node corresponding to an intersection at a Geographic Point If the
	 * location is already in the graph or null, this method does not change the
	 * graph.
	 * 
	 * @param location The location of the intersection
	 * @return true if a node was added, false if it was not (the node was already
	 *         in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location) {
		if ((location == null) || (vertices.get(location) != null)) {
			return false;
		}
		this.vertices.put(location, new MapNode(location));
		return true;
	}

	/**
	 * Adds a directed edge to the graph from pt1 to pt2. Precondition: Both
	 * GeographicPoints have already been added to the graph
	 * 
	 * @param from     The starting point of the edge
	 * @param to       The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length   The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been added as
	 *                                  nodes to the graph, if any of the arguments
	 *                                  is null, or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length)
			throws IllegalArgumentException {

		MapNode fromNode = this.vertices.get(from);
		MapNode toNode = this.vertices.get(to);
		if ((from == null) || (to == null) || (roadName == null) || (roadType == null) || (length <= 0)
				|| (fromNode == null) || (toNode == null)) {
			throw new IllegalArgumentException();
		}
		edgesNum++;
		fromNode.addEdge(from, to, roadName, roadType, length);
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal  The goal location
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return bfs(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using breadth first search
	 * 
	 * @param start        The starting location
	 * @param goal         The goal location
	 * @param nodeSearched A hook for visualization. See assignment instructions for
	 *                     how to use it.
	 * @return The list of intersections that form the shortest (unweighted) path
	 *         from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {

		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
		if ((startNode == null) || (goalNode == null)) {
			return new LinkedList<>();
		}

		HashMap<MapNode, MapNode> parentMap = new HashMap<>();
		boolean found = bfsSearch(startNode, goalNode, parentMap, nodeSearched);
		if (!found) {
			return new ArrayList<GeographicPoint>();
		}
		return constructPath(startNode, goalNode, parentMap);
	}

	private boolean bfsSearch(MapNode startNode, MapNode goalNode, HashMap<MapNode, MapNode> parentMap,
			Consumer<GeographicPoint> nodeSearched) {
		Queue<MapNode> toExplore = new LinkedList<>();
		HashSet<MapNode> visited = new HashSet<>();
		boolean found = false;
		toExplore.add(startNode);
		while (!toExplore.isEmpty()) {
			MapNode curr = toExplore.remove();
			if (curr.getLocation().equals(goalNode.getLocation())) {
				found = true;
				break;
			}
			List<MapEdge> neighbors = curr.getEdges();
			ListIterator<MapEdge> it = neighbors.listIterator(neighbors.size());

			while (it.hasPrevious()) {
				MapNode next = vertices.get(it.previous().getEnd());
				if (!visited.contains(next)) {
					// Hook for visualization. See writeup.
					nodeSearched.accept(next.getLocation());
					visited.add(next);
					parentMap.put(next, curr);
					toExplore.add(next);
				}
			}
		}

		return found;
	}

	/**
	 * construct a path between two nodes in the graph
	 * 
	 * @param startNode the start node
	 * @param goalNode  the goal
	 * @param parentMap
	 * @return path between two nodes
	 */
	private <T extends MapNode> List<GeographicPoint> constructPath(T startNode, T goalNode, HashMap<T, T> parentMap) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		T curr = goalNode;
		while (startNode != curr) {
			path.addFirst(curr.getLocation());
			curr = parentMap.get(curr);
		}
		path.addFirst(startNode.getLocation());
		return path;
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal  The goal location
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return dijkstra(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start        The starting location
	 * @param goal         The goal location
	 * @param nodeSearched A hook for visualization. See assignment instructions for
	 *                     how to use it.
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		WeightedMapNode startNode = new WeightedMapNode(vertices.get(start));
		WeightedMapNode goalNode = new WeightedMapNode(vertices.get(goal));
		if ((startNode == null) || (goalNode == null)) {
			return new LinkedList<>();
		}
		HashMap<GeographicPoint, WeightedMapNode> dijkstraVertices = new HashMap<>();
		PriorityQueue<WeightedMapNode> pq = new PriorityQueue<>();
		HashSet<WeightedMapNode> visited = new HashSet<>();
		HashMap<WeightedMapNode, WeightedMapNode> parentMap = new HashMap<>();
		boolean found = false;
		// init
		for (MapNode mapNode : this.vertices.values()) {
			dijkstraVertices.put(mapNode.location, new WeightedMapNode(mapNode, Double.MAX_VALUE));
			
		}

		startNode.setDistance(0);
		pq.add(startNode);
		while (!pq.isEmpty()) {
			WeightedMapNode curr = pq.remove();
			if (!visited.contains(curr)) {
				visited.add(curr);
				if (curr.getLocation().equals(goalNode.getLocation())) {
					found = true;
					break;
				}
				List<MapEdge> neighbors = curr.getEdges();
				ListIterator<MapEdge> it = neighbors.listIterator(neighbors.size());
				while (it.hasPrevious()) {
					MapEdge currentEdge = it.previous();
					WeightedMapNode next = dijkstraVertices.get(currentEdge.getEnd());
					if ((!visited.contains(next))) {

						if (next.getDistance() > (currentEdge.getLength() + curr.getDistance())) {
							parentMap.put(next, curr);
							next.setDistance(currentEdge.getLength() + curr.getDistance());
							// Hook for visualization. See writeup.
							nodeSearched.accept(next.getLocation());
							pq.add(next);

						}

					}
				}

			}
		}

		if (!found) {
			return new ArrayList<GeographicPoint>();

		}
		// System.out.println("visited nodes for Dijkstra");
		// System.out.println("**********");
		// for (WeightedMapNode weightedMapNode : visited) {
		// 	System.out.println(weightedMapNode.location);
		// }
		// System.out.println("**********");

		return constructPath(startNode, goalNode, parentMap);

	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal  The goal location
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		Consumer<GeographicPoint> temp = (x) -> {
		};
		return aStarSearch(start, goal, temp);
	}

	/**
	 * Find the path from start to goal using A-Star search
	 * 
	 * @param start        The starting location
	 * @param goal         The goal location
	 * @param nodeSearched A hook for visualization. See assignment instructions for
	 *                     how to use it.
	 * @return The list of intersections that form the shortest path from start to
	 *         goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal,
			Consumer<GeographicPoint> nodeSearched) {
		WeightedMapNode startNode = new WeightedMapNode(vertices.get(start));
		WeightedMapNode goalNode = new WeightedMapNode(vertices.get(goal));
		if ((startNode == null) || (goalNode == null)) {
			return new LinkedList<>();
		}
		HashMap<GeographicPoint, WeightedMapNode> dijkstraVertices = new HashMap<>();
		PriorityQueue<WeightedMapNode> pq = new PriorityQueue<>();
		HashSet<WeightedMapNode> visited = new HashSet<>();
		HashMap<WeightedMapNode, WeightedMapNode> parentMap = new HashMap<>();
		boolean found = false;
		// init
		for (MapNode mapNode : this.vertices.values()) {
			dijkstraVertices.put(mapNode.location, new WeightedMapNode(mapNode, Double.MAX_VALUE, Double.MAX_VALUE));
		}
		startNode.setDistance(0);
		startNode.setPredictedDistance(0);
		pq.add(startNode);
		while (!pq.isEmpty()) {
			WeightedMapNode curr = pq.remove();
			if (!visited.contains(curr)) {
				visited.add(curr);
				if (curr.getLocation().equals(goalNode.getLocation())) {
					found = true;
					break;
				}
				List<MapEdge> neighbors = curr.getEdges();
				ListIterator<MapEdge> it = neighbors.listIterator(neighbors.size());
				while (it.hasPrevious()) {
					MapEdge currentEdge = it.previous();
					WeightedMapNode next = dijkstraVertices.get(currentEdge.getEnd());
					if ((!visited.contains(next))) {

						if (next.getDistance() > (currentEdge.getLength() + curr.getDistance())) {
							parentMap.put(next, curr);
							next.setDistance(currentEdge.getLength() + curr.getDistance());
							next.setPredictedDistance(currentEdge.getEnd().distance(goalNode.location));
							// Hook for visualization. See writeup.
							nodeSearched.accept(next.getLocation());
							pq.add(next);

						}

					}
				}

			}
		}

		if (!found) {
			return new ArrayList<GeographicPoint>();

		}
		// System.out.println("visited nodes for a star");
		// System.out.println("**********");
		// for (WeightedMapNode weightedMapNode : visited) {
		// 	System.out.println(weightedMapNode.location);
		// }
		// System.out.println("**********");

		return constructPath(startNode, goalNode, parentMap);

	}

	public static void main(String[] args) {
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		// System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		// System.out.println("DONE.");

		// test add vertex and add edge
		// System.out.println(firstMap.getNumVertices());
		// for (MapNode mapNode : firstMap.vertices.values()) {
		// System.out.println("********");
		// System.out.println(mapNode);
		// for (MapEdge mapEdge : mapNode.getEdges()) {
		// System.out.println(mapEdge);
		// }
		// System.out.println("********");
		// }
		// You can use this method for testing.

		// PriorityQueue<MapNode> mapNodes = new PriorityQueue<>();
		// mapNodes.add(new MapNode(20));
		// mapNodes.add(new MapNode(10));
		// mapNodes.add(new MapNode(15));
		// // ListIterator<WeightedMapNode> listIterator = mapNodes.iterator();
		// while (mapNodes.size() != 0) {
		// System.out.println(mapNodes.remove());
		// }

		/*
		 * Here are some test cases you should try before you attempt the Week 3 End of
		 * Week Quiz, EVEN IF you score 100% on the programming assignment.
		 */

		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);

		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		// List<GeographicPoint> path = simpleTestMap.bfs(testStart, testEnd);
		// for (GeographicPoint geographicPoint : path) {
		// 	System.out.println(geographicPoint);
		// }
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		System.out.println("***********");
		System.out.println("testing dijkstra");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart, testEnd);
		for (GeographicPoint geographicPoint : testroute) {
			System.out.println(geographicPoint);
		}
		System.out.println("***********");
		System.out.println("testing AStar");
		List<GeographicPoint> testrouteForAStar = simpleTestMap.aStarSearch(testStart, testEnd);
		for (GeographicPoint geographicPoint : testrouteForAStar) {
			System.out.println(geographicPoint);
		}
		System.out.println("***********");

		// List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,
		// testEnd);
		/*
		 * 
		 * MapGraph testMap = new MapGraph();
		 * GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		 * 
		 * // A very simple test using real data testStart = new
		 * GeographicPoint(32.869423, -117.220917); testEnd = new
		 * GeographicPoint(32.869255, -117.216927); System.out.
		 * println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		 * testroute = testMap.dijkstra(testStart,testEnd); testroute2 =
		 * testMap.aStarSearch(testStart,testEnd);
		 * 
		 * 
		 * // A slightly more complex test using real data testStart = new
		 * GeographicPoint(32.8674388, -117.2190213); testEnd = new
		 * GeographicPoint(32.8697828, -117.2244506); System.out.
		 * println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		 * testroute = testMap.dijkstra(testStart,testEnd); testroute2 =
		 * testMap.aStarSearch(testStart,testEnd);
		 */

		/* Use this code in Week 3 End of Week Quiz */
		/*
		 * MapGraph theMap = new MapGraph();
		 * System.out.print("DONE. \nLoading the map...");
		 * GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		 * System.out.println("DONE.");
		 * 
		 * GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		 * GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		 * 
		 * 
		 * List<GeographicPoint> route = theMap.dijkstra(start,end);
		 * List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		 * 
		 */

	}

}
