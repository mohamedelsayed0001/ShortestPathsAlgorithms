package org.example.menus;

import org.example.algorithims.Algorithm;
import org.example.algorithims.BellmanFord;
import org.example.algorithims.Dijkstra;
import org.example.algorithims.FloydWarshall;

import java.util.ArrayList;

import static org.example.GraphCLI.getIntInput;

public class AllPairsMenu implements Menu {

    private final int vertices;
    private final ArrayList<int[]>[] graph;

    public AllPairsMenu (int vertices, ArrayList<int[]>[] graph) {
        this.vertices = vertices;
        this.graph = graph;
    }

    @Override
    public void display() {
        System.out.println("\nChoose an algorithm:");
        System.out.println("1. Dijkstra's Algorithm");
        System.out.println("2. Bellman-Ford Algorithm");
        System.out.println("3. Floyd-Warshall Algorithm");
        System.out.print("Enter your choice: ");

        Algorithm strategy;
        int choice = getIntInput();

        switch (choice) {
            case 1:
                strategy = new Dijkstra(graph, -1);
                break;
            case 2:
                strategy = new BellmanFord(graph, -1);
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
            System.out.println("\n--- All Pairs Shortest Path Results ---");
            System.out.println("1. Get cost of path between two nodes");
            System.out.println("2. Get path between two nodes");
            System.out.println("3. Return to main menu");
            System.out.print("Enter your choice: ");

            int subChoice = getIntInput();
            int src;
            int dest;

            switch (subChoice) {
                case 1:
                    System.out.print("Enter source node: "); src = getIntInput();
                    System.out.print("Enter destination node: "); dest = getIntInput();

                    if (src >= 0 && src < vertices && dest >= 0 && dest < vertices) {
                        System.out.println("Path cost from " + src + " to " + dest + ": " + strategy.getCost(src, dest));
                    } else System.out.println("Invalid source or destination node.");
                    break;
                case 2:
                    System.out.print("Enter source node: "); src = getIntInput();
                    System.out.print("Enter destination node: "); dest = getIntInput();

                    if (src >= 0 && src < vertices && dest >= 0 && dest < vertices) {
                        System.out.println("Path from " + src + " to " + dest + ": " + strategy.getPath(src, dest));
                    } else System.out.println("Invalid source or destination node.");
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
