//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------
package PhoneNetworkApp;

import GraphFramework.Edge;
import GraphFramework.Vertex;

public class Line extends Edge {

    int lLength;
//default constructor 
    public Line() {
        super();
    }

    public Line(int weight) {
        super(weight);
        this.lLength = 5 * weight;
    }

    public Line(Vertex source, Vertex target, int weight) {
        super(source, target, weight);
        this.lLength = 5 * weight;
    }

    public int getLength() {
        return lLength;
    }

    @Override
    public void displayInfo() {
        System.out.println(source + " - " + target + " : " + "line length: " + lLength);
    }

    @Override
    public String toString() {
        return source + " - " + target + " : " + "line length: " + lLength;
    }
}
