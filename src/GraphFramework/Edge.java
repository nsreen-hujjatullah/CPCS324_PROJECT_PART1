//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------

package GraphFramework;
// Edge class that represents an edge in a graph.
//implemented the Comparable interface to compare the weights of edges
public class Edge implements Comparable<Edge> {

    protected Vertex source;
    protected Vertex target;
    protected Vertex parent;
    protected int weight;
// Default constructor
    public Edge() {
        this.weight = 0;
        this.source = null;
        this.target = null;
        this.parent = null;
    }
// Constructor with weight parameter
    public Edge(int weight) {
        this.weight = weight;
        this.source = null;
        this.target = null;
        this.parent = null;
    }
    // Constructor with source, target, and weight parameters
    public Edge(Vertex source, Vertex target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        this.parent = null;
    }
//-------------------------------------------------------------------------
// getter
    public Vertex getSource() {
        return source;
    }

    public Vertex getTarget() {
        return target;
    }

    public Vertex getParent() {
        return parent;
    }

    public int getWeight() {
        return weight;
    }
//---------------------------------------------------------------------------
// setter
    public void setSource(Vertex source) {
        this.source = source;
    }

    public void setTarget(Vertex target) {
        this.target = target;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
// Comparison method for sorting edges based on their weight
    @Override
    public int compareTo(Edge edge) {
        if (this.weight > edge.weight) {
            return 1;
        } else if (this.weight == edge.weight) {
            return 0;
        } else {
            return -1;
        }
    }
// Equals method for comparing edges based on their source and target vertices
    @Override
    public boolean equals(Object obj) {
        Edge e = (Edge) obj;

        return this.source.equals(e.source) && this.target.equals(e.target);
    }
// Method to display information about the edge

    public void displayInfo() {
        System.out.println(source.label + " - " + target.label);
    }
// String representation of the edge
    @Override
    public String toString() {
        return source.label + " - " + target.label;
    }
}
