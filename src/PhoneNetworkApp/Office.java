//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------

package PhoneNetworkApp;

import GraphFramework.Vertex;

public class Office extends Vertex {
//default constructor 
    public Office() {
        super();
    }

    public Office(String label) {
        super(label);
    }
// ----------------------------------------
    //setter
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void displayInfo() {
        System.out.println("Office No. " + label);
    }

    @Override
    public String toString() {
        return "Office No. " + label;
    }

}
