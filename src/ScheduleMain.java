import backtracker.*;
import scheduling.*;
import viewer.BacktrackingViewer;

import java.util.Optional;

/**
 * The main program for job scheduling. <br>
 * <br>
 * It is run on the command line with four arguments:<br>
 * <br>
 * <tt>$ java ScheduleMain filename time_limit number_machines true/false </tt>
 *
 */

public class ScheduleMain {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java ScheduleMain graph-file timeLimit #-machines debug");
        } else {


            double start = System.currentTimeMillis();

            Graph analysis = new Graph(args[0]);

            JobSchedulingConfig init = new JobSchedulingConfig(analysis.getJobs(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

            init.displayJobsAssignmentOrder();

            Backtracker bt = new Backtracker(args[3].equals("true"));


            Optional<Configuration> sol = bt.solve(init);

            System.out.println("Elapsed time: " +
                    (System.currentTimeMillis() - start)/1000.0 + " seconds.");

            if (sol.isPresent()) {
                System.out.println("Goal Configuration: " + sol.get());
            } else {
                System.out.println("No solution!");
            }
            if(args[3].equals("true")) {
                BacktrackingViewer.launchViewer();
            }
        }
    }
}
