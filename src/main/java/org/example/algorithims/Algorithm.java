package org.example.algorithims;


public interface Algorithm {
    void execute ();

    String getCost(int source, int destination);

    String getPath(int source, int destination);

    boolean isSuccessful();
}
