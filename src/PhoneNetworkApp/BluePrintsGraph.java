//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------

package PhoneNetworkApp;

import GraphFramework.Edge;
import GraphFramework.Graph;
import GraphFramework.Vertex;
//The BluePrintsGraph class extends the Graph class, 
//indicating that it inherits the properties and methods of the Graph class.
public class BluePrintsGraph extends Graph {

    public BluePrintsGraph(boolean isDiagraph) {
        super(isDiagraph);
    }
    // Override the createVertex method to create Office vertices
    @Override
    public Office createVertex(String label) {
        return new Office(label);
    }
    // Override the createEdge method to create Line edges
    @Override
    public Edge createEdge(Vertex source, Vertex target, int weight) {
        return new Line(source, target, weight);
    }

}
