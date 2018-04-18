package scheduling;

import backtracker.Configuration;

import java.util.Collection;
import java.util.Map;

public class JobSchedulingConfig implements Configuration{
    public JobSchedulingConfig(Map<String, Job> jobs, int time_limit, int num_machines) {
    }

    public void displayJobsAssignmentOrder() {
    }

    /**
     * Get the collection of successors from the current one.
     *
     * @return All successors, valid and invalid
     */
    @Override
    public Collection<Configuration> getSuccessors() {
        return null;
    }

    /**
     * Is the current configuration valid or not?
     *
     * @return true if valid; false otherwise
     */
    @Override
    public boolean isValid() {
        return false;
    }

    /**
     * Is the current configuration a goal?
     *
     * @return true if goal; false otherwise
     */
    @Override
    public boolean isGoal() {
        return false;
    }
}
