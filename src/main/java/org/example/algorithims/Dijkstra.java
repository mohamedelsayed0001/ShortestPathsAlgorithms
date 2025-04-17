package org.example.algorithims;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Dijkstra implements Algorithim {

    private int source;
    private ArrayList<int[]>[] graph;
    private final int vertices;
    private int[] costs;
    private int[] parents;

    public Dijkstra(ArrayList<int[]>[] graph, int source, int[] costs, int[] parents) {
        this.source = source;
        this.graph = graph;
        this.vertices = graph.length;
        this.costs = costs;
        this.parents = parents;
    }

    public void execute() {

        for (int i = 0; i < vertices; i++) {
            for (int[] edge : graph[i]) {
                if (edge[1] < 0) {
                    System.out.println("Warning: Dijkstra's algorithm does not support negative weights!");
                    return;
                }
            }
        }

        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[source] = 0;

        Arrays.fill(parents, -1);

        boolean[] visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            int u = -1;
            for (int j = 0; j < vertices; j++) {
                if (!visited[j] && (u == -1 || costs[j] < costs[u])) {
                    u = j;
                }
            }

            if (u == -1 || costs[u] == Integer.MAX_VALUE) {
                break;
            }

            visited[u] = true;

            for (int[] edge : graph[u]) {
                int v = edge[0];
                int weight = edge[1];

                if (!visited[v] && costs[u] != Integer.MAX_VALUE &&
                        costs[u] + weight < costs[v]) {
                    costs[v] = costs[u] + weight;
                    parents[v] = u;
                }
            }
        }

    }

    public String getCost(int destination) {
        if (costs[destination] == Integer.MAX_VALUE) {
            return "destination is unreachable";
        }
        return String.valueOf(costs[destination]);
    }

    public String getPath(int destination) {
        if (destination == source) {
            return Integer.toString(source);
        }

        if (parents[destination] == -1 || costs[destination] == Integer.MAX_VALUE) {
            return "No path exists";
        }

        ArrayList<Integer> path = new ArrayList<>();
        int current = destination;

        while (current != -1) {
            path.add(current);
            current = parents[current];
        }

        Collections.reverse(path);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            result.append(path.get(i));
            if (i < path.size() - 1) {
                result.append(" -> ");
            }
        }

        return result.toString();
    }
}
