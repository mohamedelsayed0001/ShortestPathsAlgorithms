package org.example.algorithims;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Dijkstra implements Algorithm {

    private final int source;
    private final ArrayList<int[]>[] graph;
    private final int vertices;
    private final int[][] costs;
    private final int[][] parents;
    private boolean success = true;

    public Dijkstra(ArrayList<int[]>[] graph, int source) {
        this.source = source;
        this.graph = graph;
        this.vertices = graph.length;

        if (source == -1) {
            costs = new int[vertices][vertices];
            parents = new int[vertices][vertices];
        } else {
            costs = new int[1][vertices];
            parents = new int[1][vertices];
        }
    }

    @Override
    public void execute() {
        // Check for negative weights
        for (int i = 0; i < vertices; i++) {
            for (int[] edge : graph[i]) {
                if (edge[1] < 0) {
                    System.out.println("Dijkstra's algorithm does not support negative weights!");
                    success = false;
                    return;
                }
            }
        }

        if (source == -1) {
            for (int i = 0; i < vertices; i++) {
                runDijkstra(i, costs[i], parents[i]);
            }
        } else {
            runDijkstra(source, costs[0], parents[0]);
        }
    }

    private void runDijkstra(int src, int[] cost, int[] parent) {
        Arrays.fill(cost, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        cost[src] = 0;

        boolean[] visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            int u = -1;
            for (int j = 0; j < vertices; j++) {
                if (!visited[j] && (u == -1 || cost[j] < cost[u])) {
                    u = j;
                }
            }

            if (u == -1 || cost[u] == Integer.MAX_VALUE) break;

            visited[u] = true;

            for (int[] edge : graph[u]) {
                int v = edge[0], weight = edge[1];
                if (!visited[v] && cost[u] + weight < cost[v]) {
                    cost[v] = cost[u] + weight;
                    parent[v] = u;
                }
            }
        }
    }

    public String getCost(int from, int to) {
        int cost = (source == -1) ? costs[from][to] : costs[0][to];
        return (cost == Integer.MAX_VALUE) ? "∞" : String.valueOf(cost);
    }

    public String getPath(int from, int to) {
        int[] parent = (source == -1) ? parents[from] : parents[0];
        int[] cost = (source == -1) ? costs[from] : costs[0];

        if (cost[to] == Integer.MAX_VALUE) return "No path exists";

        ArrayList<Integer> path = new ArrayList<>();
        for (int v = to; v != -1; v = parent[v]) {
            path.add(v);
        }

        Collections.reverse(path);
        return path.toString();
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }
}
