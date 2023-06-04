//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------
package GraphFramework;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Graph {

    int verticesNo;// Number of vertices in the graph
    int edgeNo;// Number of edges in the graph
    boolean isDiagraph;
    protected Map<String, Vertex> vertices;// Map to store vertices with their names as keys
    protected Vertex firstVertex;// First vertex in the graph

    public Graph() {
        vertices = new HashMap<String, Vertex>();
    }

    public Graph(boolean isDiagraph) {
        this.isDiagraph = isDiagraph;// Set the number of vertices
        this.vertices = new HashMap<String, Vertex>();// Initialize the vertices map
    }

    public Graph(int verticesNo, int edgeNo, boolean isDiagraph) {
        this.verticesNo = verticesNo;// Set the number of vertices
        this.edgeNo = edgeNo;//  Set the number of edges
        this.isDiagraph = isDiagraph;// Set the directed or undirected flag
        this.vertices = new HashMap<String, Vertex>();// Initialize the vertices map
    }

    public Edge addEdge(Vertex source, Vertex target, int weight) {
        Edge edge = null;
        // Check if both source and target vertices are not null
        if (source != null && target != null) {
            addVertex(source.label);// Add the source vertex to the graph if it doesn't exist already
            addVertex(target.label);// Add the target vertex to the graph if it doesn't exist already
            edge = addEdge(source.label, target.label, weight);
        }

        return edge;
    }
    //-----------------------------------------------------------------------------------------------
/*
    This method is used to generate a random graph with the specified number of vertices and edges.
    It takes two parameters: verticesNo represents the number of vertices, and edgeNo represents the desired number of edges.
    The method initializes the graph properties and the vertices map to store the vertices and their adjacency lists
    -------------------------------------------------------------------------------------------------
    Creating the Initial Connections:
    The method starts by adding vertices to the graph with labels from "0" to (verticesNo - 1).
    It then enters a loop to create initial connections between vertices. It connects vertex i with vertex i+1 for i ranging from 0 to (verticesNo - 2).
    A random weight is generated for each edge, and the addEdge() method is called to add the edge to the graph.
    -------------------------------------------------------------------------------------------------
    After creating the initial connections, the method enters a loop to create the remaining edges based on the specified edgeNo.
    Inside the loop, a random weight is generated for each edge.
    -------------------------------------------------------------------------------------------------
    The method then performs several checks:
    If the source and target vertices are the same, the loop continues to the next iteration to avoid self-loops.
    If the source and target vertices are not the same and there is no existing edge between them, the method proceeds to create a new edge.
     */
    public void makeGraph(int verticesNo, int edgeNo) {
        this.verticesNo = verticesNo;// Set the number of vertices
        this.edgeNo = 0;// Set the number of edges
        this.isDiagraph = false;// Set the graph as undirected
        this.vertices = new HashMap<String, Vertex>();// Initialize the vertices map

        for (int i = 0; i < verticesNo; i++) {
            addVertex(i + "");// Add vertices to the graph with labels
        }

        int edgeNoCount = edgeNo;

        // First make sure that the graph is connected
        // Connect vertices with each other.
        // We need to connect V[i] Vertex with Vertex V[i+1]
        for (int i = 0; i < verticesNo - 1; i++) {
            // Randomly create edge weight
            int weight = (int) (1 + Math.random() * verticesNo);

            // Add edge
            addEdge(i + "", (i + 1) + "", weight);
            edgeNoCount--;
        }

        // Create remaining edges
        while (edgeNoCount > 0) {
            // Randomly create edge weight
            int weight = (int) (1 + Math.random() * verticesNo);

            // Randomly select source and target vertices
            String source = (int) (Math.random() * (verticesNo)) + "";
            String target = (int) (Math.random() * (verticesNo)) + "";

            if (source.equals(target)) {
                continue;// Skip if source and target vertices are the same
            }

            if (isEdge(source, target)) {
                continue;// Skip if an edge already exists between source and target vertices

            }

            if (!isDiagraph && isEdge(target, source)) {
                edgeNoCount--;// Skip if the graph is undirected and an edge already exists between target and source vertices
                continue;
            }
            // Create new edge
            addEdge(source, target, weight);
            edgeNoCount--;

            if (this.edgeNo >= (this.verticesNo * (this.verticesNo - 1))) {
                break;// Break the loop if the maximum number of possible edges is reached
            }
        }

    }
//-----------------------------------------------------------------------------------------------------------------------

    /*
    This method is used to check if an edge exists between two vertices in a graph.
    It takes two parameters: source and target, which represent the labels of the 
    source and target vertices, respectively.
    The method starts by looping through the adjacency list of the source vertex,
    which is accessed using vertices.get(source)
    For each Edge object e in the adjacency list, it checks if the target vertex's label is equal to the given target.
    If a match is found, it means that an edge exists between the source and target vertices, so the method returns true.
    If the loop finishes without finding a matching edge, 
    the method returns false, indicating that there is no edge between the source and target vertices.
     */
    private boolean isEdge(String source, String target) {
        // Looping through the adjacency list of the source vertex
        for (Edge e : vertices.get(source).adjList) {
            // Checking if the target vertex label matches the given target
            if (e.target.label.equals(target)) {
                return true;// If an edge is found, return true
            }
        }
        return false;// If no edge is found, return false
    }
//-----------------------------------------------------------------------------------------------------------------------

    /*
    This method is used to read a graph from a file and populate the graph object with the data from the file.
    It takes a fileName parameter, which represents the name of the file containing the graph data.
    The method uses a Scanner object to read the file.
    ---------------------------------------------------------------------------
    Reading the Graph Type and Size:
    The first line of the file contains the graph type, which is either "digraph 0" for an undirected graph or "digraph 1" for a directed graph. 
    The method reads this line and sets the isDiagraph flag accordingly.
    The next two integers read from the file represent the number of vertices and edges in the graph. 
    These values are stored in the verticesNo and edgeNo variables, respectively.
    ---------------------------------------------------------------------------
    Reading and Creating Edges:
    The method enters a loop to read the edges from the file. It continues as long as there is input remaining in the file 
    and the number of read lines is less than the total number of edges (line < edgeNo).
    Inside the loop, the method reads the source vertex label, creates the corresponding Vertex object, and stores it in the source variable.
    Then, it reads the target vertex label, creates the corresponding Vertex object, and stores it in the target variable.
    After that, the method reads the weight of the edge as an integer.
    Finally, it adds the edge to the graph using the addEdge() method
     */
    public void readGraphFromFile(String fileName) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
            // Read the graph type (digraph 0 for undirected, digraph 1 for directed)
            String type = scanner.nextLine();

            if (type.equalsIgnoreCase("digraph 0")) {
                isDiagraph = false;// Set the graph as undirected
            } else if (type.equalsIgnoreCase("digraph 1")) {
                isDiagraph = true;// Set the graph as directed
            } else {
                // Throw an exception for invalid graph type
                throw new Exception("Invalid graph type.");
            }
            // Read the number of vertices and edges
            int verticesNo = scanner.nextInt();
            int edgeNo = scanner.nextInt();

            this.verticesNo = verticesNo; // Set the number of vertices
            this.edgeNo = edgeNo;// Set the number of edges

            int line = 0;
            while (scanner.hasNext() && line < edgeNo) {
                // Read the source vertex label
                String sourceLabel = scanner.next();
                Vertex source = createVertex(sourceLabel);// Create the source vertex
                // Read the target vertex label
                String targetLabel = scanner.next();
                Vertex target = createVertex(targetLabel);// Create the target vertex
                // Read the edge weight
                int weight = scanner.nextInt();

                if (addEdge(source, target, weight) == null) {
                    throw new Exception("Error while create graph edges.");
                }

                line++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();// Close the scanner
            }
        }
    }
