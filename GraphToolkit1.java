/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ZheZhang
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * This is a toolkit class for performing graph operations on objects 
 * of GraphNode and RoutingGraphNode-classes and their subclasses.
 * 
 * The class has methods for:
 * 
 * + Building a graph from a textual description
 * + Listing the nodes of agraph
 * + Creating routing tables for graphs consisting of RoutingGraphNode-objects 
 *
 */

public class GraphToolkit1 {

    /**
     * Returns the nodes in graph in an ArrayList. The method is given one node 
     * in the graph as a starting point. I will then traverse through the nodes 
     * to find them all.
     * 
     * @param startingPoint The node to start from.
     * @return The ArrayList containing all the nodes.
     */
  
 
  public ArrayList<GraphSegment1> getNodes( GraphSegment1 startingPoint) {
    
    ArrayList<GraphSegment1> returnThese = new ArrayList<GraphSegment1>();
    
    // make arraylist to implement Queue
    ArrayList<GraphSegment1> fringe = new ArrayList<GraphSegment1>();
    HashSet<GraphSegment1> seen = new HashSet<GraphSegment1>();
    GraphSegment1 nodeVar = null;
     
    fringe.add(startingPoint);  // add the starting point to the queue
    seen.add(startingPoint);
    
    while (!fringe.isEmpty())
    {
      /*Take an node from the head of the queue. 
      Put it in the list we will eventually output. 
      Check the neighbors of this node. (getNeighborIterator) 
      Put the previously unknown ones in the end of the queue and to the list of previously known ones. */
      
      nodeVar = fringe.remove(0);
      returnThese.add(nodeVar);
      Iterator iw = nodeVar.getNeigborIterator();
      while(iw.hasNext())
      {
        nodeVar = (GraphSegment1) iw.next();
        if (!seen.contains(nodeVar))
        {
          fringe.add(nodeVar);
          seen.add(nodeVar);
        }
      }
    }
    return returnThese;
}

    /**
     * Builds a graph from the given textual description. The graph will have nodes with
     * a type defined by a parameter.
     * 
     * @param prototype The prototype for the nodes of the graph.
     * @param reader The input stream which contains on each line a description of an edge
     *        connecting two nodes.
     * @return The first node that was created.
     * @throws IOException In case of a reading error the method will abort and
     *         throw an IOException. 
     */
    
    public GraphSegment1 buildGraph( GraphSegment1 prototype,Reader   reader) throws IOException {
    
    /** BufferedReader that is able to read the input effectively and one line at a time. */        
    BufferedReader lineReader = new BufferedReader(reader);

    /** The first node encountered. */
    GraphSegment1 firstNode = null;        

    /** The nodes that have already been seen are stored here. */
    HashMap<String, GraphSegment1> nodes = 
        new HashMap<String, GraphSegment1>();
    
    // String array variable for splitting the lines of the input string stream
    String[] split_line;
    // Variables for storing the substrings on a line
    String node1;
     Double x1;
    String node2;
     Double x2;
              
    boolean IsFirstLine = true;
    // Temp variables for creating a new node 
    GraphSegment1 newNode1 = null;
    GraphSegment1 newNode2 = null;
     
    try {
        String connection = lineReader.readLine();
        while (connection != null) {
          
          // Split the line and remove white spaces
          split_line = connection.split(",");
          node1 = split_line[0].trim();
          x1 = Double.parseDouble(split_line[1].trim());
          //y1 = Integer.parseInt(split_line[2].trim());
          
          node2 = split_line[2].trim();
          x2 = Double.parseDouble(split_line[3].trim());
          //y2 = Integer.parseInt(split_line[5].trim());
          
  
          // if node 1 does not exist, create it, and add to nodes
          if (! nodes.containsKey(node1))
          {
            // Create node
            newNode1 = prototype.newInstance(node1,x1);
          
            //Add it to nodes
            nodes.put(node1,newNode1);
            // If it is the first node, put it to firstNode
            if (IsFirstLine) 
            {
              firstNode = newNode1; 
      //        IsFirstLine=false;
            }        
          }
          else newNode1 = nodes.get(node1);
          if (! nodes.containsKey(node2))
          {
            newNode2 = prototype.newInstance(node2,x2);
            nodes.put(node2,newNode2);
          }
          else newNode2 = nodes.get(node2);
          

          //make the connection
          newNode1.addConnection(newNode2);
          newNode2.addConnection(newNode1);
          
          connection = lineReader.readLine();
          IsFirstLine = false;
        }            
        
    } catch (IOException e) {
        
        //If an exception is caught we add our own information and rethrow it.
        IOException exception = 
            new IOException("Could not read the whole graph.");
        exception.initCause(e);
        
        throw exception;
    }            
    return firstNode;    
}

    
    public ArrayList<GraphSegment1> shortestPath (GraphSegment1 startingPoint, GraphSegment1 endPoint){
      // First, call updateRoutes to update class variables predecessor and distanceFromSource
      HashMap<GraphSegment1, GraphSegment1> predecessor = new HashMap<GraphSegment1, GraphSegment1> ();
      predecessor = this.updateRoutes(startingPoint);
      ArrayList<GraphSegment1> returnThese = new ArrayList<GraphSegment1>();
      GraphSegment1 tmpNode = endPoint;
      returnThese.add(endPoint);
      
      while (tmpNode != startingPoint ) {  //&& (this.predecessor.get(tmpNode) != null)) {
        tmpNode = predecessor.get(tmpNode);
        returnThese.add(tmpNode);
      }
      return returnThese;
    }
    
