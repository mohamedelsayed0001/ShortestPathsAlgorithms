
import org.example.algorithims.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ShortestPathAlgorithmsTest {
    
    private ArrayList<int[]>[] createGraph(int vertices, int[][] edges) {
        ArrayList<int[]>[] graph = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            graph[edge[0]].add(new int[]{edge[1], edge[2]});
        }
        return graph;
    }

    private final int[][] smallGraphEdges = {
        {0, 1, 4}, {0, 2, 1}, {2, 1, 2}, {1, 3, 1}, {2, 3, 5}
    };

    private final int[][] negativeGraphEdges = {
        {0, 1, 4}, {0, 2, 1}, {2, 1, -2}, {1, 3, 1}, {2, 3, 5}
    };

    private final int[][] unreachableGraphEdges = {
        {0, 1, 2}, {1, 2, 3}, 
    };

    private final int vertices = 4;


    @Test
    public void testDijkstraCorrectness() {
        ArrayList<int[]>[] graph = createGraph(vertices, smallGraphEdges);
        Dijkstra dijkstra = new Dijkstra(graph, 0);
        dijkstra.execute();

        assertEquals("4", dijkstra.getCost(0, 3));
        assertEquals("[0, 2, 1, 3]", dijkstra.getPath(0, 3));
    }

    @Test
    public void testBellmanFordCorrectness() {
        ArrayList<int[]>[] graph = createGraph(vertices, smallGraphEdges);
        BellmanFord bf = new BellmanFord(graph, 0);
        bf.execute();

        assertEquals("4", bf.getCost(0, 3));
        assertEquals("[0, 2, 1, 3]", bf.getPath(0, 3));
    }

    @Test
    public void testFloydWarshallCorrectness() {
        ArrayList<int[]>[] graph = createGraph(vertices, smallGraphEdges);
        FloydWarshall fw = new FloydWarshall(graph);
        fw.execute();

        assertTrue(fw.isSuccessful());
        assertEquals("4", fw.getCost(0, 3));
        assertEquals("[0, 2, 1, 3]", fw.getPath(0, 3));
    }

    @Test
    public void testDijkstraNegativeEdgeWarning() {
        ArrayList<int[]>[] graph = createGraph(vertices, negativeGraphEdges);
        Dijkstra dijkstra = new Dijkstra(graph, 0);
        dijkstra.execute();

        assertEquals("0", dijkstra.getCost(0, 3));
    }

    @Test
    public void testBellmanFordNegativeEdge() {
        ArrayList<int[]>[] graph = createGraph(vertices, negativeGraphEdges);
        BellmanFord bf = new BellmanFord(graph, 0);
        bf.execute();

        assertEquals("0", bf.getCost(0, 3));
        assertEquals("[0, 2, 1, 3]", bf.getPath(0, 3));
    }

    @Test
    public void testFloydWarshallNegativeEdge() {
        ArrayList<int[]>[] graph = createGraph(vertices, negativeGraphEdges);
        FloydWarshall fw = new FloydWarshall(graph);
        fw.execute();

        assertTrue(fw.isSuccessful());
        assertEquals("0", fw.getCost(0, 3));
    }

    @Test
    public void testUnreachableNodes() {
        ArrayList<int[]>[] graph = createGraph(4, unreachableGraphEdges);

        BellmanFord bf = new BellmanFord(graph, 0);
        bf.execute();

        assertEquals("∞", bf.getCost(0, 3));
        assertEquals("No path", bf.getPath(0, 3));
    }

    @Test
    public void testNegativeCycleFloydWarshall() {
        int[][] edges = {
            {0, 1, 1}, {1, 2, -1}, {2, 0, -1}
        };
        ArrayList<int[]>[] graph = createGraph(3, edges);
        FloydWarshall fw = new FloydWarshall(graph);
        fw.execute();

        assertFalse(fw.isSuccessful());
    }

    @Test
    public void testEfficiencyComparison() {
        int size = 100;
        ArrayList<int[]>[] graph = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            graph[i] = new ArrayList<>();
            if (i + 1 < size) graph[i].add(new int[]{i + 1, 1});
        }

        long start = System.nanoTime();
        new Dijkstra(graph, 0).execute();
        long dijkstraTime = System.nanoTime() - start;

        start = System.nanoTime();
        new BellmanFord(graph, 0).execute();
        long bellmanTime = System.nanoTime() - start;

        start = System.nanoTime();
        new FloydWarshall(graph).execute();
        long floydTime = System.nanoTime() - start;

        System.out.println("Dijkstra: " + dijkstraTime + " ns");
        System.out.println("Bellman-Ford: " + bellmanTime + " ns");
        System.out.println("Floyd-Warshall: " + floydTime + " ns");

        assertTrue(dijkstraTime < bellmanTime || dijkstraTime < floydTime);
    }
    @Test
