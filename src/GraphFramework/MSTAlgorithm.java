//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------
package GraphFramework;

import java.util.ArrayList;

public abstract class MSTAlgorithm {

    protected Graph graph; // The graph on which the algorithm operates
    protected ArrayList<Edge> MSTresultList = new ArrayList<Edge>(); // List to store the resulting MST

    public MSTAlgorithm(Graph graph) {
        this.graph = graph;
        this.MSTresultList = new ArrayList<Edge>();
    }

    public Graph getGraph() {
        return graph;
    }

    public ArrayList<Edge> getMSTresultList() {
        return MSTresultList;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void setMSTresultList(ArrayList<Edge> mSTresultList) {
        MSTresultList = mSTresultList;
    }

    public abstract void displayResultingMST(); // Abstract method to display the resulting MST

    public abstract void startMST(); // Abstract method to start the MST algorithm
}
