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
        Algorithim strategy = null;

        if (source < 0 || source >= vertices) {
            System.out.println("Invalid source node. Returning to main menu.");
            return;
        }

        System.out.println("\nChoose an algorithm:");
        System.out.println("1. Dijkstra's Algorithm (no negative weights)");
        System.out.println("2. Bellman-Ford Algorithm");
        System.out.println("3. Floyd-Warshall Algorithm");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();

        int[] costs = new int[vertices];
        int[] parents = new int[vertices];

        switch (choice) {
            case 1:
                strategy = new Dijkstra(graph, source, costs, parents);
                break;
            case 2:
//                BellmanFord(source);
                break;
            case 3:
//                FloydWarshall(source);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }

        assert strategy != null;
        strategy.execute();

        boolean backToMain = false;
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
                        System.out.print("Path from " + source + " to " + dest + ": " + strategy.getCost(dest));
                    } else System.out.println("Invalid destination node.");

                    break;
                case 2:
                    System.out.print("Enter destination node: ");
                    dest = getIntInput();
                    if (dest >= 0 && dest < vertices) {
                        System.out.print("Path from " + source + " to " + dest + ": " + strategy.getPath(dest));
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