//--------------------------------------------------------------------------
// getter

    public int getVerticesNo() {
        return verticesNo;
    }

    public int getEdgeNo() {
        return edgeNo;
    }

    public boolean isDiagraph() {
        return isDiagraph;
    }

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public Vertex getFirstVertex() {
        return firstVertex;
    }
//--------------------------------------------------------------------------
// setter

    public void setDiagraph(boolean isDiagraph) {
        this.isDiagraph = isDiagraph;
    }
//--------------------------------------------------------------------------
// abstract methode

    public abstract Vertex createVertex(String label);

    public abstract Edge createEdge(Vertex v, Vertex u, int weight);
//--------------------------------------------------------------------------
/*
    This method is used to add a vertex to the graph with the given label.
    It takes the label parameter, which represents the label of the vertex to be added.
     */
    private void addVertex(String label) {
        Vertex v = createVertex(label);// Create a new vertex object using the provided label
        if (vertices.isEmpty()) {
            // If the vertices map is empty, set the firstVertex and add the vertex to the vertices map
            firstVertex = v;
            vertices.put(v.label, v);
        } else if (!vertices.containsKey(label)) {
            // If the vertices map is not empty and the label is not already present in the map, add the vertex to the vertices map
            vertices.put(v.label, v);
        }
    }