    /**
     *
     */
    
    public HashMap<GraphSegment1, GraphSegment1> updateRoutes(GraphSegment1 startingPoint) {
      GraphSegment1 varV;
      GraphSegment1 varW;
      GraphSegment1 tmpNode;
      HashMap<GraphSegment1, GraphSegment1> predecessor = new HashMap<GraphSegment1, GraphSegment1>();
      HashMap<GraphSegment1, Double> distanceFromSource = new HashMap<GraphSegment1, Double>();
      // Initialize class variables that are updated by this method
      predecessor.put(null, null);
      distanceFromSource.put(null, 0.0);
      //this.distanceFromSource.put(startingPoint, 0);

      ArrayList<GraphSegment1> newReachableNodes = new ArrayList<GraphSegment1>();
      //element can be added to index 0: newReachableNodes.add(0, someGraphNode);
      
      //Get all nodes in the graph
      ArrayList <GraphSegment1>allNodes = new ArrayList<GraphSegment1>();
      ArrayList <GraphSegment1>determinedNodes = new ArrayList<GraphSegment1>();
      ArrayList <GraphSegment1> latestNeighbors = new ArrayList<GraphSegment1>(); // Undetermined neighbours of the latest determined node  
      allNodes = this.getNodes(startingPoint);
      determinedNodes.add(startingPoint);
      
      //initialize
      for (int i = 0; i <= allNodes.size()-1; i++)
      {
        distanceFromSource.put(allNodes.get(i), startingPoint.getDistanceToNeighbor(allNodes.get(i)));
        if (distanceFromSource.get(allNodes.get(i))==-1) //source node, or not its neighbour -> distance not defined
          predecessor.put(allNodes.get(i), null);
        else // neighbour of the source node -> distance is defined
        {
          
          predecessor.put(allNodes.get(i), startingPoint);
          newReachableNodes.add(allNodes.get(i));// newReachableNodes store all StartingPoints neighbours BCD
        }
      }
      
      while (!newReachableNodes.isEmpty()){
        Iterator irn = newReachableNodes.iterator(); //Iterator has BCD
        varV = (GraphSegment1)irn.next(); // var=B
        Double v = distanceFromSource.get(varV);// distance B->A
        while (irn.hasNext()){
          varW = (GraphSegment1)irn.next();
          Double w = distanceFromSource.get(varW);
          if (v>w){
            varV = varW;         // choose the vertex which has the shortest distance to A for example that is B
            v=w;
          }
        } // while (irn.hasNext())
        
        newReachableNodes.remove(varV);          // newReachableNodes has(CD)
        determinedNodes.add(varV); // now it has A, B 
        // Now we find B neighour, and add it to newReachableNodes(if not there yet),  
        // ...update the distanceFromSource for all undetermined neighour of B.
        Iterator iln = varV.getNeigborIterator();
        latestNeighbors.clear(); // Make sure latestNeighbors is empty first
        while (iln.hasNext()){   // Add to latestNeighbors all neigbours of latest determined nodes that have not been determined yet
          tmpNode = (GraphSegment1) iln.next();
          if (!determinedNodes.contains(tmpNode)) {
            latestNeighbors.add(tmpNode);
          }
        } // while (iln.hasNext())
        
        // Update distanceFromSource and predecessor for nodes in latestNeighbors
        for (int k=0; k<= latestNeighbors.size()-1; k++){
          varW = latestNeighbors.get(k);
          Double newDistance = distanceFromSource.get(varV) + varV.getDistanceToNeighbor(varW);
          if ((varV.getDistanceToNeighbor(varW) != -1) && 
              (predecessor.get(varW) == null || (distanceFromSource.get(varW) > newDistance))) 
          {  
            // Distance from source decreases for varW
            distanceFromSource.put(varW, newDistance);
            // Add varW to newReachableNodes
            if (predecessor.get(varW) == null) {
              // not reached before
              newReachableNodes.add(varW);
            }
            predecessor.put(varW, varV);
          } // if ((varV.getDistanceToNeighbor(varW) != -1) && ...
        } // for (int k=0; k<= latestNeighbors.size()-1; k++){
      } // while (!newReachableNodes.isEmpty()) {
      return predecessor;
    } // public void updateRoutes(GraphNode startingPoint) {
    
  } // public class GraphToolkit {



