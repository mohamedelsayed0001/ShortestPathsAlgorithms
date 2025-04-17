package org.example.menus;

import java.util.ArrayList;

import static org.example.GraphCLI.getIntInput;

public class NegativeCycleMenu implements Menu{

    private final int vertices;
    private final ArrayList<int[]>[] graph;

    public NegativeCycleMenu (int vertices, ArrayList<int[]>[] graph) {
        this.vertices = vertices;
        this.graph = graph;
    }

    public void display() {
        System.out.println("\nChoose an algorithm to check for negative cycles:");
        System.out.println("1. Using Bellman-Ford Algorithm");
        System.out.println("2. Using Floyd-Warshall Algorithm");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        boolean hasNegativeCycle = false;

        switch (choice) {
            case 1:
//                hasNegativeCycle = checkNegativeCycleBellmanFord();
                break;
            case 2:
//                hasNegativeCycle = checkNegativeCycleFloydWarshall();
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }

        if (hasNegativeCycle) {
            System.out.println("The graph contains at least one negative cycle.");
        } else {
            System.out.println("The graph does not contain any negative cycles.");
        }
    }
}
