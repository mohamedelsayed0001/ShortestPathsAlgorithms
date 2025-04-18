package org.example.algorithims;

import java.util.*;

public class BellmanFord implements Algorithm {
    private final int source;
    private final ArrayList<int[]>[] graph;
    private final int vertices;
    private final int[][] costs;
    private final int[][] parents;
    private boolean hasNegativeCycle = false;

    public BellmanFord(ArrayList<int[]>[] graph, int source) {
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
        if (source == -1) {
            for (int i = 0; i < vertices; i++) {
                if (!runBellmanFord(i, costs[i], parents[i])) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        } else {
            if (!runBellmanFord(source, costs[0], parents[0])) {
                hasNegativeCycle = true;
            }
        }
    }

    private boolean runBellmanFord(int src, int[] cost, int[] parent) {
        Arrays.fill(cost, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        cost[src] = 0;

        List<int[]> edges = new ArrayList<>();
        for (int u = 0; u < vertices; u++) {
            for (int[] neighbor : graph[u]) {
                edges.add(new int[]{u, neighbor[0], neighbor[1]});
            }
        }

        for (int i = 1; i < vertices; i++) {
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], weight = edge[2];
                if (cost[u] != Integer.MAX_VALUE && cost[u] + weight < cost[v]) {
                    cost[v] = cost[u] + weight;
                    parent[v] = u;
                }
            }
        }

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            if (cost[u] != Integer.MAX_VALUE && cost[u] + weight < cost[v]) {
                System.out.println("Negative cycle detected when starting from node " + src);
                return false;
            }
        }

        return true;
    }

    @Override
    public String getCost(int from, int to) {
        int cost = (source == -1) ? costs[from][to] : costs[0][to];
        return cost == Integer.MAX_VALUE ? "∞" : String.valueOf(cost);
    }

    @Override
    public String getPath(int from, int to) {
        int[] parent = (source == -1) ? parents[from] : parents[0];
        int[] cost = (source == -1) ? costs[from] : costs[0];

        if (cost[to] == Integer.MAX_VALUE) return "No path";

        List<Integer> path = new ArrayList<>();
        for (int v = to; v != -1; v = parent[v]) {
            path.add(v);
        }

        Collections.reverse(path);
        return path.toString();
    }

    @Override
    public boolean isSuccessful() {
        return !hasNegativeCycle;
    }
}
