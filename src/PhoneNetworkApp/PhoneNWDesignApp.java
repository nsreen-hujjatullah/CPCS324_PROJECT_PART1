//---------------------------------------
// PROJECT CPCS-324 PART1
// NSREEN , NOOR, MAI, AREEG
// B0A
//---------------------------------------
package PhoneNetworkApp;

import java.util.ArrayList;
import java.util.Scanner;

import GraphFramework.Edge;
import GraphFramework.KruskalAlg;
import GraphFramework.MHPrimAlg;
import GraphFramework.MSTAlgorithm;

public class PhoneNWDesignApp {
    // Object of BluePrintsGraph

    public static BluePrintsGraph bluePrintsGraph;
    public static BluePrintsGraph bluePrintsGraph2;

    // Scanner to get user inputs
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Scanner to get user inputs
        input = new Scanner(System.in);
        // Create a new instance of BluePrintsGraph
        bluePrintsGraph = new BluePrintsGraph(true);
        // displays a menu of options for the user to choose from and reads the user's choice.
        char menu;
        do {
            System.out.println("-------------------------------");
            System.out.println("| phone network design.");
            System.out.println("------------------------------");
            System.out.println("| 1: Requirement 1 [Apply the algorithms on file generated from Appendix1].");
            System.out.println("| 2: Requirement 2 [Apply the algorithms on random integers as specified (7 cases)].");
            System.out.println("| 3: Exit");
            System.out.println("------------------------------");
            System.out.print(" > Please enter your choice... (1-3): ");

            menu = input.next().charAt(0);// Read the user's choice from input

            System.out.println();

            if (menu == '1') {
                requirement1();// Call the method corresponding to choice 1
            } else if (menu == '2') {
                requirement2();// Call the method corresponding to choice 2
            } else if (menu == '3') {
                break;// Exit the loop if choice 3 is selected
            }

            System.out.println();

        } while (menu != '4');

