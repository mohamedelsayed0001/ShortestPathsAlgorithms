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

        boolean backToMain = false;

        strategy.execute();
        if(!strategy.isSuccessful()) {
            backToMain = true;
        }

        while (!backToMain) {
            System.out.println("\n--- Single Source Shortest Path Results ---");
            System.out.println("1. Get cost of path to a specific node");
            System.out.println("2. Get path to a specific node");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");

            int subChoice = getIntInput();
            int dest;
            switch (subChoice) {
                case 1:
                    System.out.print("Enter destination node: ");
                    dest = getIntInput();
                    if (dest >= 0 && dest < vertices) {
                        System.out.print("Path cost from " + source + " to " + dest + ": " + strategy.getCost(source, dest));
                    } else System.out.println("Invalid destination node.");

                    break;
                case 2:
                    System.out.print("Enter destination node: ");
                    dest = getIntInput();
                    if (dest >= 0 && dest < vertices) {
                        System.out.print("Path from " + source + " to " + dest + ": " + strategy.getPath(source, dest));
                    } else System.out.println("Invalid destination node.");

                    break;
                case 3:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