//--------------------------------------------------------------------------

    /*
    This method is used to add an edge to the graph with the given source and target vertices and weight.
It takes the e1 and e2 parameters, which represent the labels of the source and target vertices, respectively, 
    and the weight parameter, which represents the weight of the edge.
    --------------------------------------------------------------------------
    Checking :
    The method first retrieves the source and target vertices from the vertices map using their labels.
    It checks that both source and target vertices exist in the graph (source != null and target != null) and that they are not the same vertex (!source.equals(target)).
    If these conditions are met, it proceeds to create a new edge object using the createEdge() method, with the given source, target, and weight.
    The source and target vertices of the edge are set accordingly (edge.source = source and edge.target = target).
    It then checks if the edge already exists in the graph using the isEdge() method. If it does, the method returns the edge without adding it again.
    If the edge does not exist, it is added to the adjacency list of the source vertex (source.adjList.add(edge)), and the edge count in the graph is incremented (edgeNo++).
    If the graph is undirected (!isDiagraph), it creates edge from the target to the source.
    the edge is created using the createEdge() method, with the target and source vertices swapped, and the same weight.
    if edge does not exist, it is added to the adjacency list of the target vertex and the edge count is incremented.
     */
    private Edge addEdge(String e1, String e2, int weight) {
        Vertex source = vertices.get(e1);// Get the source vertex from the vertices map using label e1
        Vertex target = vertices.get(e2);// Get the target vertex from the vertices map using label e2
        Edge edge = null;// Initialize the edge variable as null
        // Check if both source and target vertices exist in the graph and they are not the same vertex
        if (source != null && target != null && !source.equals(target)) {
            edge = createEdge(source, target, weight);// Create a new edge object with the given source, target, and weight
            edge.source = source;// Set the edge's source vertex as the source vertex object
            edge.target = target;// Set the edge's target vertex as the target vertex object

            if (isEdge(edge)) {
                return edge; // If the edge already exists in the graph, return the edge
            } else {
                source.adjList.add(edge); // Add the edge to the adjacency list of the source vertex
                edgeNo++; // Increment the count of edges in the graph
            }

            if (!isDiagraph) {
                // If the graph is undirected, create a symmetric edge from target to source
                Edge edge2 = createEdge(target, source, weight); // Create the symmetric edge

                if (!isEdge(edge2)) {
                    target.adjList.add(edge2); // Add the symmetric edge to the adjacency list of the target vertex
                    edgeNo++; // Increment the count of edges in the graph
                }
            }
        }

        return edge; // Return the edge that was added to the graph (or null if no edge was added)
    }
//--------------------------------------------------------------------------

    /*
    This method is used to check if an edge already exists in the graph.
    It takes an edge parameter, which represents the edge to be checked.
     */
    private boolean isEdge(Edge edge) {
        for (Vertex v : vertices.values()) {
            for (Edge e : v.adjList) {
                if (e.source.label.equals(edge.source.label)
                        && e.target.label.equals(edge.target.label)) {
                    return true;
                }
            }
        }
        return false;
    }
//--------------------------------------------------------------------------
    // This method is used to print the edges of the graph.

    public void print() {
        System.out.println("________________________________");
        for (Vertex v : vertices.values()) {
            for (Edge e : v.adjList) {
                System.out.println(e);
            }
        }
        System.out.println("________________________________");
    }
}
