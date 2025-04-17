package org.example.algorithims;

import java.util.*;

public class FloydWarshall implements Algorithim {
    private final int vertices;
    private final int[][] cost;
    private final int[][] parent;
    private final ArrayList<int[]>[] graph;
    private final int source; 

    private boolean hasNegativeCycle = false; 

    public FloydWarshall(ArrayList<int[]>[] graph, int source, int[][] cost, int[][] parent) {
        this.graph = graph;
        this.vertices = graph.length;
        this.source = source;
        this.cost = cost;
        this.parent = parent;
    }

    @Override
    public void execute() {
        // Initialize cost and parent matrices
        for (int i = 0; i < vertices; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE);
            Arrays.fill(parent[i], -1);
            cost[i][i] = 0;
        }

        // Populate with graph weights
        for (int u = 0; u < vertices; u++) {
            for (int[] edge : graph[u]) {
                int v = edge[0];
                int weight = edge[1];
                cost[u][v] = weight;
                parent[u][v] = u;
            }
        }

        // Floyd-Warshall core logic
        for (int k = 0; k < vertices; k++) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (cost[i][k] != Integer.MAX_VALUE && cost[k][j] != Integer.MAX_VALUE &&
                        cost[i][k] + cost[k][j] < cost[i][j]) {
                        cost[i][j] = cost[i][k] + cost[k][j];
                        parent[i][j] = parent[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < vertices; i++) {
            if (cost[i][i] < 0) {
                hasNegativeCycle = true;
                break;
            }
        }
    }

    @Override
    public String getCost(int destination) {
        int c = cost[source][destination];
        return c == Integer.MAX_VALUE ? "∞" : String.valueOf(c);
    }

    @Override
    public String getPath(int destination) {
        if (cost[source][destination] == Integer.MAX_VALUE) return "No path";

        List<Integer> path = new ArrayList<>();
        int v = destination;
        while (v != -1 && v != source) {
            path.add(v);
            v = parent[source][v];
        }
        if (v == -1) return "No path";
        path.add(source);
        Collections.reverse(path);
        return path.toString();
    }
    public boolean isSuccessful() {
        return !hasNegativeCycle;
    }
}