public void testLargeGraphPerformance() {
    // Create a larger graph to test performance on scale
    int largeSize = 500;
    ArrayList<int[]>[] largeGraph = new ArrayList[largeSize];
    for (int i = 0; i < largeSize; i++) {
        largeGraph[i] = new ArrayList<>();
        // Add some random edges
        for (int j = 0; j < 5; j++) {
            int target = (i + j + 1) % largeSize;
            largeGraph[i].add(new int[]{target, j + 1});
        }
    }
    
    // Test that Dijkstra completes in reasonable time on large graph
    long startTime = System.currentTimeMillis();
    Dijkstra dijkstra = new Dijkstra(largeGraph, 0);
    dijkstra.execute();
    long endTime = System.currentTimeMillis();
    
    // Verify execution time is reasonable (less than 1 second)
    assertTrue((endTime - startTime) < 1000);
    // Verify we can get a result
    assertNotEquals("∞", dijkstra.getCost(0, largeSize - 1));
}

@Test
public void testDenseGraph() {
    // Create a fully connected graph where every node connects to every other node
    int size = 10;
    ArrayList<int[]>[] denseGraph = new ArrayList[size];
    for (int i = 0; i < size; i++) {
        denseGraph[i] = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            if (i != j) {
                // Distance is proportional to node difference
                denseGraph[i].add(new int[]{j, Math.abs(i - j)});
            }
        }
    }
    
    // Test all three algorithms on the dense graph
    Dijkstra dijkstra = new Dijkstra(denseGraph, 0);
    dijkstra.execute();
    
    BellmanFord bf = new BellmanFord(denseGraph, 0);
    bf.execute();
    
    FloydWarshall fw = new FloydWarshall(denseGraph);
    fw.execute();
    
    // All should produce the same shortest path from 0 to size-1
    assertEquals(dijkstra.getCost(0, size - 1), bf.getCost(0, size - 1));
    assertEquals(dijkstra.getCost(0, size - 1), fw.getCost(0, size - 1));
    assertEquals("1", dijkstra.getCost(0, 1)); // Direct edge should be shortest
}

@Test
public void testMultipleSourceNodes() {
    ArrayList<int[]>[] graph = createGraph(vertices, smallGraphEdges);
    
    // Test from different source nodes
    Dijkstra dijkstra1 = new Dijkstra(graph, 0);
    dijkstra1.execute();
    
    Dijkstra dijkstra2 = new Dijkstra(graph, 2);
    dijkstra2.execute();
    
    // Check that paths are correct from different sources
    assertEquals("[0, 2, 1, 3]", dijkstra1.getPath(0, 3));
    assertEquals("[2, 1, 3]", dijkstra2.getPath(2, 3));
    
    // Costs should be calculated correctly from each source
    assertEquals("4", dijkstra1.getCost(0, 3));
    assertEquals("3", dijkstra2.getCost(2, 3));
}

