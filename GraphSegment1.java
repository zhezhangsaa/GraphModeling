/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author ZheZhang
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class GraphSegment1 {

    /**
     * The LinkedHashMap contains the neigbors as keys and the distances to these
     * neighbors as Integer values. 
     */     
    private java.util.HashMap<GraphSegment1, Double> neighbors;

    /**
     * The nodes in this exercise can only have String data, which is guaranteed
     * to be unique for each node.
     */
    private String data;
    private Double length;
 
    
    /**
     * If a distance to a non-existing neighbor is requested, the response is
     * the value of this constant.
     */
    public static final Double NO_CONNECTION = -1.0;    
    
    /**
     * Creates a new GraphNode. 
     * 
     * @param givenData The data for the node.
     */
    public GraphSegment1(String givenData, Double l) {
      this.neighbors = new java.util.LinkedHashMap<GraphSegment1, Double>();
      this.length = l;
      this.data = givenData;
  }
  
    
    /**
     * Adds an edge between two nodes (this and the one given as parameter)
     * that has the given distance.
     * 
     * @param otherNode The node this node will be connected to.
     * @param distance The distance between the two nodes.
     */
    public void addConnection(GraphSegment1 otherNode) {

        Double distance=this.getDistance(otherNode);
        this.neighbors.put(otherNode,distance);

        Double oldDistance=this.neighbors.get(otherNode);       
   //     System.out.println(this.data+'-'+otherNode.getData()+' '+distance+'\n');       
        if (oldDistance != null && oldDistance.intValue() == distance)
        {
          return;
        }                    
       }
   
    public Double getDistance(GraphSegment1 target)
    {    
   
      Double tarL= target.length;
      //distance travel between to road segment is to add theirs length together. 
      Double distance= this.length+tarL;

      return distance;
      
    }
    //this return the length of the 1 road segment
    public Double getXCoord() {
      return this.length;
  }
  
 
    
    /**
     * Returns an Iterator which can be used to browse through the neighbor nodes.
     * 
     * @return An Iterator which can be used to browse through the neighbor nodes.
     */
    public java.util.Iterator<GraphSegment1> getNeigborIterator() {
        return this.neighbors.keySet().iterator();
    }
    
    /**
     * Returns the distance to a given neighbor.
     * 
     * @param target The neighbor the distance to which we want to get.
     * @return The distance to the neighbor or NO_CONNECTION if the nodes are not neighbors.
     */
    public Double getDistanceToNeighbor(GraphSegment1 target) {
        Double distance = this.neighbors.get(target);
        if (distance == null){
            return NO_CONNECTION;
        }
        else {
            return distance;
        }
    }
    
    /**
     * Returns the data ID contained in this node.
     */
    public String getData() {
        return this.data;
    }
    
    /**
     * Creates a new instance of this class. This method is intended to be used
     * in the buildGraph method of the GraphToolkit-class. 
     * 
     * This method is intended to be overridden in all subclasses.
     *  
     * @param data The data for the new node
     * @return the new GraphNode-instance.
     */
    public GraphSegment1 newInstance(String data,Double x) {
        return new GraphSegment1(data,x);
    }
}

