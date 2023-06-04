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
The MHPrimAlg class represents an implementation of the Prim's algorithm for finding the minimum spanning tree (MST) of a graph.
It extends the MSTAlgorithm class, indicating that it specializes in finding MSTs.
*/
public class MHPrimAlg extends MSTAlgorithm {

    // A map that associates each vertex label with the edge that connects it to its parent in the MST.
    Map<String, Edge> parents = new HashMap<String, Edge>();
    // A map that associates each vertex label with the edge that connects it to the MST.
    Map<String, Edge> edgeTo = new HashMap<String, Edge>();
    // The constructor of the MHPrimAlg class takes a Graph object as input and calls the constructor of the MSTAlgorithm class
    public MHPrimAlg(Graph graph) {
        super(graph);
    }
/*
    The startMST method is responsible for executing the Prim's algorithm to find the minimum spanning tree (MST).
    It initializes necessary data structures and uses a min heap to extract edges with the minimum weight.
    */
    public void startMST() {
        edgeTo = new HashMap<String, Edge>();
        // Initialize min heap with all vertices. Key value of
        // all vertices (except 0th vertex) is initially infinite
        parents = new HashMap<String, Edge>();
        for (Vertex v : graph.vertices.values()) {
            Edge edge = graph.createEdge(v, graph.createVertex(""), Integer.MAX_VALUE);
            edge.parent = graph.createVertex("#");
            parents.put(v.label, edge);
        }

        // Make key value of 0th vertex as 0 so that it
        // is extracted first
        parents.get(graph.firstVertex.label).weight = 0;

        //All edges are added to the min heap, minHeap, 
        //which is initially sized equal to the number of vertices.
        MinHeap<Edge> minHeap = new MinHeap<Edge>();
        for (Edge e : parents.values()) {
            minHeap.insert(e);
        }

        while (!minHeap.isEmpty()) {
            // In each iteration, the top element (minimum weight) is extracted from the heap.
            Edge minEdge = minHeap.remove();

            // Get source vertex of top element of heap
            Vertex v = graph.vertices.get(minEdge.source.label);

            // Mark the extracted vertex as visited
            v.isVisited = true;
            for (Edge e : v.adjList) {
                String w = e.target.label;
                if (!graph.vertices.get(w).isVisited) {
                    Edge dest = parents.get(w);
                    if (e.weight < dest.weight) {
                        // Decrease the key of dest Edge in Heap
                        Edge newEdge = graph.createEdge(
                                graph.createVertex(e.target.label),
                                graph.createVertex(""), e.weight);
                        newEdge = graph.createEdge(
                                dest.source, dest.target, e.weight);
                        minHeap.decreaseKey(dest, newEdge);
                        // Update the weight and the parent
                        dest.parent = v;
                        dest.weight = e.weight;
                        parents.put(w, dest);
                        // Add edge into MSTresultList
                        edgeTo.put(w, e);
                    }
                }
            }
        }
        MSTresultList = new ArrayList<Edge>();
        for (Edge e : edgeTo.values()) {
            MSTresultList.add(e);
        }
    }

    @Override
    public void displayResultingMST() {
        for (Edge e : MSTresultList) {
            System.out.println("\t" + e);
        }
    }

}

class MinHeap<T extends Comparable<T>> {

    protected ArrayList<T> elements;

    public MinHeap() {
        elements = new ArrayList<T>();
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
          // Checks if the heap is empty
        return elements.isEmpty();
    }

    public void insert(T e) {
        // TODO Auto-generated method stub
        elements.add(e);
        upHeap(elements.size() - 1);
    }

    public T remove() {
        if (elements.size() == 0) {
            return null; // If the heap is empty, return null  
        }

        if (elements.size() == 1) {
            // If there's only one element, remove and return it
            return elements.remove(0);
        }
// Remove the minimum item (root of the heap)
        T minItem = elements.get(0);
// Replace the root with the last element in the heap
        elements.set(0, elements.remove(elements.size() - 1));
       // Move the new root down the heap to maintain the min-heap property
        downHeap(0);

        return minItem;
    }

    public void decreaseKey(T element, T newElement) {
        int i = 0;
        for (i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(element)) {
                break;
            }
        }

        if (i < elements.size()) {
            // Update the element with the new value
            elements.set(i, newElement);
            upHeap(i);           
        }
    }

    private void upHeap(int i) {
        if (i <= 0) {
            return;// If the current index is at or below the root, stop the up-heap process
        }

        T item = elements.get(i);
        T parent = elements.get((i - 1) / 2);

        if (item.compareTo(parent) < 0) {
            // Swap the item with its parent if it is smaller
            T temp = parent;
            elements.set((i - 1) / 2, item);
            elements.set(i, temp);
             // Move the item and its parent up the heap recursively
            upHeap((i - 1) / 2);
        }

    }
 /*
    * this method is used to move an element down the heap to its correct position
      to maintain the min-heap property.
    * takes the index i as a parameter, representing the index of the element to be moved down.
    * this method first checks if the current index is at or beyond the 
      last non-leaf node. If so, the method returns and stops the down-heap process 
      since the current element is already in its correct position.
    * It then calculates the index of the smaller child of the current element (minChild).
    * Next, it compares the value of the left child (left) with the right child (if it exists) and selects the smaller child.
    * If the parent is greater than the selected child, a swap is performed between the parent and the child.
    * After the swap, the method calls itself recursively on the index of the swapped child (minChild) to continue the down-heap process.
     */
    private void downHeap(int i) {
        if (i >= (elements.size() - 1) / 2) {
            return;
        }

        int minChild = 2 * i + 1;
        T left = elements.get(minChild);
        T parent = elements.get(i);

        if (minChild < elements.size() - 1
                && left.compareTo(elements.get(minChild + 1)) > 0) {
            // If the right child is smaller than the left child, choose the right child
            minChild += 1;
        }

        if (parent.compareTo(elements.get(minChild)) > 0) {
            T temp = parent;
            elements.set(i, elements.get(minChild));
            elements.set(minChild, temp);
            // Move the parent and its child down the heap recursively
            downHeap(minChild);
        }
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return elements.toString();
    }
    /*
   * The test method is used to demonstrate the functionality of the MinHeap class.
   * It creates a new instance of MinHeap with Integer as the element type.
   * Several elements (3, 13, 7, 16, 21, 12, 9) are inserted into the min heap using the insert method.
   * The min heap is then printed using System.out.println. This will display the elements in the order they appear 
     in the underlying array representation of the min heap.
   * Finally, the method enters a loop that continues until the min heap becomes empty. 
     In each iteration, it removes and prints the minimum element from the min heap using the remove method. 
    
     */
    public void test() {
        MinHeap<Integer> minHeap = new MinHeap<Integer>();
        minHeap.insert(3);
        minHeap.insert(13);
        minHeap.insert(7);
        minHeap.insert(16);
        minHeap.insert(21);
        minHeap.insert(12);
        minHeap.insert(9);

        System.out.println("\nThe Min Value is : " + minHeap);

        while (!minHeap.isEmpty()) {
            System.out.println(minHeap.remove());
        }
    }
}
