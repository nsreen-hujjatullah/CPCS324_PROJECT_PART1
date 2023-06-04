//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------
package GraphFramework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
The KruskalAlg class extends the MSTAlgorithm class,
indicating that it is a specific implementation of an algorithm for finding the Minimum Spanning Tree (MST) in a graph.
parents: A map used for creating subsets of vertices. It maps a vertex label to the parent edge in the subset.
ranks: A map used for storing the rank of each subtree rooted at a vertex. It maps a vertex label to its rank.
edges: An ArrayList used to store the edges of the graph.

 */
public class KruskalAlg extends MSTAlgorithm {

    // A map for creating V subsets
    Map<String, Edge> parents = new HashMap<String, Edge>();
    // A map for storing rank of subtree rooted at i
    Map<String, Integer> ranks = new HashMap<String, Integer>();
    // Array of edges
    ArrayList<Edge> edges = new ArrayList<Edge>();
//--------------------------------------------------------------------------------------    
//The constructor KruskalAlg(Graph graph) initializes the KruskalAlg object and 
// calls the constructor of the superclass MSTAlgorithm by passing the graph parameter.

    public KruskalAlg(Graph graph) {
        super(graph);
    }
//--------------------------------------------------------------------------------------

    /*
    This method is an override of the displayResultingMST() method defined in the superclass.
     */
    @Override
    public void displayResultingMST() {
        for (Edge e : MSTresultList) {
            System.out.println("\t" + e);
        }

    }
//--------------------------------------------------------------------------------------

    /*
    The startMST() method initiates the process of finding the Minimum Spanning Tree (MST) using Kruskal's algorithm.
    It performs many operations to create subsets, sort edges, and merge components.
     */
    @Override
    public void startMST() {
        // It creates an empty MSTresultList to store the resulting Minimum Spanning Tree edges.
        MSTresultList = new ArrayList<Edge>();
        // Create array of edges, sorted by weight
        edges = new ArrayList<Edge>();
        for (Vertex v : graph.vertices.values()) {
            for (Edge e : v.adjList) {
                //edges.add(e);
                edges.add(graph.createEdge(e.source, e.target, e.weight));
            }
        }

        sortEdgesByWeight();

        // Create V subsets with single elements
        parents = new HashMap<String, Edge>();
        //For each vertex v in the graph, it creates a self-loop edge and assigns it as the parent of v. The self-loop edge has a weight of 0.
        for (Vertex v : graph.vertices.values()) {
            Edge edge = graph.createEdge(v, v, 0);
            edge.parent = v;
            //The self-loop edges are stored in the parents map, where the vertex label is the key and the self-loop edge is the value.
            parents.put(v.label, edge);
        }

        // rank of subtree rooted at i
        ranks = new HashMap<String, Integer>();
        //For each vertex v in the graph, it assigns a rank of 1 to the subtree rooted at v.
        for (Vertex v : graph.vertices.values()) {
            ranks.put(v.label, 1);
        }
        //It iterates through the edges in the edges list, which are sorted by weight.

        for (Edge e : edges) {
            //For each edge e, it retrieves the source vertex label v and the target vertex label w.
            String v = e.source.label;
            String w = e.target.label;
            //It finds the root vertices of the subsets containing v and w using the find() method.
            String rootV = find(v);
            String rootW = find(w);

            //If both rootV and rootW are not null and are not equal, it means that adding the edge e does not create a cycle.
            if (rootV != null && rootW != null) {
                //It merges the subsets containing v and w using the union() method.
                //It adds the edge e to the MSTresultList.
                if (!rootV.equals(rootW)) {
                    union(v, w); // merge v and w components
                    MSTresultList.add(e); // add edge e to mst
                }
            }
        }
    }
//--------------------------------------------------------------------------------------

    /*
    The sortEdgesByWeight() method implements a basic sorting algorithm called "Bubble Sort" 
    to sort the edges in ascending order based on their weights.
    It iterates through the edges using two nested loops.
    In each iteration, it compares the weight of the current edge (edges.get(j)) with the weight of the next adjacent edge (edges.get(j + 1)).
     */
    private void sortEdgesByWeight() {
        for (int i = 1; i < edges.size(); i++) {
            for (int j = 0; j < (edges.size() - i); j++) {
                // Compare the weights of adjacent edges
                if (edges.get(j).weight > edges.get(j + 1).weight) {
                    // Swap the edges if they are out of order
                    Edge temp = edges.get(j);
                    edges.set(j, edges.get(j + 1));
                    edges.set(j + 1, temp);
                }
            }
        }
    }
//---------------------------------------------------------------------------------
    // Returns the canonical element of the set containing element p

    private String find(String p) {
        // Check if the parent of vertex p is not itself
        if (!parents.get(p).parent.label.equals(p)) {
            // Recursively find the ultimate parent of vertex p
            return find(parents.get(p).parent.label);
        }
        // If the parent of vertex p is itself, it is the ultimate parent
        return p;
    }
//---------------------------------------------------------------------------------
    // Merges the set containing element v with the set containing element w

    public void union(String p, String q) {
        // Find the ultimate parent of vertices p and q
        String rootP = find(p);
        String rootQ = find(q);

        // If p and q already belong to the same set, no union is needed
        if (rootP.equals(rootQ)) {
            return;
        }

        // Make the root of the smaller rank point to the root of the larger rank
        if (ranks.get(rootP) < ranks.get(rootQ)) {
            parents.put(rootP, parents.get(rootQ));
        } else if (ranks.get(rootP) > ranks.get(rootQ)) {
            parents.put(rootQ, parents.get(rootP));
        } else {
            // If the ranks are equal, choose one root as the parent and increment its rank
            parents.put(rootQ, parents.get(rootP));
            ranks.put(rootP, ranks.get(rootP) + 1);
        }
    }

}
