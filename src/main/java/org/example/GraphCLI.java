package org.example;

import org.example.menus.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphCLI {
    private static ArrayList<int[]>[] graph;
    private static final Scanner scanner = new Scanner(System.in);
    private static int vertices;
    private static final GraphParser parser = new GraphParser();

    public static void main(String[] args) {
        System.out.println("Welcome to the Graph CLI tool");
        System.out.print("Please enter the path to the graph file: ");
        String filePath = scanner.nextLine();
        try {
            graph = parser.parseGraph(filePath);
            vertices = graph.length;
            mainMenu();
        } catch (IOException e) {
            System.err.println("Error reading the graph file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void mainMenu() {
        boolean exit = false;

        while (!exit) {
            separateTermial();
            parser.printGraph(graph);

            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Find shortest paths from source node to all other nodes");
            System.out.println("2. Find shortest paths between all pairs of nodes");
            System.out.println("3. Check if the graph contains a negative cycle");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            if(choice == -1) continue;

            Menu subMenu = null;

            switch (choice) {
                case 1:
                    subMenu = new SingleSourceMenu(vertices, graph);
                    break;
                case 2:
                    subMenu = new AllPairsMenu(vertices, graph);
                    break;
                case 3:
                    subMenu = new NegativeCycleMenu(graph);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if(subMenu == null) continue;
            subMenu.display();
        }
    }

    public static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    public static void separateTermial() {
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }

}