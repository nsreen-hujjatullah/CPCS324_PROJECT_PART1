//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------
package GraphFramework;

import java.util.LinkedList;
//Vertex class that represents a vertex in a graph

public class Vertex implements Comparable<Edge> {

    protected String label;
    protected boolean isVisited;
    protected LinkedList<Edge> adjList;
// Default constructor
    public Vertex() {
        adjList = new LinkedList<Edge>();
    }
// Constructor with label parameter
    public Vertex(String label) {
        this.label = label;
        adjList = new LinkedList<Edge>();
    }
//-------------------------------------------------------------------------
// getter
    public String getLabel() {
        return label;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public LinkedList<Edge> getAdjList() {
        return adjList;
    }
//---------------------------------------------------------------------------
// setter
    public void setLabel(String label) {
        this.label = label;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }
// Method to display information about the vertex
    public void displayInfo() {
        System.out.println(label);
    }

    @Override
    public String toString() {
        return label;
    }
// Equals method for comparing vertices based on their labels
    @Override
    public boolean equals(Object obj) {
        Vertex v = (Vertex) obj;
        return this.label.equals(v.label);
    }

    @Override
    public int compareTo(Edge arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
}