        input.close();// Close the input scanner
    }

    // Method to handle requirement 1
    private static void requirement1() {
        String fileName = "graph.txt";// Specify the file name
        //creates a new instance of BluePrintsGraph with the parameter false to indicate that it is not a directed graph.
        bluePrintsGraph = new BluePrintsGraph(false);
        bluePrintsGraph.readGraphFromFile(fileName);// Read the graph data from the file

        MSTAlgorithm kruskalAlg = new KruskalAlg(bluePrintsGraph);// Create Kruskal's algorithm instance
        displayResultingMST(kruskalAlg, "Kruskal");// Display the resulting minimum spanning tree using Kruskal's algorithm

        System.out.println();

        MSTAlgorithm mhPrimAlg = new MHPrimAlg(bluePrintsGraph);// Create min-heap based Prim's algorithm instance
        displayResultingMST(mhPrimAlg, "min-heap based Prim");// Display the resulting minimum spanning tree using min-heap based Prim's algorithm
    }

    private static void requirement2() {
        char caseNo;
        int verticesNo = 0;
        int edgeNo = 0;
        //It display the user to select a case number from 1 to 7 to specify the number of vertices and edges for the random graph.
        do {
            System.out.println("-------------------------------");
            System.out.println("| Requirement 2 [Apply the algorithms on random integers as specified (7 cases)].");
            System.out.println("------------------------------");
            System.out.println("| 1: n =  1,000, m = 10,000");
            System.out.println("| 2: n =  1,000, m = 15,000");
            System.out.println("| 3: n =  1,000, m = 25,000");
            System.out.println("| 4: n =  5,000, m = 15,000");
            System.out.println("| 5: n =  5,000, m = 25,000");
            System.out.println("| 6: n = 10,000, m = 15,000");
            System.out.println("| 7: n = 10,000, m = 25,000");
            System.out.println("------------------------------");
            System.out.print(" > Select case no... (1-7): ");
            //The user's choice is stored in the caseNo variable.
            caseNo = input.next().charAt(0);
            //Based on the selected case number, the verticesNo and edgeNo variables are assigned the corresponding values.
            if (caseNo == '1') {
                verticesNo = 1000;
                edgeNo = 10000;
                break;
            } else if (caseNo == '2') {
                verticesNo = 1000;
                edgeNo = 15000;
                break;
            } else if (caseNo == '3') {
                verticesNo = 1000;
                edgeNo = 25000;
                break;
            } else if (caseNo == '4') {
                verticesNo = 5000;
                edgeNo = 15000;
                break;
            } else if (caseNo == '5') {
                verticesNo = 5000;
                edgeNo = 15000;
                break;
            } else if (caseNo == '6') {
                verticesNo = 10000;
                edgeNo = 15000;
                break;
            } else if (caseNo == '7') {
                verticesNo = 10000;
                edgeNo = 25000;
                break;
            } else {
                //If the selected case number is invalid, an error message is displayed.
                System.out.println("Invlaid case number, please select [1-7]");
            }

        } while (true);//The loop continues until a valid case number is selected.

        System.out.print("Does the graph a directed graph ? [Y/N]: ");
        char ch = input.next().charAt(0);
        // Create a new instance of BluePrintsGraph
        // with the specified directed graph flag
        bluePrintsGraph2 = new BluePrintsGraph(false);
        if (ch == 'Y') {
            bluePrintsGraph2.setDiagraph(true);
        }
        //The makeGraph method of bluePrintsGraph2 is then called to 
        //generate a random graph with the specified number of vertices and edges.
        bluePrintsGraph2.makeGraph(verticesNo, edgeNo);
        // Create an instance of Kruskal's algorithm
        MSTAlgorithm kruskalAlg = new KruskalAlg(bluePrintsGraph2);
        // Call the displayResultingMST2 method to run Kruskal's algorithm
        // and measure the execution time
        double kruskalAlgTime = displayResultingMST2(kruskalAlg, "Kruskal");
        System.out.println();
        // Create an instance of min-heap based Prim's algorithm
        MSTAlgorithm mhPrimAlg = new MHPrimAlg(bluePrintsGraph2);
        // Call the displayResultingMST2 method to run min-heap based Prim's algorithm
        // and measure the execution time
        double mhPrimAlgTime = displayResultingMST2(mhPrimAlg, "min-heap based Prim");
        // Calculate the difference in execution times
        double diff = kruskalAlgTime - mhPrimAlgTime;
        // Display the comparison result
        if (diff > 0) {
            System.out.printf("The Running time of Kruskal algorithm is more then the min-heap based Prim algorithm by: %f ms\n", diff);
        } else {
            System.out.printf("The Running time of Kruskal algorithm is less then the min-heap based Prim algorithm by: %f ms\n", (-1 * diff));
        }
    }

    private static void displayResultingMST(MSTAlgorithm mstAlgorithm, String algorithm) {
        System.out.println("------------------------------");
        System.out.println("The phone network (minimum spanning tree) generated by " + algorithm + " algorithm is as follows:");
        System.out.println("---------");
        // Record the start time
        long startTime = System.currentTimeMillis();
        // Execute the MST algorithm
        mstAlgorithm.startMST();
        // Display the resulting MST
        mstAlgorithm.displayResultingMST();
        // Record the end time
        long endTime = System.currentTimeMillis();
        // Calculate the running time in milliseconds
        double runningTime = (endTime - startTime)/1000.0;
        System.out.println("---------");
        System.out.println("The cost of designed phone network: " + calcTotalCost(mstAlgorithm.getMSTresultList()));
        System.out.printf("Running time: %f s\n", runningTime);
        System.out.println("------------------------------");
    }

    private static double displayResultingMST2(MSTAlgorithm mstAlgorithm, String algorithm) {
        System.out.println("------------------------------");
        System.out.println("The phone network (minimum spanning tree) generated by " + algorithm + " algorithm is as follows:");
        System.out.println("---------");
        // Record the start time
        long startTime = System.currentTimeMillis();
        // Execute the MST algorithm
        mstAlgorithm.startMST();
        // Record the end time
        long endTime = System.currentTimeMillis();
        // Calculate the running time in milliseconds
        double runningTime = (endTime - startTime)/1000.0;
        System.out.println("---------");
        System.out.println("The cost of designed phone network: " + calcTotalCost(mstAlgorithm.getMSTresultList()));
        System.out.printf("Running time: %f s\n", runningTime);
        System.out.println("------------------------------");
        return runningTime;
    }

    private static int calcTotalCost(ArrayList<Edge> msTresultList) {
        int cost = 0;

        // Iterate over each edge in the MST result list
        for (Edge e : msTresultList) {
            // Add the weight of the edge to the total cost
            cost += e.getWeight();
        }

        return cost;
    }

}
