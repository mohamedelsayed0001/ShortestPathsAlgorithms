package org.example.algorithims;

import java.util.*;

public class BellmanFord implements Algorithim {
    private final int source;
    private final ArrayList<int[]>[] graph; // Adjacency list: int[] = {neighbor, weight}
    private final int vertices;
    private final int[] costs;
    private final int[] parents;

    public BellmanFord(ArrayList<int[]>[] graph, int source, int[] costs, int[] parents) {
        this.source = source;
        this.graph = graph;
        this.vertices = graph.length;
        this.costs = costs;
        this.parents = parents;
    }

    @Override
    public void execute() {
        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        costs[source] = 0;

        // Convert adjacency list to edge list
        List<int[]> edges = new ArrayList<>();
        for (int u = 0; u < vertices; u++) {
            for (int[] neighbor : graph[u]) {
                int v = neighbor[0];
                int weight = neighbor[1];
                edges.add(new int[]{u, v, weight});
            }
        }

        
        for (int i = 1; i < vertices; i++) {
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1], weight = edge[2];
                if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                    costs[v] = costs[u] + weight;
                    parents[v] = u;
                }
            }
        }

        // Check for negative-weight cycles
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                System.out.println("Graph contains a negative-weight cycle");
                return;
            }
        }
    }

    @Override
    public String getCost(int destination) {
        return costs[destination] == Integer.MAX_VALUE ? "∞" : String.valueOf(costs[destination]);
    }

    @Override
    public String getPath(int destination) {
        if (costs[destination] == Integer.MAX_VALUE) return "No path";

        List<Integer> path = new ArrayList<>();
        for (int v = destination; v != -1; v = parents[v]) {
            path.add(v);
        }
        Collections.reverse(path);
        return path.toString();
    }
}
