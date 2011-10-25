/*
 * Taken from http://algs4.cs.princeton.edu/
 */
package pubsim.lattices.decoder.firsttype.graph;

import java.util.Random;

/**
 *  Compilation:  javac FlowNetwork.java
 *  Execution:    java FlowNetwork V E
 *  Dependencies: Bag.java FlowEdge.java
 *
 *  A capacitated flow network, implemented using adjacency lists.
 *
 */
public class FlowNetwork {
    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj;
    
    // empty graph with V vertices
    public FlowNetwork(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<FlowEdge>();
    }

    // random graph with V vertices and E edges
    public FlowNetwork(int V, int E) {
        this(V);
        Random rand = new Random();
        for (int i = 0; i < E; i++) {
            int v = rand.nextInt(V);
            int w = rand.nextInt(V);
            double capacity = rand.nextInt(100);
            addEdge(new FlowEdge(v, w, capacity));
        }
    }

    // number of vertices and edges
    public int V() { return V; }
    public int E() { return E; }

    /** 
     * Add edge e in both v's and w's adjacency lists.  Maintains symmetry, it's an undirected graph.
     * This overwrites the existing edge.
     */
    public void addEdge(FlowEdge e) {
        E++;
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    // return list of edges incident to  v
    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    // return list of all edges - excludes self loops
    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<FlowEdge>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj(v)) {
                if (e.to() != v)
                    list.add(e);
            }
        return list;
    }


    // string representation of Graph (excludes self loops) - takes quadratic time
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

}

