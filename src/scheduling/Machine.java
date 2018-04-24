package scheduling;

import java.util.Map;
/**
 * This class for the Machine. The Machine will add time values for jobs
 * to the machine's current time  until it reach's the time limit
 * @authors William Johnson
 * @emails wcj7833
 */
public class Machine {
    private int id;
    private int time_limit;
    private int current_time = 0;

    /**
     * Constructor for a Machine
     * @param id the id number of the machine
     * @param time_limit the time limit of the machine
     */
    public Machine(int id, int time_limit){
        this.id = id;
        this.time_limit = time_limit;
    }

    /**
     * Adds a job to the map of jobs if it can do so without exceeding the time limit
     * @param job the job that will attempt to be added
     * @param jobs the map that the job will be added to
     */
    public void addJob(Job job, Map<String,Job> jobs){
        if (this.canBeAdded(job)) {
            jobs.put(job.getName(), job);
            current_time += job.getCosts()[this.id];
        }
    }

    /**
     * Checks to see if the job can be added to the Machine
     * @param job the job that will be attempted to be added to the Machine
     * @return true if the Machine can be added else false
     */
    public boolean canBeAdded(Job job){
        if (job.getCosts()[this.id]<(time_limit-current_time)) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Getter for the current time value for the machine
     * @return the current time value for the machine
     */
    public int getCurrent_time() {
        return current_time;
    }

    /**
     * Getter for the time limit of the machine
     * @return the time limit of the machine
     */
    public int getTime_limit() {
        return time_limit;
    }
}
