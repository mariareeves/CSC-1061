import java.util.ArrayList;
import java.util.List;

import edu.frcc.csc1061j.MyGraph.Graph;

public class GraphTest {

	public static void main(String[] args) {
		List<Integer> vertices = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			vertices.add(i);
		}

		// Edges for MST
		Integer[][] edges = {
				{0, 1, 5}, {0, 2, 4}, 
				{1, 0, 5}, {1, 2, 2}, {1, 5, 3},
				{2, 0, 4}, {2, 1, 2}, {2, 5, 4}, {2, 3, 5},
				{3, 2, 5}, {3, 5, 6}, {3, 4, 7},
				{4, 3, 7}, {4, 5, 8},
				{5, 1, 3}, {5, 2, 4}, {5, 3, 6}, {5, 4, 8}
		};

		Graph<Integer> graph = new Graph<>(vertices, edges);
		System.out.println("Original Graph:");
		graph.printEdges();
		
		System.out.println("Minimum Spanning Tree:");
		graph.findMinimumSpanningTree().printEdges();


		// ------------------------------------------
		// my own testing edge cases


		List<Integer> vertices2 = new ArrayList<>();
		for (int i = 0; i < 4; i++) {  // vertices: 0, 1, 2, 3
			vertices2.add(i);
		}

		// simple graph to test MST
		Integer[][] edges2 = {
				{0, 1, 1}, {1, 0, 1},
				{1, 2, 2}, {2, 1, 2},
				{2, 3, 3}, {3, 2, 3},
				{0, 2, 5}, {2, 0, 5},
				{0, 3, 10}, {3, 0, 10}
		};

		Graph<Integer> testGraph = new Graph<>(vertices2, edges2);

		System.out.println("\n--- My Own Testing Graph ---");
		System.out.println("Test Graph (original):");
		testGraph.printEdges();

		System.out.println("Test Graph (MST):");
		testGraph.findMinimumSpanningTree().printEdges();



	}


}
