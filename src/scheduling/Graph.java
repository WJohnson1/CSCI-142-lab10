package scheduling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class representing the dependency of jobs
 */
public class Graph {
    /**
     *Jobs is represented using a map (dictionary)
     */
    protected Map<String, Job> jobs;

    /**
     * Construct a directed graph by reading data from a file
     * Perform acyclicity test. If the graph is not acyclic,
     * print a proper message "Cyclic. No solution" and exit the program.
     * Set the rank of all nodes.
     * Display the graph
     * @param filename The name of a file containing node/job's name, costs, and dependency
     */
    public Graph(String filename) {
        try (Scanner in = new Scanner(new File(filename))) {
            //0. Construct the graph
            jobs = new HashMap<>();

            while (in.hasNextLine()) {
                String line = in.nextLine();
                String[] fields = line.split(",");
                int[] costs = {Integer.parseInt(fields[1]),
                        Integer.parseInt(fields[2]), Integer.parseInt(fields[3])};
                if (!jobs.containsKey(fields[0])) {

                    jobs.put(fields[0], new Job(fields[0], costs));

                }
                else{
                    jobs.get(fields[0]).setCosts(costs);
                }
                Job job = jobs.get(fields[0]);
                for (int i=4; i<fields.length; ++i) {
                    if (!jobs.containsKey(fields[i])) {
                        jobs.put(fields[i], new Job(fields[i]));
                    }
                    Job neighbor = jobs.get(fields[i]);
                    job.addOutNeighbor(neighbor);
                    neighbor.addInNeighbor(job);
                }

            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex);
        }

        //1. Acyclicity test
        if (!isAcyclic()) {
            System.out.println("Graph is cyclic. No solution!");
            System.exit(0);
        }
        System.out.println("Graph is acyclic.");


        //2. Set rank of each node in graph
        setRankBFS();
    }

    /**
     * @return a string string representation of the jobs.
     */
    @Override
    public String toString() {
        String result = "";
        for (String name : jobs.keySet()) {
            result = result + jobs.get(name) + "\n";
        }
        return result;
    }

    /**
     * @return the graph of jobs
     */
    public Map<String, Job> getJobs(){
        return this.jobs;
    }



    /**
     Perform the following peeling process in a DFS manner starting from each start node.
     Repeat until there is no start node left:
     Remove a start node with its edges from the graph.
     * @return true iff the resulted graph is emoty
     */
    public boolean isAcyclic(){
        return false;
    }


    /**
     * Set the rank of each nodes in this graph using BFS.
     * The rank of a node is defined as the maximum path length from any staring node to the node.
     */
    public void setRankBFS() {

    }

    /**
     * Test graph analysis before backtracking
     * @param args file name
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Graph graph-file");
        } else {

            Graph analysis = new Graph(args[0]);
            System.out.println("Ranks are set.");
            System.out.println(analysis);
        }
    }
}

