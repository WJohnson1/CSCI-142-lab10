package scheduling;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a node (= job)
 */
public class Job {
    /* Name associated with this job */
    private String name;

    /* The amount of time to process this job */
    private int[] costs;

    /* Out neighbors (followers) of this job are stored as a list */
    private List<Job> outNeighbors;

    /* In neighbors (followees) of this job are stored as a list (adjacency list) */
    private List<Job> inNeighbors; //useful for backtracking

    /* The maximum path length from any start node to this node */
    private int rank;

    /**
     * Constructor initializes Job with empty lists of neighbors.
     * @param name the name associated with the node.
     */
    public Job(String name){
        this.name = name;
        this.outNeighbors = new LinkedList<>();
        this.inNeighbors = new LinkedList<>();
        this.rank = 0;
    }

    /**
     * Constructor initializes Job with a given costs.
     * @param name the name associated with the node.
     * @param costs an array of costs associated with the node
     */
    public Job(String name, int[] costs) {
        this(name);
        setCosts(costs);
    }

    /**
     * A copy constructor
     * @param other a job to make a copy of
     */
    public Job(Job other){
        this.name = other.name;
        this.costs = other.costs;
        this.outNeighbors = new LinkedList<>(other.getOutNeighbors());
        this.inNeighbors = new LinkedList<>(other.getInNeighbors());
    }

    /**
     *
     * @param costs an integer array of costs
     */
    public void setCosts(int[] costs){
        this.costs = new int[costs.length];
        this.costs = costs;
    }

    /**
     * @param rank a rank to be set
     */
    public void setRank(int rank){
        this.rank =  rank;
    }

    /**
     * @return rank of this node
     */
    public int getRank(){
        return this.rank;
    }

    /**
     * @return name of this node
     */
    public String getName() {
        return name;
    }

    /**
     * @return an array of costs
     */
    public int[] getCosts(){
        return this.costs;
    }


    /**
     * Removes a node from this node's in-neighbors.
     * @param inNode a node being removed from this node's in-neighbors.
     */
    public void removeInNeighbor(Job inNode){
       this.inNeighbors.remove(inNode);
    }
    /**
     * Add an out-neighbor to this node.  Checks if already present, and does not
     * duplicate in this case.
     *
     * @param n: node to add as out-neighbor.
     */
    public void addOutNeighbor(Job n) {
        if(!outNeighbors.contains(n)) {
            outNeighbors.add(n);
        }
    }
    /**
     * Add an in-neighbor to this node.  Checks if already present, and does not
     * duplicate in this case.
     *
     * @param n: node to add as in-neighbor.
     */
    public void addInNeighbor(Job n) {
        if(!inNeighbors.contains(n)) {
            inNeighbors.add(n);
        }
    }

    /**
     * Method to return the adjacency list for this node containing all
     * of its out-neighbors.
     *
     * @return the list of out-neighbors of the given node
     */
    public Collection<Job> getOutNeighbors() {
        return new LinkedList<>(outNeighbors);
    }
    /**
     * Method to return the adjacency list for this node containing all
     * of its in-neighbors.
     *
     * @return the list of in-neighbors of the given node
     */
    public Collection<Job> getInNeighbors() {
        return new LinkedList<>(inNeighbors);
    }

    /**
     * Indicates whether or not this job is has no in-neighbors
     * @return true is iff this job has no in-neighbors
     */
    public boolean isStartNode(){
        return inNeighbors.isEmpty();
    }

    /**
     * Method to generate a string associated with the node, including the
     * name of the node, costs, the names of its out-neighbors folllowed by in-neighbors.
     * @return string associated with the node.
     */
    @Override
    public String toString() {
        String result;
        result = name +", ";
        result += "cost: " + costs[0] + " " +  costs[1] + " " +  costs[2] + " ";
        result += "rank: "  + rank + " ";
        //result += "pathCost: " + pathCost + " ";
        result += ", out:  ";

        for(Job nbr : outNeighbors) {
            result = result + nbr.getName() + ", ";
        }
        result += "in:  ";
        for(Job nbr : inNeighbors) {
            result = result + nbr.getName() + ", ";
        }
        return (result.substring(0, result.length()-2));
    }

    /**
     *  Two Nodes are equal if they have the same name.
     *
     *  @param other The other object to check equality with
     *  @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Job) {
            Job n = (Job) other;
            result = this.name.equals(n.name);
        }
        return result;
    }

}

