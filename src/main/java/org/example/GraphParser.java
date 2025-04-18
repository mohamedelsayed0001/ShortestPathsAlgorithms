package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphParser {

    @SuppressWarnings("unchecked")
    public ArrayList<int[]>[] parseGraph(String filePath) throws IOException {
        ArrayList<int[]>[] graph = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // read first line
            String[] firstLine = reader.readLine().trim().split("\\s+");
            int vertices = Integer.parseInt(firstLine[0]);
            int edges = Integer.parseInt(firstLine[1]);

            graph = new ArrayList[vertices]; // array of arrayLists of arrays [node, weight]
            for (int i = 0; i < vertices; i++) {
                graph[i] = new ArrayList<>();
            }

            // read E lines
            for (int i = 0; i < edges; i++) {
                String[] edgeLine = reader.readLine().trim().split("\\s+");
                int source = Integer.parseInt(edgeLine[0]);
                int destination = Integer.parseInt(edgeLine[1]);
                int weight = Integer.parseInt(edgeLine[2]);

                graph[source].add(new int[]{destination, weight});
            }
        }

        return graph;
    }

    public void printGraph(ArrayList<int[]>[] graph) {
        System.out.println("Graph");
        for (int i = 0; i < graph.length; i++) {
            System.out.print(i + ": [");
            for (int j = 0; j < graph[i].size(); j++) {
                int[] edge = graph[i].get(j);
                System.out.print("[" + edge[0] + ", " + edge[1] + "]");
                if (j < graph[i].size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}