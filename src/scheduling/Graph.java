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
    public ArrayList<Job> getJobs(){
        ArrayList<Job> j = new ArrayList<>(this.jobs.values());
        return j;
    }


    /**
     Perform the following peeling process in a DFS manner starting from each start node.
     Repeat until there is no start node left:
     Remove a start node with its edges from the graph.
     * @return true if the resulted graph is empty
     */
    public boolean isAcyclic(){

        boolean something = false;
        for (Job j: getJobs()){
            if (j.isStartNode()) {
                something = true;
            }
            for (Job destination: getJobs()){
                int first = 0;
                List<Job> path = this.buildPathDFS(j,destination);
                if (path != null) {
                    while (path.size() > 0) {
                        if(path.get(0).equals(j) & first < 1){
                            path.remove(0);
                            first++;
                        }
                        else if (path.get(0).equals(j)) {
                            return false;
                        } else {
                            path.remove(0);
                        }

                    }
                }
            }
        }
        if (!something){
            return false;
        }
        return true;

    }

    private boolean visitDFS( Job job, Set< Job > visited ) {

        for (Job newjob: job.getOutNeighbors()){
            if ( !visited.contains( newjob ) ) {
                visited.add( newjob );
                visitDFS( newjob, visited );
            }
            else return false;
        }
        return true;
    }
    /**
     * Create a path from a starting node to a finishing node if such
     * a path exists.
     * Uses depth-first search to determine if a path exists.
     * This pair of public and private methods uses a path list.
     * Another approach, shown below in the BFS code, is associating a
     * predecessor with each node.
     *
     * @rit.pre the arguments correspond to nodes in the graph
     * @param startNode  with starting node
     * @param finishNode finishing node
     * @return an iteration from start to finish, or null if none exists
     */
    public List< Job > buildPathDFS( Job startNode, Job finishNode ) {

        // assumes input check occurs previously


        // Construct a visited set of all nodes reachable from the start
        Set< Job > visited = new HashSet<>();
        //visited.add( startNode );
        return buildPathDFS( startNode, finishNode, visited );
    }
    /**
     * Set the rank of each nodes in this graph using BFS.
     * The rank of a node is defined as the maximum path length from any staring node to the node.
     */
    public void setRankBFS() {
        for (Job startnode: getJobs()){
            if (startnode.isStartNode()) {
                for (Job j : getJobs()) {
                    List<Job> path = buildPathDFS(startnode,j);
                    if (path != null) {
                        //System.out.println(j.getName() + "|"  +path);
                        if (path.size() > j.getRank()) {
                            j.setRank(path.size() - 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Recursive function that visits all nodes reachable from the given node
     * in depth-first search fashion. Visited list is updated in the process.
     *
     * @param start the node from which to search
     * @param finish the node for which to search
     * @param visited the list of nodes that have already been visited
     * @return the path from start to finish as a List,
     *         or if no path, an empty List.
     */
    private List< Job > buildPathDFS( Job start, Job finish, Set< Job > visited ) {

        List< Job > path = null;

        if ( start.equals( finish ) )  {
            path = new LinkedList<>();
            // Put finish node in path. (Should be 1st node added.)
            path.add( start );
            return path;
        }

        for ( Job nbr : start.getOutNeighbors() ) {
            if ( !visited.contains( nbr ) ) {
                visited.add( nbr );
                path = buildPathDFS( nbr, finish, visited );
                if ( path != null ) {
                    // Prepend this node to the successful path.
                    path.add( 0, start );
                    return path;
                }
            }
        }

        return null; // Failed on finding path from all neighbors. :-(
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