@Test
public void testNegativeCycleBellmanFord() {
    // Graph with negative cycle
    int[][] negCycleEdges = {
        {0, 1, 1}, {1, 2, 2}, {2, 1, -3}  // Negative cycle between 1 and 2
    };
    ArrayList<int[]>[] graph = createGraph(3, negCycleEdges);
    
    BellmanFord bf = new BellmanFord(graph, 0);
    bf.execute();
    
    // BellmanFord should detect the negative cycle
    assertFalse(bf.isSuccessful());
}

@Test
public void testAlternativePaths() {
    // Graph with multiple paths of the same total cost
    int[][] multiPathEdges = {
        {0, 1, 2}, {0, 2, 2},
        {1, 3, 3}, {2, 3, 3},
        {1, 4, 4}, {2, 4, 3},
        {3, 5, 1}, {4, 5, 2}
    };
    ArrayList<int[]>[] graph = createGraph(6, multiPathEdges);
    
    Dijkstra dijkstra = new Dijkstra(graph, 0);
    dijkstra.execute();
    
    BellmanFord bf = new BellmanFord(graph, 0);
    bf.execute();
    
    FloydWarshall fw = new FloydWarshall(graph);
    fw.execute();
    
    // All algorithms should find the same minimum cost (5) from 0 to 5
    assertEquals("6", dijkstra.getCost(0, 5));
    assertEquals("6", bf.getCost(0, 5));
    assertEquals("6", fw.getCost(0, 5));
    
    // The path could be either [0,1,3,5] or [0,2,3,5]
    String path = dijkstra.getPath(0, 5);
    assertTrue(path.equals("[0, 1, 3, 5]") || path.equals("[0, 2, 3, 5]"));
}
@Test
public void testDisconnectedComponents() {
    // Create a graph with disconnected components
    int[][] disconnectedEdges = {
        {0, 1, 5}, {1, 2, 3},  // Component 1: vertices 0-2
        {3, 4, 2}, {4, 5, 7}   // Component 2: vertices 3-5
    };
    ArrayList<int[]>[] graph = createGraph(6, disconnectedEdges);
    
    // Test that paths between disconnected components are properly reported
    Dijkstra dijkstra = new Dijkstra(graph, 0);
    dijkstra.execute();
    
    BellmanFord bf = new BellmanFord(graph, 0);
    bf.execute();
    
    FloydWarshall fw = new FloydWarshall(graph);
    fw.execute();
    
    // Verify unreachable nodes
    assertEquals("∞", dijkstra.getCost(0, 3));
    assertEquals("∞", bf.getCost(0, 5));
    assertEquals("∞", fw.getCost(1, 4));
    
    // Verify paths within the same component
    assertEquals("8", dijkstra.getCost(0, 2));
    assertEquals("7", fw.getCost(4, 5));
    
    // Verify path reporting is correct for unreachable nodes
    assertEquals("No path exists", dijkstra.getPath(0, 4));
    assertEquals("No path", bf.getPath(2, 3));
}

@Test
public void testSelfLoops() {
    // Create a graph with self-loops (edges from a vertex to itself)
    int[][] selfLoopEdges = {
        {0, 1, 3}, {1, 2, 5},
        {0, 0, 1}, {1, 1, 2}, {2, 2, 3}  // Self-loops
    };
    ArrayList<int[]>[] graph = createGraph(3, selfLoopEdges);
    
    Dijkstra dijkstra = new Dijkstra(graph, 0);
    dijkstra.execute();
    
    BellmanFord bf = new BellmanFord(graph, 0);
    bf.execute();
    
    FloydWarshall fw = new FloydWarshall(graph);
    fw.execute();
    
    // Self-loops shouldn't affect shortest path calculations
    assertEquals("8", dijkstra.getCost(0, 2));
    assertEquals("8", bf.getCost(0, 2));
    assertEquals("8", fw.getCost(0, 2));
    
    // Path should go through intermediate vertices, not use self-loops
    assertEquals("[0, 1, 2]", dijkstra.getPath(0, 2));
}

}
