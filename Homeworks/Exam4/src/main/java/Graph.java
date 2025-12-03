package edu.frcc.csc1061j.MyGraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;


public class Graph<E> {
	public List<Vertex> vertices = new ArrayList<>();
	
	private class Vertex {
		private E elem;
		private List<Edge> neighbors = new ArrayList<>();
		
		public Vertex (E elem) {
			this.elem = elem;
		}

		public E getKey() {
			return elem;
		}
		
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Graph.Vertex))
				return false;
			
			if (elem.equals(((Vertex)other).elem)) {
				return true;
			}
			return false;		
		}
		
		@Override 
		public String toString() {
			return elem.toString();
		}
	}

	
	private class Edge implements Comparable<Edge> {
		private Vertex s;
		private Vertex d;
		private int weight;

		public Edge(Vertex s, Vertex d, int weight) {
			this.s = s;
			this.d = d;
			this.weight = weight;
		}

		public boolean equals(Object edge) {
			return s.equals(((Edge) edge).s) && d.equals(((Edge) edge).d);
		}

		@Override
		public int compareTo(Graph<E>.Edge o) {
			return (int) (weight - o.weight);
		}
	}

	public Graph(List<Vertex> vertices) {
		for (Vertex vertex : vertices) {
			addVertex(new Vertex(vertex.getKey()));
		}
	}
	
	public Graph(List<E> vertices, E[][] edges) {
		for (E ver : vertices) {
			addVertex(new Vertex(ver));
		}
		createAdjacencyLists(edges);
	}

	public boolean addVertex(Vertex vertex) {
		if (!vertices.contains(vertex)) {
			vertices.add(vertex);
			return true;
		} else {
			return false;
		}

	}

	public boolean addEdge(Edge edge) {
		
		List<Edge> neighbors = edge.s.neighbors;
		if (!neighbors.contains(edge)) {
			neighbors.add(edge);
			return true;
		} else {
			return false;
		}
	}

	private Vertex findVertex(E key) {
		for(Vertex v : vertices) {
			if (v.elem.equals(key)) {
				return v;
			}
		}
		return null;
	}
	private void createAdjacencyLists(E[][] edges) {
		for (int i = 0; i < edges.length; i++) {
			addEdge(new Edge(findVertex(edges[i][0]), findVertex(edges[i][1]), (int)edges[i][2]));
		}
	}

	public void printEdges() {
		for (int i = 0; i < vertices.size(); i++) {
			System.out.print("Vertex: " + vertices.get(i).toString() + ":");
			List<Edge> neighbors = vertices.get(i).neighbors;
			for (Edge edge : neighbors) {
				System.out.print("(" + edge.s + ", " + edge.d + ", " + edge.weight + ")");
			}
			System.out.println();
		}
	}

	public List<Vertex> getChildNodes(Vertex vertex) {
		List<Vertex> childNodes = new ArrayList<>();
		List<Edge> neighbors = vertex.neighbors;
		for (Edge edge : neighbors) {
			childNodes.add(edge.d);
		}
		return childNodes;
	}
	
	/* TODO: Implement the DFS algorithm for a graph either recursively
	** or iteratively using a stack. It should return a list of all the 
	** vertices in the pre-order depth-first traversal.
	*/
	// DFS -> LIFO
	public List<Vertex> dfs(Vertex root) {
		// list that will store the vertices in the order we visit them
		List<Vertex> result = new ArrayList<>();

		// if root is null, just return empty list
		if (root == null) {
			return result;
		}
		// this list keeps track of which vertices we have already visited
		List<Vertex> visited = new ArrayList<>();

		// stack for DFS
		Deque<Vertex> stack = new ArrayDeque<>();
		// start by pushing the root vertex on the stack
		stack.push(root);

		// keep going while there are vertices to process
		while (!stack.isEmpty()) {
			// take the top vertex from the stack
			Vertex current = stack.pop();

			// if we have not seen this vertex yet
			if (!visited.contains(current)) {
				// mark it as visited
				visited.add(current);

				// add it to the result in pre-order when we first see it
				result.add(current);

				// get all neighbors/children of this vertex
				List<Vertex> children = getChildNodes(current);

				// push neighbors onto the stack
				for (int i = children.size() - 1; i >= 0; i--) {
					Vertex child = children.get(i);
					if (!visited.contains(child)) {
						stack.push(child);
					}
				}
			}
		}

		return result;
	}

	/* TODO: Implement the BFS algorithm for a graph. It should return a list 
	** of all the vertices in the breadth-first traversal.
	*/
	// BFS -> FIFO
	public List<E> bfs() {
		// list to store the elements in the order we visit them
		List<E> result = new ArrayList<>();
		// keeps track of which vertices have been visited
		List<Vertex> visited = new ArrayList<>();
		// queue for BFS
		Deque<Vertex> queue = new ArrayDeque<>();

		// we loop over all vertices in case the graph is not connected
		for (Vertex start : vertices) {
			// if this start vertex has not been visited yet
			if (!visited.contains(start)) {
				// visit it first and add to queue
				visited.add(start);
				queue.offer(start);
				// BFS loop
				while (!queue.isEmpty()) {
					// take the vertex from the front of the queue
					Vertex current = queue.poll();

					// add its element/key to the result list
					result.add(current.getKey());

					// look at all neighbors children of this vertex
					List<Vertex> children = getChildNodes(current);
					// for each neighbor, if not visited, visit and enqueue it
					for (Vertex child : children) {
						if (!visited.contains(child)) {
							visited.add(child);
							queue.offer(child);
						}
					}
				}
			}
		}

		return result;

	}
	

	/* TODO: Create a spanning tree using Kruskal's Algorithm and return it. 
	** The spanning tree will be a new graph
	*/
	public Graph<E> findMinimumSpanningTree() {
		// make a new graph to store the MST
		// this copies the vertex keys into a new graph
		Graph<E> mst = new Graph<>(this.vertices);

		// list to hold all edges in the original graph
		List<Edge> allEdges = new ArrayList<>();

		// get all edges but avoid duplicates
		for (Vertex v : vertices) {
			for (Edge e : v.neighbors) {

				// get the positions of the two vertices
				int uIndex = vertices.indexOf(e.s);
				int vIndex = vertices.indexOf(e.d);

				// only add the edge once when left index < right index
				if (uIndex < vIndex) {
					allEdges.add(e);
				}
			}
		}

		// sort edges by weight
		Collections.sort(allEdges);

		// union-find setup
		// parent[i] tells us which set each vertex is in
		int[] parent = new int[vertices.size()];

		// each vertex starts in its own set
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
		}

		// helper method to find the root of a set
		java.util.function.IntUnaryOperator find = x -> {
			while (parent[x] != x) {
				x = parent[x];
			}
			return x;
		};

		// go through each edge in sorted order
		for (Edge e : allEdges) {

			// get the index of each endpoint
			int ui = vertices.indexOf(e.s);
			int vi = vertices.indexOf(e.d);

			// find their roots
			int rootU = find.applyAsInt(ui);
			int rootV = find.applyAsInt(vi);

			// if roots are different, adding this edge won't make a cycle
			if (rootU != rootV) {

				// join the two sets
				parent[rootV] = rootU;

				// now add this edge to the MST graph
				Vertex mstU = mst.findVertex(e.s.getKey());
				Vertex mstV = mst.findVertex(e.d.getKey());

				// add the edge in both directions
				mst.addEdge(mst.new Edge(mstU, mstV, e.weight));
				mst.addEdge(mst.new Edge(mstV, mstU, e.weight));
			}
		}

		return mst;
	}
}
