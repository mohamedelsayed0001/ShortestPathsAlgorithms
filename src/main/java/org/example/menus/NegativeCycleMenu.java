package org.example.menus;

import org.example.algorithims.Algorithm;
import org.example.algorithims.BellmanFord;
import org.example.algorithims.FloydWarshall;

import java.util.ArrayList;

import static org.example.GraphCLI.getIntInput;

public class NegativeCycleMenu implements Menu{

    private final ArrayList<int[]>[] graph;

    public NegativeCycleMenu (ArrayList<int[]>[] graph) {
        this.graph = graph;
    }

    public void display() {
        System.out.println("\nChoose an algorithm to check for negative cycles:");
        System.out.println("1. Using Bellman-Ford Algorithm");
        System.out.println("2. Using Floyd-Warshall Algorithm");
        System.out.print("Enter your choice: ");

        int choice = getIntInput();
        Algorithm strategy;

        switch (choice) {
            case 1:
                strategy = new BellmanFord(graph, 0);
                break;
            case 2:
                strategy = new FloydWarshall(graph);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
                return;
        }

        strategy.execute();

        if(strategy.isSuccessful()) {
            System.out.println("The graph does not contain any negative cycles.");
        }
    }
}
