
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
}
