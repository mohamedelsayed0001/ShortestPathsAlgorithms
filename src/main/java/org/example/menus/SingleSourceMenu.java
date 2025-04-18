package org.example.menus;

import org.example.algorithims.*;

import java.util.ArrayList;

import static org.example.GraphCLI.getIntInput;

public class SingleSourceMenu implements Menu {

    private final int vertices;
    private final ArrayList<int[]>[] graph;

    public SingleSourceMenu (int vertices, ArrayList<int[]>[] graph) {
        this.vertices = vertices;
        this.graph = graph;
    }

    @Override
    public void display() {
        System.out.print("Enter the source node: ");
        int source = getIntInput();

        if (source < 0 || source >= vertices) {
            System.out.println("Invalid source node. Returning to main menu.");
            return;
        }

        System.out.println("\nChoose an algorithm:");
        System.out.println("1. Dijkstra's Algorithm (no negative weights)");
        System.out.println("2. Bellman-Ford Algorithm");
        System.out.println("3. Floyd-Warshall Algorithm");
        System.out.print("Enter your choice: ");

        Algorithm strategy;
        int choice = getIntInput();

        switch (choice) {
            case 1:
                strategy = new Dijkstra(graph, source);
                break;
            case 2:
                strategy = new BellmanFord(graph, source);
                break;
            case 3:
                strategy = new FloydWarshall(graph);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }

        strategy.execute();
        if (strategy.isSuccessful()) {
            for (int dest = 0; dest < vertices; dest++) {
                System.out.println("Path from " + source + " to " + dest + ": " + strategy.getPath(source, dest)
                        + ", cost = " + strategy.getCost(source, dest));
            }
        }
    }
}